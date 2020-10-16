package cn.hctech2006.softcup.datasource.bean;

public class DataSource {
    //数据库地址
    private String url ;
    //数据库用户名
    private String userName;
    //数据库密码
    private String password;
    //数据库类型, 暂时支持sqlServer, mysql,POSTGRESQL
    private String dataBaseType;
    //数据库编号
    private String dataSourceId;
    //数据源命名
    private String datasourceName;
    //数据库名
    private String databaseName;


    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

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
