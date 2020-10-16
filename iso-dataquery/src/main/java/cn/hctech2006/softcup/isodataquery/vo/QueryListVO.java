package cn.hctech2006.softcup.isodataquery.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询实例列表数据展示层对象")
public class QueryListVO {
    @ApiModelProperty(name = "quName", value = "查询结果名称")
    private String quName;
    @ApiModelProperty(name = "quId", value = "查询实例编号")
    private String quId;
    @ApiModelProperty(name = "quTime", value = "查询时间")
    private String quTime;
    @ApiModelProperty(name = "quParams", value = "查询使用参数")
    private String quParams;

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

    public String getQuName() {
        return quName;
    }

    public void setQuName(String quName) {
        this.quName = quName;
    }

    public String getQuId() {
        return quId;
    }

    public void setQuId(String quId) {
        this.quId = quId;
    }
}
