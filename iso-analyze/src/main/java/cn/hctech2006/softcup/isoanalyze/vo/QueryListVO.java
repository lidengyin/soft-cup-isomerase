package cn.hctech2006.softcup.isoanalyze.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询实例列表数据展示层对象")
public class QueryListVO {
    @ApiModelProperty(name = "quName", value = "查询结果名称")
    private String quName;
    @ApiModelProperty(name = "quId", value = "查询实例编号")
    private String quId;

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
