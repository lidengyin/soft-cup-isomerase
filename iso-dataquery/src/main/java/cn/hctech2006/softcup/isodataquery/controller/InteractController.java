package cn.hctech2006.softcup.isodataquery.controller;

import cn.hctech2006.softcup.isodataquery.common.ServerResponse;
import cn.hctech2006.softcup.isodataquery.dto.QueryDTO;
import cn.hctech2006.softcup.isodataquery.service.impl.QueryServiceImpl;
import cn.hctech2006.softcup.isodataquery.vo.QueryDetailsVO;
import cn.hctech2006.softcup.isodataquery.vo.QuerySmallListVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "查询实例控制器")
@RestController
public class InteractController {
    @Autowired
    private QueryServiceImpl queryService;
    @ApiOperation(value = "生成查询实例")
    @PostMapping("/interaction/{queryName}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse upload(@ApiParam(type = "path", name = "queryName", value = "查询实例名", required = true, defaultValue = "使用率查询实例") @PathVariable String queryName
            , @RequestBody List<QueryDTO> queryDTOS) throws Exception {
        return queryService.upload(queryName, queryDTOS);
    }
    @ApiOperation(value = "查询一个查询实例", notes = "显示结果给前段")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = QueryDetailsVO.class)
    })
    @GetMapping("/interaction/{quId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse queryOneToFg(@ApiParam(type = "path", name = "quId", value = "查询实例编号", required = true,
        defaultValue = "120021d4-3e78-4feb-8119-8cd404663af6"
    )@PathVariable String quId) throws Exception {
        return queryService.queryOneResult(quId);
    }
    @ApiOperation(value = "查询实例列表")
    @GetMapping("/interaction")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "pageNum", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(type = "query", name = "pageSize", value = "页行", required = true, defaultValue = "10"),
            @ApiImplicitParam(type = "query", name = "showFlag", value = "显示标志", required = true, defaultValue = "1")
    })
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse list(int pageNum, int pageSize, String showFlag){
        return queryService.list(pageNum, pageSize, showFlag);
    }

    @ApiOperation(value = "查看某个实例的参数")
    @ApiResponses({
            @ApiResponse(code=200, message = "ok", response = QuerySmallListVO.class)
    })
    @GetMapping("/interaction/querySmall/{quId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse getQuerySmallByQuId(@ApiParam(type = "path", name = "quId", value = "模型实例编号", required = true,
        defaultValue = "120021d4-3e78-4feb-8119-8cd404663af6"
    ) @PathVariable String quId){
        return queryService.getQuerySmallByQuId(quId);
    }

    @ApiOperation(value = "服务间传输, 根据查询编号获取查询名")
    @GetMapping("/interaction/queryName/{quId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse getQueryName(@ApiParam(type = "path", name = "quId", value = "查询实例编号", required = true)@PathVariable String quId){
        return queryService.getQueryName(quId);
    }
    @ApiOperation(value = "新增-查询参数-查看所有参数类型")
    @GetMapping("/interaction/params")
    public ServerResponse getAllParams(){
        Map<String, String> maps = new HashMap<>();
        maps.put("DATE", "时间类型");
        maps.put("NUM", "数字类型");
        maps.put("BOOL", "布尔类型");
        maps.put("STR", "字符串类型");
        return ServerResponse.createBySuccess(maps);
    }
    @ApiOperation(value = "新增-查询参数-查看某一个参数类型可用的比较方法")
    @GetMapping("/interaction/params/judges/{pmName}")
    public ServerResponse getAllJudge(@ApiParam(type = "path", name = "pmName", value = "参数类型名",defaultValue = "DATE") @PathVariable String pmName){
        Map<String, List<String>> types = new HashMap<>();
        //1.字符串
        List<String> strArr = new ArrayList<>();
        strArr.add("equal");
        strArr.add("dim");
        types.put("STR", strArr);
        //2.数字
        List<String> strDig = new ArrayList<>();
        strDig.add("equal");
        strDig.add("bigger");
        strDig.add("smaller");
        types.put("NUM", strDig);
        //3.布尔
        List<String> strBol = new ArrayList<>();
        strBol.add("equal");
        types.put("BOOL", strBol);
        //4. 时间范围
        List<String> strDate = new ArrayList<>();
        strDate.add("bigger");
        strDate.add("smaller");
        strDate.add("equal");
        types.put("DATE", strDate);
        if (types.containsKey(pmName)){
            List<String> judgeList = types.get(pmName);
            String[] judgeStr = new String[judgeList.size()];
            judgeStr = judgeList.toArray(judgeStr);
            return ServerResponse.createBySuccess(judgeStr);
        }else{
            return ServerResponse.createByError();
        }
    }

}
