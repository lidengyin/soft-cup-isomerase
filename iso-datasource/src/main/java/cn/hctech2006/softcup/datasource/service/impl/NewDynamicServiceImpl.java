package cn.hctech2006.softcup.datasource.service.impl;

import cn.hctech2006.softcup.datasource.bean.NlDatabase;
import cn.hctech2006.softcup.datasource.common.ServerResponse;
import cn.hctech2006.softcup.datasource.datasource.DBContextHolder;
import cn.hctech2006.softcup.datasource.dto.DatabaseDTO;
import cn.hctech2006.softcup.datasource.dto.DatabaseServiceDTO;
import cn.hctech2006.softcup.datasource.dynamic.NewDynamicDataSource;
import cn.hctech2006.softcup.datasource.mapper.NlDatabaseMapper;
import cn.hctech2006.softcup.datasource.util.DBUtil;
import cn.hctech2006.softcup.datasource.vo.DatabaseDetailsVO;
import cn.hctech2006.softcup.datasource.vo.DatabaseListVO;
import cn.hctech2006.softcup.datasource.vo.DatabaseVO;
import cn.hutool.db.handler.HandleHelper;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * RestFul数据源服务层
 */
@Service
public class NewDynamicServiceImpl {
    //类日志
    private Logger logger = LoggerFactory.getLogger(NewDynamicServiceImpl.class);
    //动态数据源
    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private NewDynamicDataSource dynamicDataSource;
    @Autowired
    private NlDatabaseMapper databaseMapper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String dbType = "mysql";
    private String dbName = "";

    /**
     * datasource-数据源存储
     * @param databaseDTO
     * @return
     */
    public ServerResponse storage(DatabaseDTO databaseDTO){
        logger.info("---------------------判断数据源是否已经持久化-----------------------------");
        String dbUrl = hostAndTableNameAndTypeToUrl(databaseDTO);
        String dbId = strToMD5(dbUrl);
        ServerResponse response = getOne(dbId);
        if (response.isSuccess()) {
            logger.info("-----------------数据源已经持久化-----------------------------");
            return ServerResponse.createBySuccess("该数据源已经存在, 数据源唯一编号为: ", dbId);
        }
        logger.info("---------------------数据源没有持久化-----------------------------");
        logger.info("---------------------开始新建数据源存储-----------------------------");
        NlDatabase database = databaseDTOToDatabasePOJO(databaseDTO);
        DatabaseDetailsVO databaseDetailsVO = databasePOJOToDatabaseVO(database);
        int result = databaseMapper.insert(database);
        if (result <= 0) return ServerResponse.createByError("数据源存储失败");
        String databaseJsonStr = JSONObject.toJSONStringWithDateFormat(databaseDetailsVO,"yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
        logger.info("数据源json格式如下: "+databaseJsonStr);
        logger.info("---------------------新建数据源存储完毕-----------------------------");

        return ServerResponse.createBySuccess("数据源存储成功", databaseDetailsVO);
    }

    /**
     * datasource-获取数据源的详细信息
     * @param dbId
     * @return
     */
    public ServerResponse getOne(String dbId)  {

           logger.info("-----------------开始查看数据源详细信息-----------------------------");
           NlDatabase database = databaseMapper.selectByDbId(dbId);
           DatabaseDetailsVO databaseDetailsVO = new DatabaseDetailsVO();
           if (database != null) {
               databaseDetailsVO = databasePOJOToDatabaseVO(database);
               String databaseVOJSONStr = JSONObject.toJSONString(databaseDetailsVO);
               logger.info("-----------------数据源详细信息:"+databaseVOJSONStr);
               logger.info("-----------------结束查看数据源详细信息-----------------------------");
               return ServerResponse.createBySuccess(databaseDetailsVO);
           }
           return ServerResponse.createByError("该数据源不存在");
    }
    /**
     * datasource-获取数据源的详细信息
     * @param dbId
     * @return
     */
    public ServerResponse getDbNameAndDbType(String dbId)  {

        logger.info("-----------------开始查看数据源详细信息-----------------------------");
        NlDatabase database = databaseMapper.selectByDbId(dbId);
        DatabaseDetailsVO databaseDetailsVO = new DatabaseDetailsVO();
        if (database != null) {
            databaseDetailsVO = databasePOJOToDatabaseVO(database);
            String databaseVOJSONStr = JSONObject.toJSONString(databaseDetailsVO);
            logger.info("-----------------数据源详细信息:"+databaseVOJSONStr);
            logger.info("-----------------结束查看数据源详细信息-----------------------------");
            Map<String, String> result = new HashMap<>();
            result.put("dbType", databaseDetailsVO.getDbType());
            result.put("dbName", databaseDetailsVO.getDbName());
            return ServerResponse.createBySuccess(result);
        }
        return ServerResponse.createByError("该数据源不存在");
    }

    /**
     * datasource-获取数据源列表
     * @param dbType
     * @param dbDatabase
     * @return
     */
    public ServerResponse list( String dbType,  String dbDatabase, int pageNum, int pageSize)  {
        logger.info("-----------------开始查看数据源列表信息-----------------------------");
        logger.info("-----------------数据源列表设置分页: pageNum: "+pageNum+" pageSize: "+pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<NlDatabase> databases;
          try{
              databases = databaseMapper.selectByDbTypeAndDbDatabase(dbType, dbDatabase);

          }catch (Exception e){
              logger.error("----------详细异常: "+e.getMessage());
                throw new RuntimeException("获取数据源列表,sql异常\n"+e.getMessage());
          }


        List<DatabaseVO> databaseVOS = new ArrayList<>();
        for (NlDatabase database : databases){
            DatabaseVO databaseVO = databasePOJOToDatabaseListVO(database);
            databaseVOS.add(databaseVO);
        }
        PageInfo result = new PageInfo<>(databases);
        result.setList(databaseVOS);
        DatabaseListVO databaseListVO = pageInfoToDatabaseListVO(result);
        logger.error("-----------------结束查看数据源详细信息-----------------------------");
        return ServerResponse.createBySuccess(databaseListVO);
    }

    /**
     * datasource-批量删除数据源
     * @param dbIds
     * @return
     */
    //隔离级别, 不一起失败
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ServerResponse delete( String dbIds){
        String[] dbIdArray = dbIds.split(",");
        for (int i = 0; i < dbIdArray.length; i ++){
            int result = databaseMapper.deleteByDbId(dbIdArray[i]);
            if (result <= 0) return ServerResponse.createByError("删除失败, 请重新删除");
        }
        return ServerResponse.createBySuccess(dbIds);
    }




    /**
     * jdbcTemplate-获取数据源下所有数据库
     * @param dbId
     * @return
     */
    public ServerResponse getDatabases(String dbId) throws Exception {
        setMainDatasource(dbId);
        String dbName = this.dbName;
        String dbType = this.dbType;
        List<String> result = queryTables(dbName,dbType);
        return ServerResponse.createBySuccess(result);
    }

    /**
     * jdbcTemplate-获取数据表全部字段
     * @return
     * @throws Exception
     */
    public ServerResponse getFields(String dbId, String dtName) throws Exception {
        setMainDatasource(dbId);
        String dbType = this.dbType;
        //Map<String, String> fields = queryFields(dtName,dbType);
        String[][] fields = queryFieldsAddType(dtName,dbType);
        return ServerResponse.createBySuccess(fields);
    }
    /**
     * jdbcTemplate-根据查询参数获得查询结果
     * @param dbId
     * @param fdName
     * @param dtName
     * @param paramsStr
     * @return
     * @throws Exception
     */
    public ServerResponse getDataByField(String dbId,  String sql
    ) throws Exception {
        setMainDatasource(dbId);
        List<Map<String, Object>> resultList = queryResultBySql(sql);
        return ServerResponse.createBySuccess(resultList);
    }

    /**
     * sql-查询结果SQL语句封装
     * @param fdName
     * @param dtName
     * @param params
     * @return
     */
    private List<Map<String, Object>> queryResult(String fdName, String dtName, Map<String, String> params){
        String sql = "select ";
        sql= sql + fdName +" from "+dtName+ " where 1=1";
        List<Map<String, Object>> resultList;
        if (params != null){
            for (Map.Entry<String, String> term : params.entrySet() ){
                sql+=" and "+term.getKey()+"= :"+term.getKey();
            }
            logger.info("sql: "+sql);
            resultList = jdbcTemplate.queryForList(sql,params);
        }else{
            logger.info("sql: "+sql);
            resultList = jdbcTemplate.queryForList(sql,new HashMap<String, Object>());
        }

        return resultList;
    }

    /**
     * 查询结果通过sql语句
     * @param sql

     * @return
     */
    private List<Map<String, Object>> queryResultBySql(String sql){
        List<Map<String, Object>> resultList;
        logger.info("sql: "+sql);
        resultList = jdbcTemplate.queryForList(sql,new HashMap<String, Object>());
        return resultList;
    }


    /**
     * sql-查询该数据表下所有字段
     * @param dtName
     * @param dbType
     * @return
     */
    private  Map<String, String> queryFields(String dtName, String dbType){
        String sql = null;
        if (dbType.equalsIgnoreCase("POSTGRESQL")){
            sql  =  "SELECT a.attname as column_name, col_description(a.attrelid,a.attnum) as column_comment FROM pg_class as c,pg_attribute as a where c.relname = "
                    +"'"+dtName+"'"+" and a.attrelid = c.oid and a.attnum>0;";
        }else if (dbType.equalsIgnoreCase("sqlserver")){
            sql = "SELECT " +
                    " B.name AS column_name, " +
                    " C.value AS column_description " +
                    " FROM sys.tables A " +
                    " INNER JOIN sys.columns B ON B.object_id = A.object_id " +
                    " LEFT JOIN sys.extended_properties C ON C.major_id = B.object_id AND C.minor_id = B.column_id " +
                    " WHERE A.name = "+"'"+dtName+"'";
        }
        else if (dbType.equalsIgnoreCase("mysql")){
            sql = "select column_name , column_comment from information_schema.columns where table_Name="+"'"+dtName+"'";
        }
        System.out.println("sql: "+sql);
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
            System.out.println(resultStr.get(i)+" "+resultStr.get(i+1));
        }
        queryFieldsAddType(dtName, dbType);
        return resultMap;
    }

    private  String[][] queryFieldsAddType(String dtName, String dbType){
        String sql = null;
        if (dbType.equalsIgnoreCase("POSTGRESQL")){
            sql  =  " SELECT a.attname as column_name, " +
                    " col_description(a.attrelid,a.attnum) as column_comment, " +
                    " format_type(a.atttypid,a.atttypmod) as column_type FROM pg_class as c,pg_attribute as a where c.relname = "
                    +"'"+dtName+"'"+" and a.attrelid = c.oid and a.attnum>0;";
        }else if (dbType.equalsIgnoreCase("sqlserver")){
            sql = "SELECT " +
                    " B.name AS column_name, " +
                    " C.value AS column_description, " +
                    " D.name AS column_type "+
                    " FROM sys.tables A " +
                    " INNER JOIN sys.columns B ON B.object_id = A.object_id " +
                    " LEFT JOIN sys.extended_properties C ON C.major_id = B.object_id AND C.minor_id = B.column_id " +
                    " INNER JOIN sys.types D ON D.user_type_id=B.user_type_id" +
                    " WHERE A.name ="+"'"+dtName+"'";
        }
        else if (dbType.equalsIgnoreCase("mysql")){
            sql = "select column_name , column_comment , data_type from information_schema.columns where table_Name="+"'"+dtName+"'";
        }
        System.out.println("sql: "+sql);
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, new HashMap<String ,Object>());
        List<String> resultStr = new ArrayList<>();
        String[][] resultArr = new String[resultList.size()][3];
        for (Map<String, Object> term : resultList){
            for (Map.Entry<String, Object> param : term.entrySet()){
                resultStr.add((String) param.getValue());
            }
        }
        for (int i = 0, j = 0; i < resultStr.size()-2; i +=3, j++){
            resultArr[j][0]=resultStr.get(i);
            resultArr[j][1]=resultStr.get(i+1);
            resultArr[j][2]=resultStr.get(i+2);
            System.out.println(resultArr[j][0]+" "+resultArr[j][1]+" "+resultArr[j][2]);
        }
        return resultArr;
    }

    /**
     * sql-内部类获取该数据源全部数据表
     * @param dbDatabase
     * @param dbType
     * @return
     */
    private List<String> queryTables(String dbDatabase, String dbType){
        List<String> resultStr = new ArrayList<>();
        String sql = null;
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (dbType.equalsIgnoreCase("postgresql")){
            sql="select relname as tabname, cast(obj_description(relfilenode, "+"'"+"pg_class"+"'"+")as "+"varchar"+") as comment from pg_class c where relkind="+"'"+"r"+"'"+"and relname not like "+"'"+"pg_%"+"'"+" and relname not like "+"'"+"sql_%"+"'"+" order by relname";
            resultList = jdbcTemplate.queryForList(sql, new HashMap<String, Object>());
        }else if(dbType.equalsIgnoreCase("mysql")){
            sql = "select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA="+"'"+dbDatabase+"'";
            resultList = jdbcTemplate.queryForList(sql, new HashMap<String, Object>());
        }
        else if (dbType.equalsIgnoreCase("sqlserver")){
            //sql ="SELECT Name FROM "+dbDatabase+"..SysObjects"+" Where XType="+"'"+"U"+"'";
            sql = "select TABLE_NAME from information_schema.TABLES";
            resultList = jdbcTemplate.queryForList(sql, new HashMap<String, Object>());

        }
        System.out.println("dbType: "+dbType);
        System.out.println("sql: "+sql);


        for (Map<String, Object> term : resultList){
            for (Map.Entry<String, Object> param : term.entrySet()){
                if (param.getValue()!=null)
                {

                    resultStr.add((String) param.getValue());
                }
            }
        }
        return resultStr;
    }
    /**
     * datasourse-设置主数据源
     * @param dbId
     * @return
     * @throws Exception
     */
    private ServerResponse setMainDatasource(String dbId) throws Exception {
        if (StringUtils.isEmpty(dbId)) return ServerResponse.createByError("参数不能为空");
        DatabaseDetailsVO databaseDetailsVO = (DatabaseDetailsVO)getOne(dbId).getData();
        if (databaseDetailsVO == null) return ServerResponse.createByError("该数据源不存在, 请重新输入");
        DatabaseServiceDTO databaseServiceDTO = databaseDetailsVOTodatabaseServiceDTO(databaseDetailsVO);
        queryOrStorageDataSource(databaseServiceDTO);
        //业务开始
        logger.info("------------------设置当前主数据源-----------------------");
        DBContextHolder.setDataSource(databaseServiceDTO.getDbId());
        this.dbType=databaseDetailsVO.getDbType();
        this.dbName=databaseDetailsVO.getDbDatabase();
        return ServerResponse.createBySuccess();
    }
    /**
     * datasource-查询或新建数据源
     * @param databaseServiceDTO
     * @throws Exception
     */
    private void queryOrStorageDataSource(DatabaseServiceDTO databaseServiceDTO) throws Exception {
        dynamicDataSource.createDataSourceWithCheck(databaseServiceDTO);
    }
    /**
     * datasource-根据参数生成jdbcUrl;
     * @return
     */
    private String hostAndTableNameAndTypeToUrl(DatabaseDTO databaseDTO){
        String host = databaseDTO.getHost();
        String dbType = databaseDTO.getDbType();
        String dbTable = databaseDTO.getDbTable();
        String url = null;
        String urlSuffix="?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";
        if (dbType.equalsIgnoreCase(DBUtil.DatabaseType.MYSQL.name())){
            url = "jdbc:mysql://"+host+":3306/"+dbTable+urlSuffix;
        }else if(dbType.equalsIgnoreCase(DBUtil.DatabaseType.SQLSERVER.name())){
            url = "jdbc:sqlserver://"+host+":1433;databaseName="+dbTable;
        }else if (dbType.equalsIgnoreCase(DBUtil.DatabaseType.POSTGRESQL.name())){
            url = "jdbc:postgresql://"+host+":5432/"+dbTable+urlSuffix;
        }
        logger.info("---------------------工具内部方法, 使用host, dbType, dbTable生成jdbc地址-------------------------");
        logger.info("dbType: "+dbType);
        logger.info("url: "+url);
        logger.info("---------------------工具内部方法, 使用host, dbType, dbTable生成jdbc地址完毕----------------------");
        return url;
    }

    /**
     * POJO-数据源, 数据展示层详细展示对象转换为服务层数据传输对象
     * @param databaseDetailsVO
     * @return
     */
    private DatabaseServiceDTO databaseDetailsVOTodatabaseServiceDTO(DatabaseDetailsVO databaseDetailsVO){
        DatabaseServiceDTO databaseServiceDTO = new DatabaseServiceDTO();
        databaseServiceDTO.setDbDatabase(databaseDetailsVO.getDbDatabase());
        databaseServiceDTO.setDbId(databaseDetailsVO.getDbId());
        databaseServiceDTO.setDbName(databaseDetailsVO.getDbName());
        databaseServiceDTO.setDbTpassword(databaseDetailsVO.getDbTpassword());
        databaseServiceDTO.setDbType(databaseDetailsVO.getDbType());
        databaseServiceDTO.setDbUrl(databaseDetailsVO.getDbUrl());
        databaseServiceDTO.setDbUser(databaseDetailsVO.getDbUser());
        return databaseServiceDTO;
    }
    //TODO 数据源查询是否存在
    //TODO 数据源根据数据源编号查询
    //todo 查看数据源列表
    //todo 数据源修改,修改数据源名
    //todo 数据源修改删除

    /**
     * POJO-PageInfo转数据展示层, 列表分页展示
     * @param pageInfo
     * @return
     */
    private DatabaseListVO pageInfoToDatabaseListVO(PageInfo pageInfo){
        DatabaseListVO databaseListVO = new DatabaseListVO();
        databaseListVO.setDatabaseVOS(pageInfo.getList());
        databaseListVO.setPageNum(pageInfo.getPageNum());
        databaseListVO.setPageSize(pageInfo.getPageSize());
        databaseListVO.setPages(pageInfo.getPages());
        databaseListVO.setTotal(pageInfo.getTotal());
        databaseListVO.setNavigateFirstPage(pageInfo.getNavigateFirstPage());
        databaseListVO.setNavigateLastPage(pageInfo.getNavigateLastPage());
        databaseListVO.setNavigatepageNums(pageInfo.getNavigatepageNums());
        return databaseListVO;
    }
    /**
     * POJO-POJO转数据展示层, 列表详情展示
     * @param database
     * @return
     */
    private DatabaseDetailsVO databasePOJOToDatabaseVO(NlDatabase database){
        DatabaseDetailsVO databaseDetailsVO = new DatabaseDetailsVO();
        databaseDetailsVO.setDbDatabase(database.getDbDatabase());
        databaseDetailsVO.setDbId(database.getDbId());
        databaseDetailsVO.setDbName(database.getDbName());
        databaseDetailsVO.setDbTime(dateToStr(database.getDbTime()));
        databaseDetailsVO.setDbTpassword(database.getDbTpassword());
        databaseDetailsVO.setDbType(database.getDbType());
        databaseDetailsVO.setDbUrl(database.getDbUrl());
        databaseDetailsVO.setDbUser(database.getDbUser());
        return databaseDetailsVO;
    }
    /**
     * POJO-POJO转数据展示展示层, 列表数据展示
     * @param database
     * @return
     */
    private DatabaseVO databasePOJOToDatabaseListVO(NlDatabase database){
        DatabaseVO databaseVO = new DatabaseVO();
        databaseVO.setDbName(database.getDbName());
        databaseVO.setDbId(database.getDbId());
        return databaseVO;
    }


    /**
     * POJO-数据传输层转POJO
     * @param databaseDTO
     * @return
     */
    private NlDatabase databaseDTOToDatabasePOJO(DatabaseDTO databaseDTO){
        logger.info("---------------------开始databaseDTO转化为DatabasePOJO---------------------------");
        String host = databaseDTO.getHost();
        String dbType = databaseDTO.getDbType();
        String dbTable = databaseDTO.getDbTable();
        String dbUser = databaseDTO.getDbUser();
        String dbPassword = databaseDTO.getDbPassword();

        String dbUrl = hostAndTableNameAndTypeToUrl(databaseDTO);
        String dbId = strToMD5(dbUrl);
        String dbName= dbType+"-"+host+"-"+dbTable;
        NlDatabase database = new NlDatabase();
        database.setDbId(dbId);
        database.setDbUser(dbUser);
        database.setDbTpassword(dbPassword);
        database.setDbUrl(dbUrl);
        database.setDbType(dbType);
        database.setDbTime(new Date());
        database.setDbDatabase(dbTable);
        database.setDbName(dbName);
        logger.info("---------------------结束databaseDTO转化为DatabasePOJO---------------------------");
        return database;
    }

    /**
     * util-时间字符串转化类
     * @param date
     * @return
     */
    private String dateToStr(Date date){
        return sdf.format(date);
    }
    /**
     * util-MD5加密工具类
     * @param dbUrl
     * @return
     */
    private String strToMD5(String dbUrl){
        logger.info("---------------------MD5加密-------------------------");
        return DigestUtils.md5DigestAsHex(dbUrl.getBytes());

    }

    /**
     * util-字符串转Map
     * @param str
     * @return
     */
    private static Map<String, String> strToMap(String str){
        Map<String, String> map = JSONObject.parseObject(str,Map.class);
        return map;
    }
    private static String mapToStr(Map<String, String> map){
        String str = JSONObject.toJSONString(map);
        return str;
    }


}
