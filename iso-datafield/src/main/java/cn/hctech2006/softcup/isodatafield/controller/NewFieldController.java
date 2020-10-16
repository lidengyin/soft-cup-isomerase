package cn.hctech2006.softcup.isodatafield.controller;

import cn.hctech2006.softcup.isodatafield.common.ServerResponse;
import cn.hctech2006.softcup.isodatafield.service.impl.NewFieldServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "新版本字段控制器")
@RestController
public class NewFieldController {
    @Autowired
    private NewFieldServiceImpl fieldService;
    @ApiOperation(value = "查询某数据表所有字段")
    @GetMapping("fields/dbId/dtId/{dtId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse getFields(@ApiParam(type = "path", name = "dtId", value = "数据表编号", required = true,
    defaultValue = "711a0c79863f8ae810f4c24a449e31eea480e716010b99c0b7fe39da8453b3c7") @PathVariable String dtId) {
        return fieldService.getFields(dtId);
    }
    @ApiOperation(value = "查询某个字段详细信息")
    @GetMapping("/fields/fdId/{fdId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse getOne(@ApiParam(type = "path", name = "fdId", value = "字段编号", required = true)
                                     @PathVariable String fdId){
        return fieldService.getOne(fdId);
    }
    @ApiOperation(value = "查询某个字段详细信息--字段名-字段注释-字段类型版本")
    @GetMapping("/fields/details/fdId/{fdId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse getOneDetails(@ApiParam(type = "path", name = "fdId", value = "字段编号", required = true)
                                 @PathVariable String fdId){
        return fieldService.getOne(fdId);
    }
}
