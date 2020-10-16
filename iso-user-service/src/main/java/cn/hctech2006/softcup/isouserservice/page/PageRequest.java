package cn.hctech2006.softcup.isouserservice.page;

import java.util.HashMap;
import java.util.Map;

public class PageRequest {
    /**
     * 当前页码
     */
    private int pageNum = 1;
    /**
     * 每页数量
     */
    private int pageSize = 10;

    public PageRequest(int pageNum, int pageSize, Map<String, Object> params) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.params = params;
    }

    /**
     * 查询参数
     */

    private Map<String, Object> params = new HashMap<>();

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
    public Map<String, Object> getParams() {
        return params;
    }
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
    public Object getParam(String key) {
        return getParams().get(key);
    }

}