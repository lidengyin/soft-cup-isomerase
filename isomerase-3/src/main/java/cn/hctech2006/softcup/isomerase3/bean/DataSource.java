package cn.hctech2006.softcup.isomerase3.bean;

import lombok.Data;

@Data
public class DataSource {
    //数据库地址
    private String url ;
    //数据库用户名
    private String userName;
    //数据库密码
    private String password;
    //数据库类型, 暂时支持sqlServer, mysql
    private String dataBaseType;
    //数据库编号
    private String dataSourceId;

    private String datasourceName;


    public String getDatasourceName() {
        return datasourceName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
}
