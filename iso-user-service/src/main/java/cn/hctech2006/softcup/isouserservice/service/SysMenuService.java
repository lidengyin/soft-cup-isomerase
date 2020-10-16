package cn.hctech2006.softcup.isouserservice.service;




import cn.hctech2006.softcup.isouserservice.bean.SysMenu;
import cn.hctech2006.softcup.isouserservice.page.PageRequest;
import cn.hctech2006.softcup.isouserservice.page.PageResult;

import java.util.List;

public interface SysMenuService extends CurdService<SysMenu> {
    @Override
    int save(SysMenu record);

    @Override
    int delete(SysMenu record);

    @Override
    int delete(List<SysMenu> records);

    @Override
    SysMenu findById(Long id);

    @Override
    PageResult findPage(PageRequest pageRequest);
}
