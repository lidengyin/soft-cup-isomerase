package cn.hctech2006.softcup.consumerserver.controller;

import cn.hctech2006.softcup.consumerserver.common.ServerResponse;
import cn.hctech2006.softcup.consumerserver.service.IsoeraseDynamicService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "动态数据源查询控制器")
@RequestMapping("/umbrella/dynamic")
@RestController
public class IsoeraseDynamicController {
    @Autowired
    private IsoeraseDynamicService dynamicService;
//    @ApiOperation(value = "动态查询语句")
//    @ApiImplicitParams({
//            @ApiImplicitParam(type = "query", name = "datasourceId", value = "数据源唯一标识, 使用地址代替", required = true, defaultValue = "8935abfd1530548fe1c7f28ac0ed3e1d"),
//            @ApiImplicitParam(type = "query", name = "field", value = "该数据表查询字段,可以叠加", required = true, defaultValue = "hotel_name ,channel"),
//            @ApiImplicitParam(type = "query", name = "dataTable", value = "需要查询的数据表", required = true, defaultValue = "nl_order"),
//    })
//    @PostMapping("/query_dynamic.do")
//    public ServerResponse queryDynamic(String datasourceId
//    , String[] field, String dataTable, @RequestBody Map<String, String> params ) throws Exception {
//        return dynamicService.queryDynamic(datasourceId, field , dataTable, params);
//    }


//    @ApiOperation(value = "动态查询语句" ,notes = "范例, 其中chartType是图表类型, chartOption是图表子选项, field是单个查询字段, dataTable是查询数据表, datasourceId是查询数据源, params是查询参数列表\n" +
//            "[\n" +
//            "  {\n" +
//            "    \"chartOption\": \"折线图\",\n" +
//            "    \"chartType\": \"纵轴\",\n" +
//            "    \"dataTable\": \"sys_user\",\n" +
//            "    \"datasourceId\": \"8935abfd1530548fe1c7f28ac0ed3e1d\",\n" +
//            "    \"field\": \"name\",\n" +
//            "    \"params\": {\n" +
//            "    }\n" +
//            "  },\n" +
//            "  {\n" +
//            "    \"chartOption\": \"折线图\",\n" +
//            "    \"chartType\": \"横轴\",\n" +
//            "    \"dataTable\": \"sys_user\",\n" +
//            "    \"datasourceId\": \"8935abfd1530548fe1c7f28ac0ed3e1d\",\n" +
//            "    \"field\": \"password\",\n" +
//            "    \"params\": {\n" +
//            "    }\n" +
//            "  }\n" +
//            "]")
//
//    @PostMapping("/query_dynamic.do")
//    public ServerResponse queryDynamic(@ApiParam("请求查询语句列表") @RequestBody List<ParamBean> paramBeans) throws Exception {
//        return dynamicService.querydynamicList(paramBeans);
//    }
//
//    @ApiOperation(value = "动态数据源连接接口")
//    @ApiImplicitParams({
//            @ApiImplicitParam(type = "query", name = "url", value = "数据库地址", required = true, defaultValue = "jdbc:mysql://172.17.0.1:3306/hc_hotel_1?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai"),
//            @ApiImplicitParam(type = "query", name = "username", value = "数据库用户名", required = true, defaultValue = "root"),
//            @ApiImplicitParam(type = "query", name = "password", value = "数据库密码", required = true, defaultValue = "123456"),
//            @ApiImplicitParam(type = "query", name = "databaseType", value = "数据库类型", required = true, defaultValue = "mysql"),
//            //@ApiImplicitParam(type = "query", name = "dataSourceName", value = "数据库名", required = true, defaultValue = "hc_hotel_1"),
//
//
//    })
//    @GetMapping("/query_datasource.do")
//    public ServerResponse queryOrStorage(String username, String password, String url
//            , String databaseType) throws Exception {
//        return dynamicService.queryOrStorage(username,password, url, databaseType);
//    }
    @ApiOperation(value = "查看数据源列表")
    @GetMapping("/query_list_datasource.do")
    public ServerResponse queryListDataSource(){
        return dynamicService.queryListDatasource();

    }
//    @ApiOperation(value = "动态查询语句,使用数据源编号")
//    @ApiImplicitParams({
//            @ApiImplicitParam(type = "query", name = "datasourceId", value = "数据源唯一标识, 使用地址代替", required = true, defaultValue = "jdbc:mysql://172.17.0.1:3306/hc_hotel_1?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai"),
//            @ApiImplicitParam(type = "query", name = "sql", value = "数据库查询语句", required = true, defaultValue = "select * from nl_hotel"),
//            //@ApiImplicitParam(type = "query", name = "databaseType", value = "数据库类型", required = true, defaultValue = "mysql"),
//    })
//    @PostMapping("/query_dynamic_number.do")
//    public ServerResponse queryDynamicNumber(String datasourceId
//            , String sql,  @RequestBody Map<String, String> params ) throws Exception {
//        return dynamicService.queryDynamic(datasourceId, sql , params);
//    }
//
//    @ApiOperation(value = "查看某一个表的所有字段")
//    @GetMapping("/query_all.do")
//    public ServerResponse queryColumnName(String datasourceId, String datableName) throws Exception {
//        return dynamicService.queryColumnName(datasourceId,datableName);
//    }
//
//    @ApiOperation(value = "数据测试")
//    @GetMapping("/query_array_list.do")
//    @ApiImplicitParams({
//            @ApiImplicitParam(type = "query", name = "params", value = "测试字符串数组",required = true)
//    })
//    public void queryArrayList(String[] params){
//        for (int i = 0; i < params.length;  i++){
//            System.out.println(params[i]);
//        }
//    }
//    @ApiOperation(value = "查看某个数据库的所有数据表")
//    @GetMapping("/query_all_table.do")
//    @ApiImplicitParams({
//            @ApiImplicitParam(type = "query", name = "datasourceId", value = "数据源", defaultValue = "", required = true),
//            @ApiImplicitParam(type = "query", name = "tableName", value = "数据库名", defaultValue = "", required = true),
//    })
//    public ServerResponse queryTableName(String datasourceId, String tableName) throws Exception {
//        return dynamicService.queryTableName(datasourceId, tableName);
//    }
}