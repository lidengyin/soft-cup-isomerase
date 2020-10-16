package cn.hctech2006.softcup.isodatatable.service.impl;

import cn.hctech2006.softcup.isodatatable.bean.NlDatatable;
import cn.hctech2006.softcup.isodatatable.common.ServerResponse;
import cn.hctech2006.softcup.isodatatable.mapper.NlDatatableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DatatableServiceImpl {
    @Autowired
    private NlDatatableMapper datatableMapper;
    /**
     * 数据源中数据表存储
     * @param datatables
     * @return
     */
    public ServerResponse upload(List<NlDatatable> datatables){
        for (NlDatatable param : datatables){
            NlDatatable datatable = datatableMapper.selectByDbIdAndDtId(param.getDbId(), param.getDtId());
            if (datatable != null) continue;
            int result = datatableMapper.insert(param);
            if (result <= 0)
            return ServerResponse.createByError("上传失败");
        }
        return ServerResponse.createBySuccess("上传成功");
    }
    public ServerResponse list(String dbId){
        if (StringUtils.isEmpty(dbId)) return ServerResponse.createByError("参数不能为空");
        List<NlDatatable> datatables = datatableMapper.selectByDbId(dbId);
        return ServerResponse.createBySuccess(datatables);
    }
    public ServerResponse getOne(String dtId){
        NlDatatable datatable = datatableMapper.selectByDtId(dtId);
        return ServerResponse.createBySuccess(datatable);
    }
}
