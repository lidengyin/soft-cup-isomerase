package cn.hctech2006.softcup.isomerase3.mapper;


import cn.hctech2006.softcup.isomerase3.bean.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUserRole record);

    SysUserRole selectByPrimaryKey(@Param("userId") Long userId, @Param("roleId") Long roleId);

    List<SysUserRole> selectAll();

    int updateByPrimaryKey(SysUserRole record);
}