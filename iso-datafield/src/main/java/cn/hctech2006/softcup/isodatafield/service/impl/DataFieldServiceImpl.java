package cn.hctech2006.softcup.isodatafield.service.impl;

import cn.hctech2006.softcup.isodatafield.bean.NlField;
import cn.hctech2006.softcup.isodatafield.common.ServerResponse;
import cn.hctech2006.softcup.isodatafield.mapper.NlFieldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
public class DataFieldServiceImpl {
    @Autowired
    private NlFieldMapper fieldMapper;
    @Transactional(propagation = Propagation.REQUIRED)
    /**
     * 数据表字段上传
     */
    public ServerResponse upload(List<NlField> fields){
        for (NlField param : fields){
            NlField field = fieldMapper.selectByFdId(param.getFdId());
            if (field != null) {
                int result = fieldMapper.updateByFlId(field);
                if (result <= 0) return ServerResponse.createByError();
                continue;
            }
            int result = fieldMapper.insert(param);
            if (result <= 0) return ServerResponse.createByError("上传失败");
        }
        return ServerResponse.createBySuccess("上传成功");
    }

//    public ServerResponse update(NlField field){
//
//    }

    /**
     * 数据字段列表展示
     * @param dtId
     * @return
     */
    public ServerResponse list(String dtId){
        if (StringUtils.isEmpty(dtId)) return ServerResponse.createBySuccess("参数不能为空");
            List<NlField>  fields = fieldMapper.selectByDtId(dtId);
            return ServerResponse.createBySuccess(fields);
    }


    public ServerResponse getOne(String fdId){
        NlField field = fieldMapper.selectByFdId(fdId);
        return ServerResponse.createBySuccess(field);
    }



}
