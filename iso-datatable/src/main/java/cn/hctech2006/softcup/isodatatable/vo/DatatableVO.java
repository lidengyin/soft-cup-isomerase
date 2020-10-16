package cn.hctech2006.softcup.isodatatable.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "数据表数据展示层模型对象")
public class DatatableVO {
    @ApiModelProperty(name = "dtId", value = "数据表编号")
    private String dtId;
    @ApiModelProperty(name = "dtName", value = "数据表名")
    private String dtName;
    @ApiModelProperty(name = "dtTime", value = "数据表创建时间")
    private String dtTime;

    public String getDtId() {
        return dtId;
    }

    public void setDtId(String dtId) {
        this.dtId = dtId;
    }

    public String getDtName() {
        return dtName;
    }

    public void setDtName(String dtName) {
        this.dtName = dtName;
    }

    public String getDtTime() {
        return dtTime;
    }

    public void setDtTime(String dtTime) {
        this.dtTime = dtTime;
    }
}
