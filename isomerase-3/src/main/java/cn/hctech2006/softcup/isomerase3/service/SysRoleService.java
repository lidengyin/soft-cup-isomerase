package cn.hctech2006.softcup.isomerase3.service;


import cn.hctech2006.softcup.isomerase3.bean.SysRole;
import cn.hctech2006.softcup.isomerase3.bean.SysRoleMenu;
import cn.hctech2006.softcup.isomerase3.page.PageRequest;
import cn.hctech2006.softcup.isomerase3.page.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysRoleService extends CurdService<SysRole>{
    @Override
    int save(SysRole record);

    @Override
    int delete(SysRole record);

    @Override
    int delete(List<SysRole> records);

    @Override
    SysRole findById(Long id);

    @Override
    PageResult findPage(PageRequest pageRequest);

    int saveRoleAndMenu(SysRoleMenu sysRoleMenu);

    int updateRoleAndMenuDelFlag(Long roleId);

}
