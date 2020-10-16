package cn.hctech2006.softcup.isoanalyze.mapper;

import cn.hctech2006.softcup.isoanalyze.bean.NlAnalyzeSmall;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NlAnalyzeSmallMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlAnalyzeSmall record);

    NlAnalyzeSmall selectByPrimaryKey(Integer id);

    List<NlAnalyzeSmall> selectAll();

    int updateByPrimaryKey(NlAnalyzeSmall record);
}