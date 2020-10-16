package cn.hctech2006.softcup.isomerase3.dynamic;


import cn.hctech2006.softcup.isomerase3.bean.DataSource;
import cn.hctech2006.softcup.isomerase3.datasource.DBContextHolder;
import cn.hctech2006.softcup.isomerase3.util.DBUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.stat.DruidDataSourceStatManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private boolean debug = false;
    private final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
    private Map<Object, Object> dynamicTargetDataSources;
    private Object dynamicDefaultTargetDataSource;

    /**
     * 设置当前数据源, 动态切换
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = DBContextHolder.getDataSource();
        // todo 这个是要继续加注释
        if (debug){
            //如果当前数据源不为空
            if (dataSource != null && !dataSource.equals("")){
                Map<Object, Object> dynamicTargetDataSource2 = this.dynamicTargetDataSources;
                //如果当前数据源中包含目标数据源
                if (dynamicTargetDataSource2.containsKey(dataSource)){
                    logger.info("---------当前数据源: "+ dataSource+"--------");
                }else{
                    throw new RuntimeException("不存在的数据源: "+dataSource);
                }
            }//如果数据源为空,. 依旧使用默认数据源
            else{
                logger.info("----当前数据源: 默认数据源----");
            }
        }
        return dataSource;
    }

    /**
     * 设置目标动态数据源库
     * @param targetDataSources
     */
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.dynamicTargetDataSources=targetDataSources;
    }

    /**
     * 设置主数据员
     * @param defaultTargetDataSource
     */
    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        this.dynamicDefaultTargetDataSource=defaultTargetDataSource;
    }

    /**
     * 创建动态数据源
     * @param key
     * @param driverClass
     * @param url
     * @param username
     * @param password
     * @param databaseType
     * @return
     * @throws SQLException
     */
    public boolean createDataSource(String key,String driverClass,  String url , String username
            , String password, String databaseType) throws SQLException {
        try {
            try {
                //排除连接失败的错误
                Class.forName(driverClass);
                //连接数据库
                DriverManager.getConnection(url,username,password);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                return false;
            }
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setName(key);
            druidDataSource.setUsername(username);
            druidDataSource.setPassword(password);
            druidDataSource.setUrl(url);
            //初始化建立的物理连接数
            druidDataSource.setInitialSize(50);
            //设置最大连接池数量
            druidDataSource.setMaxActive(200);
            //单位毫秒, 是1分钟
            druidDataSource.setMaxWait(60000);
            //最小连接池数量
            druidDataSource.setMinIdle(40);
            String validationQuery = "select 1";
            if ("mysql".equalsIgnoreCase(databaseType)){
                driverClass = DBUtil.MYSQLDRIVER;
                validationQuery = "select 1";
            }else if("sqlserver".equalsIgnoreCase(DBUtil.SQLSERVERDRIVER)){
                driverClass = DBUtil.SQLSERVERDRIVER;
                validationQuery="select 1";
            }
            //申请连接时执行validateQuery监测连接是否有效
            druidDataSource.setTestOnBorrow(true);
            //申请在连接时监测, 如果空闲时间大于TimeBetweenEvictionRUnsMillis
            // ,执行validationQuery,监测连接是否有效
            druidDataSource.setTestWhileIdle(true);
            //自动做保活连接
            druidDataSource.setKeepAlive(true);
            druidDataSource.setRemoveAbandonedTimeout(3600);
            druidDataSource.setRemoveAbandoned(true);
            druidDataSource.setFilters("stat, wall");
            druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
            druidDataSource.setMinEvictableIdleTimeMillis(180000);

            DruidDataSource createDataSource =  druidDataSource;
            druidDataSource.init();
            Map<Object, Object> dynamicTargetDataSources_temp = this.dynamicTargetDataSources;
            dynamicTargetDataSources_temp.put(key, createDataSource);
            setTargetDataSources(dynamicTargetDataSources_temp);
            super.afterPropertiesSet();
            logger.info("数据源初始化成功");
            return true;
        }catch (Exception e){
            //e.printStackTrace();
            logger.error(e+"");
            return false;
        }
    }

    /**
     * 数据源删除
     * @param dataSourceId
     * @return
     */
    public boolean delDataSource(String dataSourceId){
        Map<Object, Object> dynamicTargetDataSource2 = this.dynamicTargetDataSources;
        if (dynamicTargetDataSource2.containsKey(dataSourceId)){
            Set<DruidDataSource> druidDataSourceINstances = DruidDataSourceStatManager.getDruidDataSourceInstances();
            for (DruidDataSource term : druidDataSourceINstances){
                if (dataSourceId.equalsIgnoreCase(term.getName())){
                    dynamicTargetDataSource2.remove(dataSourceId);
                    DruidDataSourceStatManager.removeDataSource(term);
                    setTargetDataSources(dynamicTargetDataSource2);
                    super.afterPropertiesSet();
                    return true;
                }
            }
        }
        //删除失败, 当亲没有这个数据源
        return false;
    }

    /**
     * 监测当前数据源是否有效
     * @param key
     * @param url
     * @param driverClass
     * @param username
     * @param password
     * @param databaseType
     * @return
     */
    public boolean testDataSource(String key, String url , String driverClass, String username
            , String password, String databaseType){
        try{
            Class.forName(driverClass);
            DriverManager.getConnection(url ,username, password);
            return true;
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            return false;
        }
    }


    public void createDataSourceWithCheck(DataSource dataSource) throws Exception {
        String dataSourceId = dataSource.getDataSourceId();
        logger.info("准备创建数据源, 地址"+dataSourceId);
        Map<Object, Object> dynamicTargetDataSources2 = this.dynamicTargetDataSources;
        if (dynamicTargetDataSources2.containsKey(dataSourceId)){
            logger.info("数据源"+dataSourceId+"之前已经创建, 准备测试数据源是否正常");
            DruidDataSource druidDataSource = (DruidDataSource) dynamicTargetDataSources2.get(dataSourceId);

            //数据源正常标志
            boolean rightFlag = true;
            Connection connection = null;
            try{
                logger.info("准备获取数据库链接");
                connection = druidDataSource.getConnection();
                logger.info("数据源"+dataSourceId+"正常");
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.getMessage(), e);
                rightFlag = false;
                logger.info("缓存数据源"+dataSourceId+"已失效, 准备删除");
                if (delDataSource(dataSourceId)){
                    logger.info("缓存数据源删除成功");
                }else{
                    logger.info("缓存数据源删除失败");
                }
            }finally {
                if (connection != null){
                    connection.close();
                }
            }
            if (rightFlag){
                logger.info("不需要创建新的数据源");
                return;
            }else{
                logger.info("准备重新创建数据源....");
                createDataSource(dataSource);
                logger.info("重新创建数据源成功");
            }
        }else{

            createDataSource(dataSource);
        }
    }
    public void createDataSource(DataSource dataSource) throws Exception {
        String dataSourceId = dataSource.getDataSourceId();
        String url = dataSource.getUrl();
        logger.info("准备创建数据源"+dataSourceId);
        String databaseType = dataSource.getDataBaseType();
        String username = dataSource.getUserName();
        String password = dataSource.getPassword();
        //String url = dataSource.getUrl();
        String driverClass = "";
        if ("mysql".equalsIgnoreCase(databaseType)){
            driverClass=DBUtil.MYSQLDRIVER;
        }else if("sqlserver".equalsIgnoreCase(databaseType)){
            driverClass=DBUtil.SQLSERVERDRIVER;
        }
        //如果数据源连接测试正常
        if (testDataSource(dataSourceId, url, driverClass, username,password,databaseType)){
            boolean result = this.createDataSource(dataSourceId, driverClass, url, username, password, databaseType);
            if (!result){
                throw  new Exception("数据源"+dataSourceId+"配置正确, 但是创建失败");
            }
        }else {
            throw  new Exception("数据源配置有误, 请重新配置");
        }
    }

    public List<DataSource> datasourceList(){
        Map<Object, Object> dynamicTargetDataSources = this.dynamicTargetDataSources;
        List<DataSource> dataSources = new ArrayList<>();
        for (Map.Entry<Object, Object> term  : dynamicTargetDataSources.entrySet()){
            DataSource dataSource = new DataSource();
            System.out.println("key: "+ term.getKey());
            System.out.println("value: "+ term.getValue());
            DruidDataSource druidDataSource = (DruidDataSource) term.getValue();
            System.out.println("value: "+ druidDataSource.getUrl());
            dataSource.setUserName(druidDataSource.getUsername());
            dataSource.setPassword(druidDataSource.getPassword());
            dataSource.setDatasourceName(druidDataSource.getName());
            dataSource.setUrl(druidDataSource.getUrl());
            dataSource.setDataSourceId((String) term.getKey());
            dataSources.add(dataSource);
        }
        return dataSources;
    }
    public DataSource queryDataSourcebydataSourceId(String dataSourceId){
        Map<Object, Object> dynamicTargetDataSources = this.dynamicTargetDataSources;
        if (dynamicTargetDataSources.containsKey(dataSourceId)){
            DruidDataSource druidDataSource = (DruidDataSource) dynamicTargetDataSources.get(dataSourceId);
            DataSource dataSource = new DataSource();
            dataSource.setUserName(druidDataSource.getUsername());
            dataSource.setPassword(druidDataSource.getPassword());
            dataSource.setDatasourceName(druidDataSource.getName());
            dataSource.setUrl(druidDataSource.getUrl());
            dataSource.setDataSourceId(dataSourceId);
            logger.info("数据源唯一编码: "+dataSource.getDataSourceId());
            return dataSource;
        }
        return new DataSource();
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Map<Object, Object> getDynamicTargetDataSources() {
        return dynamicTargetDataSources;
    }

    public void setDynamicTargetDataSources(Map<Object, Object> dynamicTargetDataSources) {
        this.dynamicTargetDataSources = dynamicTargetDataSources;
    }

    public Object getDynamicDefaultTargetDataSource() {
        return dynamicDefaultTargetDataSource;
    }

    public void setDynamicDefaultTargetDataSource(Object dynamicDefaultTargetDataSource) {
        this.dynamicDefaultTargetDataSource = dynamicDefaultTargetDataSource;
    }
}
