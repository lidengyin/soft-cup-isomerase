package cn.hctech2006.softcup.isomerase3.mapper;


import cn.hctech2006.softcup.isomerase3.bean.NlField;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NlFieldMapper {
    int deleteByPrimaryKey(String filedId);

    int insert(NlField record);

    NlField selectByPrimaryKey(String filedId);

    List<NlField> selectAll();

    int updateByPrimaryKey(NlField record);

    List<NlField> selectByMainId(String mainId);

    String selectChartTypeByMainIdAndFieldName(@Param("mainId") String mainId, @Param("fieldName") String fieldName);
    String selectChartOptionByMainIdAndFieldName(@Param("mainId") String mainId, @Param("fieldName") String fieldName);

    List<String> selectFieldNameByMainId(String mainId);
    List<String> selectFieldValueByMainIdAndFieldName(@Param("mainId") String mainId, @Param("fieldName") String fieldName);
}