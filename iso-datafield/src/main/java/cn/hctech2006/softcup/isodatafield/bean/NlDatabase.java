package cn.hctech2006.softcup.isodatafield.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(description = "数据源POJO模型", value = "NlDatabase")
public class NlDatabase implements Serializable {
    @ApiModelProperty(hidden = true,example = "1")
    private Integer id;
    @ApiModelProperty(name = "dbId", value = "数据源唯一标识")
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
    @ApiModelProperty(name = "dbTime", value = "数据源创建时间")
    private Date dbTime;
    @ApiModelProperty(name = "dbDatabase", value = "数据源所属数据库名称")
    private String dbDatabase;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getDbTime() {
        return dbTime;
    }

    public void setDbTime(Date dbTime) {
        this.dbTime = dbTime;
    }

    public String getDbDatabase() {
        return dbDatabase;
    }

    public void setDbDatabase(String dbDatabase) {
        this.dbDatabase = dbDatabase;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", dbId=").append(dbId);
        sb.append(", dbName=").append(dbName);
        sb.append(", dbUrl=").append(dbUrl);
        sb.append(", dbType=").append(dbType);
        sb.append(", dbUser=").append(dbUser);
        sb.append(", dbTpassword=").append(dbTpassword);
        sb.append(", dbTime=").append(dbTime);
        sb.append(", dbDatabase=").append(dbDatabase);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}