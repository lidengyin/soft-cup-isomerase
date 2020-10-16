package cn.hctech2006.softcup.isomerase3.service.impl;


import cn.hctech2006.softcup.isomerase3.bean.DataSource;
import cn.hctech2006.softcup.isomerase3.common.ServerResponse;
import cn.hctech2006.softcup.isomerase3.datasource.DBContextHolder;
import cn.hctech2006.softcup.isomerase3.dynamic.DynamicDataSource;
import cn.hctech2006.softcup.isomerase3.vo.ParamBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DynamicServiceImpl {
    private Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("dynamicDataSource")
    private DynamicDataSource dynamicDataSource;
    @Autowired
    private FieldServiceImpl fieldService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ServerResponse queryDynamic(String datasourceId, String field , String dataTable, Map<String, String> params,
                                       String mainId, String chartType, String chartOption) throws Exception {
        if (field == null || field.equalsIgnoreCase("")) return ServerResponse.createByError("字段不能为空");
        logger.info("输入的dataSourceId: "+datasourceId);
        DataSource dataSource = queryDataSourceByDatasourceId(datasourceId);
        logger.info("url: "+dataSource.getUrl());
        logger.info("username: "+dataSource.getUserName());
        logger.info("password: "+dataSource.getPassword());
        logger.info("dataSourceId: "+dataSource.getDataSourceId());
        if (dataSource.getDataSourceId() == null || dataSource.getUserName() == null || dataSource.getPassword()==null || dataSource.getUrl() == null) return ServerResponse.createByError("该数据源不存在, 请重新输入");
        logger.info("数据源唯一编号: "+dataSource.getDataSourceId());
        dynamicDataSource.createDataSourceWithCheck(dataSource);
        DBContextHolder.setDataSource(dataSource.getDataSourceId());
        logger.info("sql_params"+params);
        String sql = "select ";
//        for (int i = 0; i < field.length; i ++){
//            if (i == field.length-1){
//                sql+=field[i];
//            }else{
//                sql+=field[i]+",";
//            }
//        }
        sql= sql + field +" from "+dataTable+ " where 1=1";
        logger.info("sql: "+sql);

        for (Map.Entry<String, String> term : params.entrySet() ){
            sql+=" and "+term.getKey()+"= :"+term.getKey();
        }

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql,params);
        logger.info("截止");
        logger.info("开始存储");
        String fieldId = UUID.randomUUID().toString();
        for (Map<String, Object> resultMap : resultList){
            for (Map.Entry<String, Object> term : resultMap.entrySet()){

                String value = ((term.getValue() instanceof Date)?sdf.format(((Date)term.getValue())) : term.getValue().toString());
                ServerResponse response = fieldService.uploadFiled(term.getKey(), value, dataTable, fieldId, mainId, chartType, chartOption);
                if (!response.isSuccess()){
                    return ServerResponse.createByError("存储错误");
                }
            }
        }


        return ServerResponse.createBySuccess(fieldId);
    }
    public ServerResponse querydynamicList(List<ParamBean> paramBeans) throws Exception {
        if (paramBeans == null) return ServerResponse.createByError("所需生成参数为空");
        String mainId = UUID.randomUUID().toString();
        for(ParamBean term : paramBeans){
            String datasourceId = term.getDatasourceId();
            String field = term.getField();
            String chartType = term.getChartType();
            String chartOption = term.getChartOption();
            Map<String, String> params = term.getParams();
            String dataTable = term.getDataTable();
            ServerResponse response = queryDynamic(datasourceId,field,dataTable,params, mainId ,chartType ,chartOption);
            if (!response.isSuccess()){
                return ServerResponse.createByError("获取失败, 请重新获取");
            }
        }
        return ServerResponse.createBySuccess(mainId);
    }

    public ServerResponse queryDynamic(String datasourceId, String sql , Map<String, String> params) throws Exception {
        System.out.println("输入的dataSourceId: "+datasourceId);
        DataSource dataSource = queryDataSourceByDatasourceId(datasourceId);
        System.out.println("url: "+dataSource.getUrl());
        System.out.println("username: "+dataSource.getUserName());
        System.out.println("password: "+dataSource.getPassword());
        System.out.println("dataSourceId: "+dataSource.getDataSourceId());
        if (dataSource.getDataSourceId() == null || dataSource.getUserName() == null || dataSource.getPassword()==null || dataSource.getUrl() == null) return ServerResponse.createByError("该数据源不存在, 请重新输入");
        logger.info("数据源唯一编号: "+dataSource.getDataSourceId());
        dynamicDataSource.createDataSourceWithCheck(dataSource);
        DBContextHolder.setDataSource(dataSource.getDataSourceId());
        logger.info("sql_params"+params);
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql,params);
        System.out.println("截止");
        return ServerResponse.createBySuccess(resultList);
    }
    private DataSource queryDataSourceByDatasourceId(String dataSourceId){
        return dynamicDataSource.queryDataSourcebydataSourceId(dataSourceId);
    }
    public ServerResponse queryOrStorage(String username, String password, String url
            , String databaseType, String dataSourceName) throws Exception {
        try{
            System.out.println("进入");
            DataSource dataSource = new DataSource();
            String dataSourceIdmd5Str = DigestUtils.md5DigestAsHex(url.getBytes());
            dataSource.setDataSourceId(dataSourceIdmd5Str);
            dataSource.setUrl(url);
            dataSource.setUserName(username);
            dataSource.setPassword(password);
            dataSource.setDataBaseType(databaseType);
            dataSource.setDatasourceName(dataSourceName);
            dynamicDataSource.createDataSourceWithCheck(dataSource);
            System.out.println("截止");
            return ServerResponse.createBySuccess("数据源基本信息", dataSource);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError("数据源创建失败");
        }
    }
    public ServerResponse queryListDataSource(){
        List<DataSource> dataSources = dynamicDataSource.datasourceList();
        return ServerResponse.createBySuccess(dataSources);
    }
    public ServerResponse queryColumnName(String datasourceId, String datableName) throws Exception {
        DataSource dataSource = queryDataSourceByDatasourceId(datasourceId);
        if (dataSource.getDataSourceId() == null || dataSource.getUserName() == null || dataSource.getPassword()==null || dataSource.getUrl() == null) return ServerResponse.createByError("该数据源不存在, 请重新输入");
        logger.info("数据源唯一编号: "+datasourceId);
        dynamicDataSource.createDataSourceWithCheck(dataSource);
        DBContextHolder.setDataSource(dataSource.getDataSourceId());
        //logger.info("sql_params");
        //获取表的字段名和备注名
        String sql = "select column_name , column_comment from information_schema.columns where table_Name="+"'"+datableName+"'";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, new HashMap<String ,Object>());
        List<String> resultStr = new ArrayList<>();
        for (Map<String, Object> term : resultList){
            for (Map.Entry<String, Object> param : term.entrySet()){
                resultStr.add((String) param.getValue());
            }
        }
        Map<String, String> resultMap = new HashMap<>();
        for (int i = 0; i < resultStr.size()-1; i +=2){
            resultMap.put(resultStr.get(i), resultStr.get(i+1));
        }
        return ServerResponse.createBySuccess(resultMap);
    }
    public ServerResponse queryTableName(String datasourceId, String tableName) throws Exception {
        DataSource dataSource = queryDataSourceByDatasourceId(datasourceId);
        if (dataSource.getDataSourceId() == null || dataSource.getUserName() == null || dataSource.getPassword()==null || dataSource.getUrl() == null) return ServerResponse.createByError("该数据源不存在, 请重新输入");
        logger.info("数据源唯一编号: "+datasourceId);
        dynamicDataSource.createDataSourceWithCheck(dataSource);
        DBContextHolder.setDataSource(dataSource.getDataSourceId());
        //logger.info("sql_params");
        //获取表的字段名和备注名

        String sql = "select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA="+"'"+tableName+"'";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, new HashMap<String, Object>());
        List<String> resultStr = new ArrayList<>();
        for (Map<String, Object> term : resultList){
            for (Map.Entry<String, Object> param : term.entrySet()){
                resultStr.add((String) param.getValue());
            }
        }
        return ServerResponse.createBySuccess(resultStr);
    }

}
