package cn.hctech2006.softcup.uaaservice.security;

import cn.hctech2006.softcup.uaaservice.bean.SysUser;
import cn.hctech2006.softcup.uaaservice.service.SysMenuServiceImpl;
import cn.hctech2006.softcup.uaaservice.service.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class JwtUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserServiceImpl sysUserService;
    @Autowired
    private SysMenuServiceImpl sysMenuService;
    //唯一函数根据用户名过去用户信息源以及用户权限信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser sysUser = sysUserService.findUserByName(username);
        if (sysUser == null){
            throw new UsernameNotFoundException("用户没有找到");
        }
        Set<String> perms = sysUserService.findPermissions(username);
        //权限封装
        List<GrantedAuthority> authorities =
                perms.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());
        return new JwtUserDetails(sysUser.getName(), sysUser.getPassword(), authorities);
    }
}
