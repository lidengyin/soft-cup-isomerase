package cn.hctech2006.softcup.isomodel.mapper;

import cn.hctech2006.softcup.isomodel.bean.NlModelSmall;

import java.util.List;

public interface NlModelSmallMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlModelSmall record);

    NlModelSmall selectByPrimaryKey(Integer id);

    List<NlModelSmall> selectAll();

    int updateByPrimaryKey(NlModelSmall record);

    List<NlModelSmall> selectByMlId(String mlId);

    NlModelSmall selectByMsName(String msName);

    int deleteByMlId(String mlId);


}