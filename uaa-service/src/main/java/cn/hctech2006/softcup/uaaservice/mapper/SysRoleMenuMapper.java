package cn.hctech2006.softcup.uaaservice.mapper;

import cn.hctech2006.softcup.uaaservice.bean.SysRoleMenu;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    int insert(SysRoleMenu record);

    SysRoleMenu selectByPrimaryKey(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    List<SysRoleMenu> selectAll();

    int updateByPrimaryKey(SysRoleMenu record);
}