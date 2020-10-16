package cn.hctech2006.softcup.isouserservice.mapper;


import cn.hctech2006.softcup.isouserservice.bean.SysRoleDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleDeptMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("deptId") Long deptId);

    int insert(SysRoleDept record);

    SysRoleDept selectByPrimaryKey(@Param("roleId") Long roleId, @Param("deptId") Long deptId);

    List<SysRoleDept> selectAll();

    int updateByPrimaryKey(SysRoleDept record);
}