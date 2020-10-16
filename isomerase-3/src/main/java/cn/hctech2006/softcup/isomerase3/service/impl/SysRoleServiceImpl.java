package cn.hctech2006.softcup.isomerase3.service.impl;


import cn.hctech2006.softcup.isomerase3.bean.SysRole;
import cn.hctech2006.softcup.isomerase3.bean.SysRoleMenu;
import cn.hctech2006.softcup.isomerase3.mapper.SysRoleMapper;
import cn.hctech2006.softcup.isomerase3.mapper.SysRoleMenuMapper;
import cn.hctech2006.softcup.isomerase3.page.MybatisPageHelper;
import cn.hctech2006.softcup.isomerase3.page.PageRequest;
import cn.hctech2006.softcup.isomerase3.page.PageResult;
import cn.hctech2006.softcup.isomerase3.service.SysRoleService;
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

    @Override
    public PageResult findPage(PageRequest pageRequest) {


        return  MybatisPageHelper.findPage(pageRequest,sysRoleMapper, "selectAll", pageRequest.getParam("sysRole"));
    }

    @Override
    public int saveRoleAndMenu(SysRoleMenu record) {
        return sysRoleMenuMapper.insert(record);
    }

    @Override
    public int updateRoleAndMenuDelFlag(Long roleId) {
        return sysRoleMenuMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public int update(SysRole record) {
        return sysRoleMapper.updateByPrimaryKey(record);
    }

    @Override
    public int update(List<SysRole> records) {
        return sysRoleMapper.updateByPrimaryKey(records);
    }
}
