package cn.hctech2006.softcup.datasource.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 创建数据源的数据库连接池之前判断数据库信息是否正确
 */
public class DBUtil {
    private final Logger logger = LoggerFactory.getLogger(DBUtil.class);
    private DatabaseType databaseType = null;
    private String username;
    private String password;
    private String url;
    public static final String MYSQLDRIVER="com.mysql.cj.jdbc.Driver";
    public static final String SQLSERVERDRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String POSTGRESQLDRIVER="org.postgresql.Driver";


    public DBUtil(DatabaseType databaseType, String username, String password, String url) {
        this.databaseType = databaseType;
        this.username = username;
        this.password = password;
        this.url = url;
        forName();
    }

    /**
     * 驱动判断
     */
    private void forName(){
        try{
            if (databaseType == null){
                throw new RuntimeException("没有指定数据库类型");
            }
            if (databaseType == DatabaseType.MYSQL){
                Class.forName(MYSQLDRIVER).newInstance();
            }
            if (databaseType == DatabaseType.SQLSERVER){
                Class.forName(SQLSERVERDRIVER).newInstance();
            }if (databaseType == DatabaseType.POSTGRESQL){
                Class.forName(POSTGRESQLDRIVER);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("加载驱动失败");
        }
    }


    public Connection testConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                url, username, password
        );
        return connection;
    }
    /**
     * 获取连接对象
     * @return
     * @throws SQLException
     */
    public Connection getConnection()  {
      try{
          Connection connection = DriverManager.getConnection(
                  url, username, password
          );
          return connection;
      }catch (Exception e){
          e.printStackTrace();
          return null;
      }


    }

    /**
     * 连接是否正常
     * @param connection
     * @return
     * @throws SQLException
     */
    public boolean  connlsOk(Connection connection) throws SQLException {
        if (connection != null && connection.isClosed()){
            return true;
        }
        return false;
    }

    public void closeConn(Connection connection){
        try{
            if (connection != null){
                connection.close();
                connection = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public enum DatabaseType{
        MYSQL, OEACLE, SQLSERVER,POSTGRESQL
    }


}
