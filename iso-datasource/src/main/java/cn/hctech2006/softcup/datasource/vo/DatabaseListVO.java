package cn.hctech2006.softcup.datasource.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "数据源列表数据展示层模型")
public class DatabaseListVO {
    @ApiModelProperty(name = "databaseVOS", value = "数据源列表单个数据展示层列表")
    private List<DatabaseVO> databaseVOS;
    @ApiModelProperty(name = "pageNum", value = "当前页码")
    private int pageNum;
    @ApiModelProperty(name = "pageSize", value = "当前页行")
    private int pageSize;
    @ApiModelProperty(name = "size", value = "每页行数")
    private int size;
    @ApiModelProperty(name = "total", value = "总行数")
    private long total;
    @ApiModelProperty(name = "pages", value = "总页数")
    private int pages;
    @ApiModelProperty(name = "navigatepageNums", value = "导航栏页码数")
    private int[] navigatepageNums;
    @ApiModelProperty(name = "navigateFirstPage", value = "首页")
    private int navigateFirstPage;
    @ApiModelProperty(name = "navigateFirstPage", value = "尾页")
    private int navigateLastPage;

    public List<DatabaseVO> getDatabaseVOS() {
        return databaseVOS;
    }

    public void setDatabaseVOS(List<DatabaseVO> databaseVOS) {
        this.databaseVOS = databaseVOS;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int[] getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }
}
