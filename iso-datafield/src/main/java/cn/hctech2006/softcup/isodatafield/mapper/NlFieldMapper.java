package cn.hctech2006.softcup.isodatafield.mapper;

import cn.hctech2006.softcup.isodatafield.bean.NlField;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NlFieldMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlField record);

    NlField selectByPrimaryKey(Integer id);

    List<NlField> selectAll();

    int updateByPrimaryKey(NlField record);

    NlField selectByFdId(String fdId);

    List<NlField> selectByDtId(String dtId);

    int updateByFlId(NlField field);
}