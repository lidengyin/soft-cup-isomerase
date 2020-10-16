package cn.hctech2006.softcup.isouserservice.service;


import cn.hctech2006.softcup.isouserservice.bean.SysRole;
import cn.hctech2006.softcup.isouserservice.bean.SysRoleMenu;
import cn.hctech2006.softcup.isouserservice.page.PageRequest;
import cn.hctech2006.softcup.isouserservice.page.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysRoleService {

    int save(SysRole record);


    int delete(SysRole record);


    int delete(List<SysRole> records);


    SysRole findById(Long id);

    List<SysRole> findPage();

    int saveRoleAndMenu(SysRoleMenu sysRoleMenu);


}
