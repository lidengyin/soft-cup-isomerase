package cn.hctech2006.softcup.isouserservice.service.impl;


import cn.hctech2006.softcup.isouserservice.bean.SysMenu;
import cn.hctech2006.softcup.isouserservice.mapper.SysMenuMapper;
import cn.hctech2006.softcup.isouserservice.page.MybatisPageHelper;
import cn.hctech2006.softcup.isouserservice.page.PageRequest;
import cn.hctech2006.softcup.isouserservice.page.PageResult;
import cn.hctech2006.softcup.isouserservice.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public int save(SysMenu record) {
        return sysMenuMapper.insert(record);
    }

    @Override
    public int delete(SysMenu record) {
        return 0;
    }

    @Override
    public int delete(List<SysMenu> records) {
        return 0;
    }

    @Override
    public SysMenu findById(Long id) {
        return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        System.out.println("1");
        return MybatisPageHelper.findPage(pageRequest, sysMenuMapper, "selectAll", pageRequest.getParam("sysMenu"));
    }

    @Override
    public int update(SysMenu record) {
        return sysMenuMapper.updateByPrimaryKey(record);
    }

    @Override
    public int update(List<SysMenu> records) {
        return 0;
    }
}

