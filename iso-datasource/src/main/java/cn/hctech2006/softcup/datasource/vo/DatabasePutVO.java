package cn.hctech2006.softcup.datasource.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "数据源修改数据传输层模型")
public class DatabasePutVO {
    @ApiModelProperty(name = "dbName", value = "数据源命名")
    private String dbName;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
