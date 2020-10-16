package cn.hctech2006.softcup.isouserservice.service;





import cn.hctech2006.softcup.isouserservice.page.PageRequest;
import cn.hctech2006.softcup.isouserservice.page.PageResult;

import java.util.List;

/**
 * 通用CURD 接口
 * @param <T>
 */
public interface CurdService<T> {
    /**
     * 保存操作
     * @param record
     * @return
     */
    int save(T record);

    /**
     * 删除操作
     * @param record
     * @return
     */
    int delete(T record);

    /**
     * 批量删除操作
     * @param records
     * @return
     */
    int delete(List<T> records);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    T findById(Long id);

    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    PageResult findPage(PageRequest pageRequest);

    /**
     * 修改操作
     * @param record
     * @return
     */
    int update(T record);

    /**
     * 批量修改操作
     * @param records
     * @return
     */
    int update(List<T> records);

}
