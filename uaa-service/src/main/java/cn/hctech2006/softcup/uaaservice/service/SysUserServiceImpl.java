package cn.hctech2006.softcup.uaaservice.service;

import cn.hctech2006.softcup.uaaservice.bean.SysMenu;
import cn.hctech2006.softcup.uaaservice.bean.SysUser;
import cn.hctech2006.softcup.uaaservice.mapper.SysMenuMapper;
import cn.hctech2006.softcup.uaaservice.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysUserServiceImpl {
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysMenuServiceImpl menuService;

    /**
     * 根据用户名查找相关用户
     * @param username
     * @return
     */
    public SysUser findUserByName(String username){
        return userMapper.findByUserName(username);
    }

    /**
     * 根据用户名获取权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username){
        Set<String> perms = new HashSet<>();
        List<SysMenu> sysMenus = menuService.findMenusByUsername(username);
        for (SysMenu sysMenu : sysMenus){
            if (sysMenu.getPerms() != null && sysMenu.getPerms() != ""){
                System.out.println("perms: "+sysMenu.getPerms());
                perms.add(sysMenu.getPerms());
            }
        }
        return perms;
    }

}
