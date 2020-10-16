package cn.hctech2006.softcup.uaaservice.mapper;

import cn.hctech2006.softcup.uaaservice.bean.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Mapper
public interface SysMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysMenu record);

    SysMenu selectByPrimaryKey(Long id);

    List<SysMenu> selectAll();

    int updateByPrimaryKey(SysMenu record);

    List<SysMenu> selectMenusByUsername(String username);
}