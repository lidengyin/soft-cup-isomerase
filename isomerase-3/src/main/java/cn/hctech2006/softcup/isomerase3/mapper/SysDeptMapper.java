package cn.hctech2006.softcup.isomerase3.mapper;

import cn.hctech2006.softcup.isomerase3.bean.SysDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDept record);

    SysDept selectByPrimaryKey(Long id);

    List<SysDept> selectAll(SysDept sysDept);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> selectDeptByArticleId(Long articleId);

    List<SysDept> selectDeptByParentId(Long parentId);

}