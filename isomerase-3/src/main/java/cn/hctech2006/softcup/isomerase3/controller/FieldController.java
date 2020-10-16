package cn.hctech2006.softcup.isomerase3.controller;

import cn.hctech2006.softcup.isomerase3.common.ServerResponse;
import cn.hctech2006.softcup.isomerase3.service.impl.FieldServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(tags = "字段生成相关控制器")
@RequestMapping("/field")
@RestController
public class FieldController {
    @Autowired
    private FieldServiceImpl fieldService;

    @ApiOperation(value = "列表生成查询")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "mainId", value = "生成查询列表编号", required = true, defaultValue = "7bde6ae2-d9b6-4d95-b0b9-2cd94dcc693f")
    })
    @GetMapping("/list_field.do")
    public ServerResponse listField(String mainId){
        return fieldService.findByMainId(mainId);
    }
    @ApiOperation(value = "列表导出接口")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "mainId", value = "生成查询列表编号", required = true, defaultValue = "7bde6ae2-d9b6-4d95-b0b9-2cd94dcc693f")
    })
    @GetMapping("/export_excel.do")
    public void excelExport(@ApiIgnore HttpServletRequest request, @ApiIgnore HttpServletResponse response, String mainId) throws Exception {
        fieldService.excelExport(request, response, mainId);
    }
}
