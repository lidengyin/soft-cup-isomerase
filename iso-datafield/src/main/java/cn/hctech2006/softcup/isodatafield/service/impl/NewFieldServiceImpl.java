package cn.hctech2006.softcup.isodatafield.service.impl;

import cn.hctech2006.softcup.isodatafield.bean.NlField;
import cn.hctech2006.softcup.isodatafield.common.ServerResponse;
import cn.hctech2006.softcup.isodatafield.service.DataTableService;
import cn.hctech2006.softcup.isodatafield.service.DynamicService;
import cn.hctech2006.softcup.isodatafield.vo.FieldVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NewFieldServiceImpl {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private DynamicService dynamicService;
    @Autowired
    private DataTableService dataTableService;
    @Autowired
    private DataFieldServiceImpl dataFieldService;
    private Logger logger = LoggerFactory.getLogger(NewFieldServiceImpl.class);
    /**
     * 获取数据表全部字段
     * @param dtId
     * @return
     * @throws Exception
     */
    public ServerResponse getFields(String dtId) {
        if (StringUtils.isEmpty(dtId)) return ServerResponse.createByError("参数不能为空");
        ServerResponse response = dataTableService.getOne(dtId);
        if (!response.isSuccess()) return ServerResponse.createByError("该数据表不存在");
        Map<String, String> dtMap= (Map<String, String>) response.getData();
        String dbId = dtMap.get("dbId");
        String dtName = dtMap.get("dtName");
        //Map<String, String> fields = (Map<String, String>) dynamicService.getFields(dtName, dbId).getData();
        String[][] fields = objectToFieldSerilizable(dynamicService.getFields(dtName, dbId));
        //对数据表字段进行持久化
        response = storageDataField(fields,dbId,dtId);
        if (!response.isSuccess()) return response;
        logger.info("------------------当前数据源数据表字段持久化成功-----------------------");
        return response;
    }
    private String[][] objectToFieldSerilizable(ServerResponse response){
        ObjectMapper mapper=new ObjectMapper();
        String[][] field = mapper.convertValue(response.getData(), String[][].class);
        return field;
    }
    /**
     * 服务间调用-获取数据表的某一个字段
     * @param fdId
     * @return
     */
    public ServerResponse getOne(String fdId){
        return dataFieldService.getOne(fdId);

    }

    /**
     * 服务间调用添加字段类型
     * @param fdId
     * @return
     */
    public ServerResponse getOneAddMlType(String fdId){
        return dataFieldService.getOne(fdId);

    }


    /**
     * 数据表字段持久化
     * @param result
     * @param dbId
     * @param dtId
     * @return
     */
    private ServerResponse storageDataField(String[][] result, String dbId, String dtId){
        List<NlField> fields = new ArrayList<>();
        for (int i = 0; i < result.length; i ++){
            NlField field = new NlField();
            field.setDbId(dbId);
            field.setDtId(dtId);
            field.setFdId(dtId+DigestUtils.md5DigestAsHex(result[i][0].getBytes()));
            field.setFdName(result[i][0]);
            field.setFdComment(result[i][1]);
            field.setFdType(result[i][2]);
            field.setFdTime(new Date());
            field.setShowFlag("1");
            fields.add(field);
        }

        ServerResponse response = dataFieldService.upload(fields);
        List<FieldVO> fieldVOS = new ArrayList<>();
        for (NlField field : fields){

            FieldVO fieldVO = fieldPOJOToFieldVO(field);
            System.out.println("name: "+fieldVO.getFdName());
            fieldVOS.add(fieldVO);
        }
        if (response.isSuccess()) return ServerResponse.createBySuccess(fieldVOS);
        return response;
    }

    /**
     * 判断字段类型
     * @param fdType
     * @return
     */
    private String judgefdType(String fdType){

        if (fdType.equalsIgnoreCase("varchar") || fdType.contains("character")){
            return "字符串";
        }else if(fdType.equalsIgnoreCase("int")
                || fdType.equalsIgnoreCase("long")
                || fdType.equalsIgnoreCase("double")
                || fdType.equalsIgnoreCase("float")
                || fdType.equalsIgnoreCase("integer")){
            return "数字";
        }else if(fdType.equalsIgnoreCase("datetime")
               || fdType.equalsIgnoreCase("date")){
            return "时间";
        }else if (fdType.equalsIgnoreCase("boolean")){
            return "布尔";
        }else return null;
    }
    private FieldVO fieldPOJOToFieldVO(NlField field){
        FieldVO fieldVO = new FieldVO();
        fieldVO.setFdId(field.getFdId());
        fieldVO.setFdComment(field.getFdComment());
        fieldVO.setFdName(field.getFdName());
        fieldVO.setFdTime(sdf.format(field.getFdTime()));
        fieldVO.setFdType(judgefdType(field.getFdType()));
        return fieldVO;
    }



}
