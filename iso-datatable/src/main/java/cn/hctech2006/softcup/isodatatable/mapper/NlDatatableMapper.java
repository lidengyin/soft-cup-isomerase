package cn.hctech2006.softcup.isodatatable.mapper;

import cn.hctech2006.softcup.isodatatable.bean.NlDatatable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NlDatatableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlDatatable record);

    NlDatatable selectByPrimaryKey(Integer id);

    List<NlDatatable> selectAll();

    int updateByPrimaryKey(NlDatatable record);

    NlDatatable selectByDbIdAndDtId(@Param("dbId") String dbId, @Param("dtId") String dtId);
    List<NlDatatable> selectByDbId(String dbId);
    NlDatatable selectByDtId(String dtId);
}