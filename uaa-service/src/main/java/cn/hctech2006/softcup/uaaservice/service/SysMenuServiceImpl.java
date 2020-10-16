package cn.hctech2006.softcup.uaaservice.service;

import cn.hctech2006.softcup.uaaservice.bean.SysMenu;
import cn.hctech2006.softcup.uaaservice.mapper.SysMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    public List<SysMenu> findMenusByUsername(String username){
        return sysMenuMapper.selectMenusByUsername(username);
    }
}
