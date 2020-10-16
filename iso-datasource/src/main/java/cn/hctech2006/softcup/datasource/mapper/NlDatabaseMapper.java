package cn.hctech2006.softcup.datasource.mapper;

import cn.hctech2006.softcup.datasource.bean.NlDatabase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NlDatabaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlDatabase record);

    NlDatabase selectByPrimaryKey(Integer id);

    List<NlDatabase> selectAll();

    int updateByPrimaryKey(NlDatabase record);

    NlDatabase selectByDbId(String dbId);

    List<NlDatabase> selectByDbTypeAndDbDatabase(@Param("dbType") String dbType, @Param("dbDatabase") String dbDatabase);

    int deleteByDbId(String dbId);
}