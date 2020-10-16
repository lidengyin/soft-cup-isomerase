package cn.hctech2006.softcup.datasource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "数据源数据传输层模型")
public class DatabaseDTO {
    @ApiModelProperty(name = "host", value = "主机地址", required = true,example = "localhost")
    private String host;
    @ApiModelProperty(name = "dbType", value = "数据库类型", required = true, example = "mysql")
    private String dbType;
    @ApiModelProperty(name = "dbUser", value = "数据库用户名", required = true, example = "root")
    private String dbUser;
    @ApiModelProperty(name = "dbPassword", value = "数据库用户密码", required = true, example = "123456")
    private String dbPassword;
    @ApiModelProperty(name = "dbTable", value = "数据库名", required = true, example = "soft_cup_1")
    private String dbTable;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbTable() {
        return dbTable;
    }

    public void setDbTable(String dbTable) {
        this.dbTable = dbTable;
    }
}
