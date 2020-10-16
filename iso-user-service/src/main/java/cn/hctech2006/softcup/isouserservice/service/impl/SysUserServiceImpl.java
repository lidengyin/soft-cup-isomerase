package cn.hctech2006.softcup.isouserservice.service.impl;



import cn.hctech2006.softcup.isouserservice.bean.SysUser;
import cn.hctech2006.softcup.isouserservice.bean.SysUserRole;
import cn.hctech2006.softcup.isouserservice.exception.UserLoginException;
import cn.hctech2006.softcup.isouserservice.jwt.JWT;
import cn.hctech2006.softcup.isouserservice.jwt.UserLoginDTO;
import cn.hctech2006.softcup.isouserservice.jwt.fegin.AuthServiceClient;
import cn.hctech2006.softcup.isouserservice.mapper.SysUserMapper;
import cn.hctech2006.softcup.isouserservice.mapper.SysUserRoleMapper;
import cn.hctech2006.softcup.isouserservice.page.MybatisPageHelper;
import cn.hctech2006.softcup.isouserservice.page.PageRequest;
import cn.hctech2006.softcup.isouserservice.page.PageResult;
import cn.hctech2006.softcup.isouserservice.service.SysUserService;
import cn.hctech2006.softcup.isouserservice.util.PassWordEncoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public int save(SysUser record) {
        return sysUserMapper.insert(record);
    }

    @Override
    public int delete(SysUser record) {
        return 0;
    }

    @Override
    public int delete(List<SysUser> records) {
        return 0;
    }

    @Override
    public SysUser findById(Long id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MybatisPageHelper.findPage(pageRequest, sysUserMapper, "selectAll", pageRequest.getParam("sysUser"));
    }

    @Override
    public int update(SysUser record) {
        return sysUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public int update(List<SysUser> records) {
        return 0;
    }

    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.findByUsername(username);
    }
    @Autowired
    AuthServiceClient authServiceClient;
    @Override
    public UserLoginDTO login(String username, String password) {
        SysUser sysUser = sysUserMapper.findByUsername(username);
        if(sysUser == null){
            throw new UserLoginException("该用户不存在");

        }
        System.out.println(PassWordEncoderUtils.matches(password, sysUser.getPassword()));
        System.out.println(sysUser.getPassword());
        if (!PassWordEncoderUtils.matches(password, sysUser.getPassword())){
            throw new UserLoginException("密码错误");
        }
        JWT jwt = authServiceClient.getToken("Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==","password",username,password );
        if (jwt == null){
            throw new UserLoginException("error Internal");
        }
        UserLoginDTO userLoginDTO = new UserLoginDTO(jwt, sysUser);
        return userLoginDTO;
    }

    @Override
    public int saveUserAndRole(SysUserRole sysUserRole) {
        return sysUserRoleMapper.insert(sysUserRole);
    }

    @Override
    public int deleteUserAndRole(Long userId) {
        return sysUserRoleMapper.deleteByPrimaryKey(userId);
    }
}
