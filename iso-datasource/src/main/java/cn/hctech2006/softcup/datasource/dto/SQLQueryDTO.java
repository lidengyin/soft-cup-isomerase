package cn.hctech2006.softcup.datasource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@ApiModel(description = "field数据查询数据传输层模型")
public class SQLQueryDTO {
    @ApiModelProperty(name = "dbId", value = "数据源编号")
    private String dbId;
    @ApiModelProperty(name = "fdName", value = "数据源编号")
    private String fdName;
    @ApiModelProperty(name = "dtName", value = "数据表")
    private String dtName;
    @ApiModelProperty(name = "params", value = "查询使用参数")
    private Map<String, String> params;

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public String getDtName() {
        return dtName;
    }

    public void setDtName(String dtName) {
        this.dtName = dtName;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
