package cn.hctech2006.softcup.isomerase3.mapper;

import cn.hctech2006.softcup.isomerase3.bean.NlCminfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NlCminfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlCminfo record);

    NlCminfo selectByPrimaryKey(Integer id);

    List<NlCminfo> selectAll();

    int updateByPrimaryKey(NlCminfo record);
}