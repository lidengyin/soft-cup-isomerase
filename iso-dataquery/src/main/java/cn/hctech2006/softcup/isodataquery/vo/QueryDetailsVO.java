package cn.hctech2006.softcup.isodataquery.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@ApiModel(description = "前段显示查询结果集数据显示层")
public class QueryDetailsVO {
    @ApiModelProperty(name = "quName", value = "查询结果名称")
    private String quName;
    @ApiModelProperty(name = "quTime", value = "查询时间")
    private String quTime;
    @ApiModelProperty(name = "quParams", value = "查询使用参数")
    private String quParams;
    @ApiModelProperty(name = "qsVOMap", value = "查询小项数据map集合展示层")
    private Map<String, QuerySmallVO> qsVOMap;
    @ApiModelProperty(name = "quUrl", value = "查询实例数据集在线地址")
    private String quUrl ;

    public Map<String, QuerySmallVO> getQsVOMap() {
        return qsVOMap;
    }

    public void setQsVOMap(Map<String, QuerySmallVO> qsVOMap) {
        this.qsVOMap = qsVOMap;
    }

    public String getQuName() {
        return quName;
    }

    public void setQuName(String quName) {
        this.quName = quName;
    }

    public String getQuTime() {
        return quTime;
    }

    public void setQuTime(String quTime) {
        this.quTime = quTime;
    }

    public String getQuParams() {
        return quParams;
    }

    public void setQuParams(String quParams) {
        this.quParams = quParams;
    }

    public String getQuUrl() {
        return quUrl;
    }

    public void setQuUrl(String quUrl) {
        this.quUrl = quUrl;
    }
}
