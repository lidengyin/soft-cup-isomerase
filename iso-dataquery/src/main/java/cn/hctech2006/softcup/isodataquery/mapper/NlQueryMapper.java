package cn.hctech2006.softcup.isodataquery.mapper;

import cn.hctech2006.softcup.isodataquery.bean.NlQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NlQueryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlQuery record);

    NlQuery selectByPrimaryKey(Integer id);

    List<NlQuery> selectAll();

    int updateByPrimaryKey(NlQuery record);

    NlQuery selectByQuId(String quId);

    int updateQuUrlByQuId(@Param("quId") String quId, @Param("quUrl") String quUrl);

    List<NlQuery> selectByShowFlag(String showFlag);


}