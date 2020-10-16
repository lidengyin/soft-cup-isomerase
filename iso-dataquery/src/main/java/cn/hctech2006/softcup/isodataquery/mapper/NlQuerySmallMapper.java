package cn.hctech2006.softcup.isodataquery.mapper;

import cn.hctech2006.softcup.isodataquery.bean.NlQuerySmall;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NlQuerySmallMapper {
    int insert(NlQuerySmall record);

    List<NlQuerySmall> selectAll();
    List<NlQuerySmall> selectByQuId(String quId);

}