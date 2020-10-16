package cn.hctech2006.softcup.isomerase3.mapper;


import cn.hctech2006.softcup.isomerase3.bean.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    List<SysRole> selectAll(SysRole sysRole);

    int updateByPrimaryKey(SysRole record);
    int updateByPrimaryKey(List<SysRole> record);
}