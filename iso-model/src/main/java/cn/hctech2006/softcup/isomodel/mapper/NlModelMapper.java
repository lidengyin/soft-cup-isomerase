package cn.hctech2006.softcup.isomodel.mapper;

import cn.hctech2006.softcup.isomodel.bean.NlModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NlModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlModel record);

    NlModel selectByPrimaryKey(Integer id);

    List<NlModel> selectAll();

    int updateByPrimaryKey(NlModel record);

    NlModel selectModelByMlName(String mlName);

    List<NlModel> selectByMlAlgoAndMlResultAndShowFlag(@Param("mlAlgo") String mlAlgo, @Param("mlResult") String mlResult, @Param("showFlag") String showFlag);

    NlModel selectByMlId(String mlId);
    int updateByMlId(NlModel model);


}