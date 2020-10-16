package cn.hctech2006.softcup.isomerase3.service;
import cn.hctech2006.softcup.isomerase3.bean.SysDept;
import cn.hctech2006.softcup.isomerase3.page.PageRequest;
import cn.hctech2006.softcup.isomerase3.page.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysDeptService extends CurdService<SysDept>{
    @Override
    int save(SysDept record);

    @Override
    int delete(SysDept record);

    @Override
    int delete(List<SysDept> records);

    @Override
    SysDept findById(Long id);

    @Override
    PageResult findPage(PageRequest pageRequest);

    List<SysDept> findRootTree();

    List<SysDept> findByParentId(Long parentId);
}
