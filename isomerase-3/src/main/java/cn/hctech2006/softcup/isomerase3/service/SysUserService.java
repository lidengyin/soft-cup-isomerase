package cn.hctech2006.softcup.isomerase3.service;


import cn.hctech2006.softcup.isomerase3.bean.SysUser;
import cn.hctech2006.softcup.isomerase3.bean.SysUserRole;
import cn.hctech2006.softcup.isomerase3.jwt.UserLoginDTO;
import cn.hctech2006.softcup.isomerase3.page.PageRequest;
import cn.hctech2006.softcup.isomerase3.page.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysUserService extends CurdService<SysUser> {
    @Override
    int save(SysUser record);

    @Override
    int delete(SysUser record);

    @Override
    int delete(List<SysUser> records);

    @Override
    SysUser findById(Long id);

    @Override
    PageResult findPage(PageRequest pageRequest);

    @Override
    int update(SysUser record);

    @Override
    int update(List<SysUser> records);

    SysUser findByUsername(String username);

    UserLoginDTO login(String username, String password);

    int saveUserAndRole(SysUserRole sysUserRole);

    int deleteUserAndRole(Long userId);
}
