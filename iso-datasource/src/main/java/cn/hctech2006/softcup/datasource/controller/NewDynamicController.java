package cn.hctech2006.softcup.datasource.controller;
import cn.hctech2006.softcup.datasource.common.ServerResponse;
import cn.hctech2006.softcup.datasource.dto.DatabaseDTO;
import cn.hctech2006.softcup.datasource.service.impl.NewDynamicServiceImpl;
import cn.hctech2006.softcup.datasource.vo.DatabaseDetailsVO;
import cn.hctech2006.softcup.datasource.vo.DatabaseListVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "数据源与SQL查询控制器")
@RestController
public class NewDynamicController {
    @Autowired
    private NewDynamicServiceImpl dynamicService;

    @ApiOperation(value = "datasource---动态数据源存储", notes = "将数据源持久化到数据库, 如果已经持久化则返回数据源唯一标识" )
    @PostMapping(value = "/dynamics",produces = "application/json; charset=utf-8")
    @ApiResponses({
            @ApiResponse(code = 200,message = "ok",response = DatabaseDetailsVO.class)
    })
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse queryOrStorage(@RequestBody DatabaseDTO databaseDTO) throws Exception {
        System.out.println("开始测试");
        return dynamicService.storage(databaseDTO);
    }
    @ApiOperation(value = "datasource---查看数据源详细信息")
    @GetMapping(value = "/dynamics/{dbId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = DatabaseDetailsVO.class)
    })
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse getOne(@ApiParam(type = "path", name = "dbId", value = "数据源唯一标识", required = true) @PathVariable String dbId){
        return dynamicService.getOne(dbId);
    }
    @ApiOperation(value = "datasource---查看数据源详细信息----服务间调用")
    @GetMapping(value = "/dynamics/dbName/dbId/{dbId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = DatabaseDetailsVO.class)
    })
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse getDbNameAndDbType(@ApiParam(type = "path", name = "dbId", value = "数据源唯一标识", required = true) @PathVariable String dbId){
        return dynamicService.getDbNameAndDbType(dbId);
    }
    @ApiOperation(value = "datasource---查看数据源列表", notes = "根据restful风格一个资源一个url唯一标定, \n" +
            "因此在此我们的URI包括:\n" +
            "/dynamics/dbType/{dbType}/dbDatabase/{dbDatabase}: 双条件查询, 具体数据库dbDatabase依赖于数据库类型dbType\n" +
            "/dynamics/dbType/{dbType}: 单条件查询, 按照逻辑不能只查询数据库名, 首先要有数据库类型\n" +
            "/dynamics: 无条件查询\n" +
            "同时根据默认风格, 我们会在没有数据分页情况下, 进行pageNum=1, pageSize=20的分页查询\n")
    @GetMapping(value = {"/dynamic/dbType/{dbType}/dbDatabase/{dbDatabase}"
                    , "/dynamic/dbType/{dbType}"
                    , "/dynamics"
    } )

    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = DatabaseListVO.class)
    })
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse list(@ApiParam(type = "path", name = "dbType", value = "数据库类型", defaultValue = "mysql")@PathVariable(required = false) String dbType
            ,@ApiParam(type = "path", name = "dbDatabase", value = "数据库名", defaultValue = "soft_cup_1") @PathVariable(required = false) String dbDatabase
            , @ApiParam(type = "query", name = "pageNum", value = "页码", required = false, defaultValue = "1") @RequestParam(required = false, defaultValue = "1") int pageNum
            , @ApiParam(type = "query", name = "pageSize", value = "页行", required = false, defaultValue = "10") @RequestParam(required =  false, defaultValue = "20") int pageSize
    ){
        return dynamicService.list(dbType, dbDatabase, pageNum, pageSize);
    }

    @ApiOperation(value = "datasource---数据源列表删除")
    @DeleteMapping("/dynamics/dbIds/{dbIds}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse delete(@ApiParam(type = "path", name = "dbIds", value = "数据源唯一编号列表, 用逗号隔开即可", required = true) @PathVariable String dbIds){
        dynamicService.delete(dbIds);
        return ServerResponse.createBySuccess(dbIds);
    }
    @ApiOperation(value = "sql---查询该数据源所有数据表")
    @GetMapping("/dynamics/dbId/{dbId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse getDatatables(@ApiParam(type = "path", name = "dbId", value = "数据源编号", required = true,
            defaultValue = "711a0c79863f8ae810f4c24a449e31ee"
    ) @PathVariable String dbId) throws Exception {
        return dynamicService.getDatabases(dbId);
    }
    @ApiOperation(value = "sql---查询某数据表所有字段")
    @GetMapping("dynamics/dbId/{dbId}/dtName/{dtName}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse getFields(@ApiParam(type = "path", name = "dtName", value = "数据表表名", required = true)
                                        @PathVariable String dtName,
                                    @ApiParam(type = "path", name = "dbId", value = "数据库编号", required = true)
                                        @PathVariable String dbId) throws Exception {
        return dynamicService.getFields(dbId, dtName);
    }
    @ApiOperation(value = "sql---根据字段查询字段数据")
    @GetMapping("dynamics/data/dbId/{dbId}/sql/{sql}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse getFieldsBySQL(
            @ApiParam(type = "path", name = "dbId", value = "数据源编号", required = true, defaultValue = "711a0c79863f8ae810f4c24a449e31ee") @PathVariable String dbId,
            @ApiParam(type = "path", name = "sql", value = "查询语句", required = true) @PathVariable String  sql) throws Exception {
        return dynamicService.getDataByField(dbId, sql);
    }
}

