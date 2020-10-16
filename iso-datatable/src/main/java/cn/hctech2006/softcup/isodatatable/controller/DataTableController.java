package cn.hctech2006.softcup.isodatatable.controller;

import cn.hctech2006.softcup.isodatatable.common.ServerResponse;
import cn.hctech2006.softcup.isodatatable.service.impl.DataTableServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "数据表控制器")
@RestController
public class DataTableController {
    @Autowired
    private DataTableServiceImpl fieldService;

    @ApiOperation(value = "服务间调用--查询该数据源所有数据表")
    @GetMapping("/datatables/dbId/{dbId}")
    public ServerResponse getDatatables(@ApiParam(type = "path", name = "dbId", value = "数据源编号", required = true,
    defaultValue = "711a0c79863f8ae810f4c24a449e31ee") @PathVariable String dbId) throws Exception {
        return fieldService.getDataTable(dbId);
    }

    @ApiOperation(value = "服务间调用--获取某个数据表的详细信息")
    @GetMapping("/datatables/{dtId}")
    public ServerResponse getOne(@ApiParam(type = "path", name = "dtId", value = "数据表编号", required = true) @PathVariable String dtId){
        return fieldService.getOne(dtId);
    }

}
