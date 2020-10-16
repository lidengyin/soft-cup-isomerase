package cn.hctech2006.softcup.uaaservice.mapper;

import cn.hctech2006.softcup.uaaservice.bean.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    List<SysUser> selectAll();

    int updateByPrimaryKey(SysUser record);
    SysUser findByUserName(String username);
}