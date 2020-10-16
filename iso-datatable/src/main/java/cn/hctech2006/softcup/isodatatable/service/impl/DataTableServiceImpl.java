package cn.hctech2006.softcup.isodatatable.service.impl;

import cn.hctech2006.softcup.isodatatable.bean.NlDatatable;
import cn.hctech2006.softcup.isodatatable.common.ServerResponse;
import cn.hctech2006.softcup.isodatatable.service.DynamicService;
import cn.hctech2006.softcup.isodatatable.vo.DatatableVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DataTableServiceImpl {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private DynamicService dynamicService;
    @Autowired
    private DatatableServiceImpl datatableService;

    private Logger logger = LoggerFactory.getLogger(DataTableServiceImpl.class);

    public ServerResponse getOne(String dtId){
        NlDatatable datatable = (NlDatatable) datatableService.getOne(dtId).getData();
        if (datatable == null) return ServerResponse.createByError();
        Map<String, String> result = new HashMap<>();
        result.put("dtName", datatable.getDtName());
        result.put("dbId", datatable.getDbId());
        return ServerResponse.createBySuccess(result);
    }
    /**
     * 公共类获取数据源下数据表
     * @param dbId
     * @return
     */
    public ServerResponse getDataTable(String dbId) throws Exception {
        if (StringUtils.isEmpty(dbId)) return ServerResponse.createByError("参数不能为空");
        ServerResponse response = dynamicService.getDatatables(dbId);
        List<String> result = (List<String>) response.getData();
        //对数据表进行持久化
        response = storageDatatable(result, dbId);
        if (!response.isSuccess()) return ServerResponse.createByError("持久化失败");
        logger.info("------------------当前数据源数据表持久化成功-----------------------");
        return response;
    }



    /**
     * 数据表持久化存储
     * @param result
     * @param dbId
     * @return
     */
    private ServerResponse storageDatatable(List<String> result, String dbId){
        List<NlDatatable> datatables = new ArrayList<>();
        for (String term : result){
            NlDatatable datatable = new NlDatatable();
            datatable.setDbId(dbId);
            datatable.setDtId(dbId+DigestUtils.md5DigestAsHex(term.getBytes()));
            datatable.setDtName(term);
            datatable.setDtTime(new Date());
            datatable.setShowFlag("1");
            datatables.add(datatable);
        }
        ServerResponse response =  datatableService.upload(datatables);
        List<DatatableVO> datatableVOS = new ArrayList<>();
        for (NlDatatable datatable : datatables){
            DatatableVO datatableVO = datatablePOJOToDatatableVO(datatable);
            datatableVOS.add(datatableVO);
        }
        if (response.isSuccess()) return ServerResponse.createBySuccess(datatableVOS);
        return response;
    }

    /**
     * 数据表POJO转数据表表现层对象
     * @param datatable
     * @return
     */
    private DatatableVO datatablePOJOToDatatableVO(NlDatatable datatable){
        DatatableVO datatableVO = new DatatableVO();
        datatableVO.setDtId(datatable.getDtId());
        datatableVO.setDtName(datatable.getDtName());
        datatableVO.setDtTime(sdf.format(datatable.getDtTime()));
        return datatableVO;
    }


}
