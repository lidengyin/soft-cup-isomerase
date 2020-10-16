package cn.hctech2006.softcup.datasource.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "数据源列表单个数据展示层模型")
public class DatabaseVO {

        @ApiModelProperty(name = "dbId", value = "数据源唯一标识", required = true)
        private String dbId;
        @ApiModelProperty(name = "dbName", value = "数据源命名")
        private String dbName;

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
