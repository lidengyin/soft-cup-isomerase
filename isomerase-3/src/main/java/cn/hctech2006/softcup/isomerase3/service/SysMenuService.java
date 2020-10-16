package cn.hctech2006.softcup.isomerase3.service;




import cn.hctech2006.softcup.isomerase3.bean.SysMenu;
import cn.hctech2006.softcup.isomerase3.page.PageRequest;
import cn.hctech2006.softcup.isomerase3.page.PageResult;

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
