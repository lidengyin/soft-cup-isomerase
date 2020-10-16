package cn.hctech2006.softcup.isoanalyze.mapper;

import cn.hctech2006.softcup.isoanalyze.bean.NlAnalyze;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NlAnalyzeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlAnalyze record);

    NlAnalyze selectByPrimaryKey(Integer id);

    List<NlAnalyze> selectAll();

    int updateByPrimaryKey(NlAnalyze record);

    NlAnalyze selectByAeId(String aeId);

    int updateByAeId(NlAnalyze analyze);

    List<NlAnalyze> selectByShowFlag(String showFlag);
}