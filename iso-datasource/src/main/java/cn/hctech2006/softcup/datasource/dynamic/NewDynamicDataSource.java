package cn.hctech2006.softcup.datasource.dynamic;

import cn.hctech2006.softcup.datasource.bean.DataSource;
import cn.hctech2006.softcup.datasource.datasource.DBContextHolder;
import cn.hctech2006.softcup.datasource.dto.DatabaseServiceDTO;
import cn.hctech2006.softcup.datasource.util.DBUtil;
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

public class NewDynamicDataSource extends AbstractRoutingDataSource{

        private boolean debug = false;
        private final Logger logger = LoggerFactory.getLogger(NewDynamicDataSource.class);
        private Map<Object, Object> dynamicTargetDataSources;
        private Object dynamicDefaultTargetDataSource;

        /**
         * 设置当前数据源, 动态切换
         * @return
         */
        @Override
        protected Object determineCurrentLookupKey() {
            //获取当前主数据源的编号
            String dataSource = DBContextHolder.getDataSource();
            // todo 如果是初始化
            if (debug){
                //如果当前数据源不为空
                if (dataSource != null && !dataSource.equals("")){
                    Map<Object, Object> dynamicTargetDataSource2 = this.dynamicTargetDataSources;
                    //如果当前数据源中包含目标数据源
                    if (dynamicTargetDataSource2.containsKey(dataSource)){
                        logger.info("------------------------当前数据源: "+ dataSource+"------------------------");
                    }else{
                        throw new RuntimeException("不存在的数据源: "+dataSource);
                    }
                }//如果数据源为空,. 依旧使用默认数据源
                else{
                    logger.info("------------------------当前数据源: 默认数据源------------------------");
                }
            }
            logger.info("------------------------当前数据源: 默认数据源: "+dataSource+"------------------------");
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
                , String password, String databaseType) {
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
                druidDataSource.setDbType(databaseType);

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
                }else if("sqlserver".equalsIgnoreCase(databaseType)){
                    driverClass = DBUtil.SQLSERVERDRIVER;
                    validationQuery="select 1";
                }else if ("POSTGRESQL".equalsIgnoreCase(databaseType)){
                    driverClass = DBUtil.POSTGRESQLDRIVER;
                    validationQuery="select 1";
                }
                //申请连接时执行validateQuery监测连接是否有效
                druidDataSource.setTestOnBorrow(true);
                //申请在连接时监测, 如果空闲时间大于TimeBetweenEvictionRUnsMillis
                // ,执行validationQuery,监测连接是否有效
                druidDataSource.setTestWhileIdle(true);
                //自动做保活连接
                druidDataSource.setKeepAlive(true);
                druidDataSource.setValidationQuery(validationQuery);
                druidDataSource.setRemoveAbandonedTimeout(3600);
                druidDataSource.setRemoveAbandoned(true);
                druidDataSource.setFilters("stat, wall");
                druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
                druidDataSource.setMinEvictableIdleTimeMillis(180000);
                //数据源初始化
                DruidDataSource createDataSource =  druidDataSource;
                druidDataSource.init();
                Map<Object, Object> dynamicTargetDataSources_temp = this.dynamicTargetDataSources;
                //在原有缓存数据源基础上加上此数据源
                dynamicTargetDataSources_temp.put(key, createDataSource);
                //设置新数据源列表
                setTargetDataSources(dynamicTargetDataSources_temp);
                super.afterPropertiesSet();
                logger.info("------------------------数据源初始化成功------------------------");
                return true;
            }catch (Exception e){
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
         * @param url
         * @param driverClass
         * @param username
         * @param password
         * @return
         */
        public boolean testDataSource(String url , String driverClass, String username
                , String password){
            logger.info("--------------driverClass: "+driverClass+", userName: "+username+", password: "+password+", url: "+url+"---------------");
            try{
                Class.forName(driverClass);
                DriverManager.getConnection(url ,username, password);
                return true;
            }catch (SQLException | ClassNotFoundException e){
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 穿件数据源之前,监测数据源是否存在, 存在则直接使用, 不存在则创建
         * @param databaseServiceDTO
         * @throws Exception
         */
        public void createDataSourceWithCheck(DatabaseServiceDTO databaseServiceDTO) throws Exception {
            String dbId = databaseServiceDTO.getDbId();
            logger.info("-----------------------准备创建数据源, 数据源唯一标识: "+dbId+"------------------------");
            Map<Object, Object> dynamicTargetDataSources2 = this.dynamicTargetDataSources;
            if (dynamicTargetDataSources2.containsKey(dbId)){
                logger.info("-----------------------"+dbId+"号数据源之前已经创建, 准备测试数据源是否正常------------------------");
                DruidDataSource druidDataSource = (DruidDataSource) dynamicTargetDataSources2.get(dbId);

                //数据源正常标志
                boolean rightFlag = true;
                Connection connection = null;
                try{
                    logger.info("-----------------------准备获取数据库链接-----------------------");
                    connection = druidDataSource.getConnection();
                    logger.info("-----------------------数据源"+dbId+",正常-----------------------");
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                    rightFlag = false;
                    logger.info("-----------------------缓存数据源"+dbId+",已失效, 准备删除-----------------------");
                    if (delDataSource(dbId)){
                        logger.info("-----------------------缓存数据源删除成功-----------------------");
                    }else{
                        logger.info("-----------------------缓存数据源删除失败-----------------------");
                    }
                }finally {
                    if (connection != null){
                        connection.close();
                    }
                }
                if (rightFlag){
                    logger.info("-----------------------不需要创建新的数据源-----------------------");
                    return;
                }else{
                    logger.info("-----------------------准备重新创建数据源-----------------------");
                    createDataSource(databaseServiceDTO);
                    logger.info("重新创建数据源成功");
                }
            }else{

                createDataSource(databaseServiceDTO);
            }
        }
        public void createDataSource(DatabaseServiceDTO databaseServiceDTO) throws Exception {
            String dbId = databaseServiceDTO.getDbId();
            String url = databaseServiceDTO.getDbUrl();
            logger.info("-----------------------准备创建数据源"+dbId+"-----------------------");
            String dbType = databaseServiceDTO.getDbType();
            String dbUser = databaseServiceDTO.getDbUser();
            String dbPassword = databaseServiceDTO.getDbTpassword();
            String driverClass = "";
            if ("mysql".equalsIgnoreCase(dbType)){
                driverClass=DBUtil.MYSQLDRIVER;
            }else if("sqlserver".equalsIgnoreCase(dbType)){
                driverClass=DBUtil.SQLSERVERDRIVER;
            }else if("postgresql".equalsIgnoreCase(dbType)){
                driverClass=DBUtil.POSTGRESQLDRIVER;
            }
            //如果数据源连接测试正常
            if (testDataSource(url, driverClass, dbUser,dbPassword)){
                boolean result = this.createDataSource(dbId, driverClass, url, dbUser, dbPassword, dbType);
                if (!result){
                    logger.error("-----------------------数据源"+dbId+",配置正确, 但是创建失败-----------------------");
                    throw  new Exception("-----------------------数据源"+dbId+"配置正确, 但是创建失败-----------------------");
                }
            }else {
                logger.error("-----------------------数据源配置有误, 请重新配置-----------------------");
                throw  new Exception("-----------------------数据源配置有误, 请重新配置-----------------------");
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
                dataSource.setDatasourceName(druidDataSource.getName());
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
                dataSource.setDatasourceName(druidDataSource.getName());
                dataSource.setDataBaseType(druidDataSource.getDbType());
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


