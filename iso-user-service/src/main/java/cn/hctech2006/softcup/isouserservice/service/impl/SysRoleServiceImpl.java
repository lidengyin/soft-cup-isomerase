package cn.hctech2006.softcup.isouserservice.service.impl;


import cn.hctech2006.softcup.isouserservice.bean.SysRole;
import cn.hctech2006.softcup.isouserservice.bean.SysRoleMenu;
import cn.hctech2006.softcup.isouserservice.mapper.SysRoleMapper;
import cn.hctech2006.softcup.isouserservice.mapper.SysRoleMenuMapper;
import cn.hctech2006.softcup.isouserservice.page.MybatisPageHelper;
import cn.hctech2006.softcup.isouserservice.page.PageRequest;
import cn.hctech2006.softcup.isouserservice.page.PageResult;
import cn.hctech2006.softcup.isouserservice.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Override
    public int save(SysRole record) {
        return sysRoleMapper.insert(record);
    }

    @Override
    public int delete(SysRole record) {
        return 0;
    }

    @Override
    public int delete(List<SysRole> records) {
        return 0;
    }

    @Override
    public SysRole findById(Long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }


    public List<SysRole> findPage() {

        return  sysRoleMapper.selectAll();
    }

    @Override
    public int saveRoleAndMenu(SysRoleMenu record) {
        return sysRoleMenuMapper.insert(record);
    }


    public int updateRoleAndMenuDelFlag(Long roleId) {
        return sysRoleMenuMapper.deleteByPrimaryKey(roleId);
    }


    public int update(SysRole record) {
        return sysRoleMapper.updateByPrimaryKey(record);
    }


    public int update(List<SysRole> records) {
        return sysRoleMapper.updateByPrimaryKey(records);
    }
}
