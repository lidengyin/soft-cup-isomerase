package cn.hctech2006.softcup.isomodel.controller;
import cn.hctech2006.softcup.isomodel.common.ServerResponse;
import cn.hctech2006.softcup.isomodel.dto.ModelDTO;
import cn.hctech2006.softcup.isomodel.dto.ModelPutDTO;
import cn.hctech2006.softcup.isomodel.service.impl.ModelServiceImpl;
import cn.hctech2006.softcup.isomodel.vo.ModelDetailsVO;
import cn.hctech2006.softcup.isomodel.vo.ModelSmallVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "大数据模型控制器")
@RestController
public class ModelController {
    @Autowired
    private ModelServiceImpl modelService;
    @ApiOperation(value = "大数据模型上传")
    @PostMapping("/models/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse upload(@RequestBody ModelDTO modelDTO){
        return modelService.upload(modelDTO);
    }
    @ApiOperation(value = "大数据模型列表展示")
    @GetMapping(value = {"/models/mlAlgo/{mlAlgo}/mlResult/{mlResult}",
            "/models/mlResult/{mlResult}",
            "/models/mlAlgo/{mlAlgo}",
    "/models/"})
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "pageNum", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(type = "query", name = "pageSize", value = "页行", required = true, defaultValue = "10"),
            @ApiImplicitParam(type = "path", name = "mlAlgo", value = "模型算法", defaultValue = "python"),
            @ApiImplicitParam(type = "path", name = "mlResult", value = "模型预期结果类型", defaultValue = "时间"),
            @ApiImplicitParam(type = "query", name = "showFlag", value = "显示标志,1显示, 0不显示", defaultValue = "1"),
    })
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse list(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize
            , @PathVariable(required = false) String mlAlgo
            , @PathVariable(required = false) String mlResult
            ,  String showFlag
    ){
        return modelService.list(pageNum, pageSize, mlAlgo, mlResult, showFlag);
    }
    @ApiOperation(value = "获取一个模型详情")
    @ApiResponses({
            @ApiResponse(code=200, message = "ok", response = ModelDetailsVO.class)
    })
    @GetMapping("/models/mlId/{mlId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse getOne(@ApiParam(name = "mlId", value = "模型编号", required = true ,
        defaultValue = "b5250528-2d6b-49d9-8d69-c5a7bbd504f5"
    ) @PathVariable String mlId){
        return modelService.getOne(mlId);
    }
    @ApiOperation(value = "大数据模型修改")
    @PutMapping("/models/mlId/{mlId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse update(@ApiParam(type = "path", name = "mlId", value = "模型编号", required = true) @PathVariable String mlId, @RequestBody ModelPutDTO modelPutDTO){
        return modelService.update(mlId,modelPutDTO );
    }
    @ApiOperation(value = "获取某一个模型的参数")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ModelSmallVO.class)
    })
    @GetMapping("/models/modelSmall/mlId/{mlId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse getModelSmall(@ApiParam(type = "path", name = "mlId", value = "模型编号", required = true,
        defaultValue = "b5250528-2d6b-49d9-8d69-c5a7bbd504f5"
    ) @PathVariable String mlId){
        return modelService.getModelSmall(mlId);
    }
    @ApiOperation(value = "数据间传输, 获取模型名和模型类型")
    @GetMapping("/models/mdName/mdId/{mlId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ServerResponse getModelName(@ApiParam(type = "path", name = "mlId", value = "模型编号", required = true)@PathVariable String mlId){
        return modelService.getModelName(mlId);
    }
}
