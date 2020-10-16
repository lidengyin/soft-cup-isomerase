package cn.hctech2006.softcup.isodatatable.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "数据源服务层数据传输对象")
public class DatabaseServiceDTO {
    @ApiModelProperty(name = "dbId", value = "数据源唯一标识", required = true)
    private String dbId;
    @ApiModelProperty(name = "dbName", value = "数据源命名")
    private String dbName;
    @ApiModelProperty(name = "dbUrl", value = "数据源jdbc地址")
    private String dbUrl;
    @ApiModelProperty(name = "dbType", value = "数据库类型")
    private String dbType;
    @ApiModelProperty(name = "dbUser", value = "数据库用户名")
    private String dbUser;
    @ApiModelProperty(name = "dbTpassword", value = "数据库用户密码")
    private String dbTpassword;
    @ApiModelProperty(name = "dbDatabase", value = "数据源所属数据库名称")
    private String dbDatabase;

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

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbTpassword() {
        return dbTpassword;
    }

    public void setDbTpassword(String dbTpassword) {
        this.dbTpassword = dbTpassword;
    }

    public String getDbDatabase() {
        return dbDatabase;
    }

    public void setDbDatabase(String dbDatabase) {
        this.dbDatabase = dbDatabase;
    }
}
