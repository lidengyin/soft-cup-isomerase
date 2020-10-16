package cn.hctech2006.softcup.isomerase3.mapper;


import cn.hctech2006.softcup.isomerase3.bean.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId);

    int insert(SysRoleMenu record);

    SysRoleMenu selectByPrimaryKey(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    List<SysRoleMenu> selectAll();

    int updateByPrimaryKey(SysRoleMenu record);


}