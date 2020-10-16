package cn.hctech2006.softcup.isoanalyze.controller;

import cn.hctech2006.softcup.isoanalyze.common.ServerResponse;
import cn.hctech2006.softcup.isoanalyze.dto.AnalyzeDTO;
import cn.hctech2006.softcup.isoanalyze.service.impl.AnalyzeServiceImpl;
import cn.hctech2006.softcup.isoanalyze.service.impl.MailServiceImpl;
import cn.hctech2006.softcup.isoanalyze.vo.AnalyzeBigDataVO;
import cn.hctech2006.softcup.isoanalyze.vo.AnalyzeListVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.mail.MessagingException;

@Api(tags = "大数据分析控制器")
@RestController
public class AnalyzeController {
    @Autowired
    private AnalyzeServiceImpl analyzeService;

    @ApiOperation(value = "分析数据生成")
    @PostMapping("/analyzes")
    @ApiResponses({
            @ApiResponse(code=200, message = "ok", response = AnalyzeDTO.class)
    })
    public ServerResponse upload(@ApiIgnore @RequestHeader("Authorization") String Authorization,  @RequestBody AnalyzeDTO analyzeDTO) throws Exception {
        System.out.println("Authorization: "+Authorization);
        return analyzeService.upload(analyzeDTO);
    }
    @ApiOperation(value = "大数据发送信息")
    @PostMapping("/analyzes/aeId")
    public ServerResponse updateByBigData(@ApiParam(type = "query", name = "aeId", value = "数据分析编号", defaultValue = "38668485-44ce-4796-97dc-441ed897ce69") @RequestParam(required = false, defaultValue = "38668485-44ce-4796-97dc-441ed897ce69") String aeId,
                                          @ApiParam(type = "query", name = "aeResult", value = "算法结果")  @RequestParam String aeResult,
                                          @ApiParam(type = "query", name = "alarmFlag", value = "是否预警, 预警1, 不预警0")@RequestParam  String alarmFlag) throws MessagingException {

        return analyzeService.updateByBigData(aeId,aeResult, alarmFlag);

    }
    @ApiOperation(value = "数据分析列表")
    @GetMapping("/analyzes/showFlag/{showFlag}")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "pageNum", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(type = "query", name = "pageSize", value = "页行", required = true, defaultValue = "10"),
            @ApiImplicitParam(type = "path", name = "showFlag", value = "显示标志, 1显示, 0不显示")
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "ok", response = AnalyzeListVO.class)
    })
    public ServerResponse list(int pageNum, int pageSize,  @PathVariable String showFlag){
        return analyzeService.list(pageNum, pageSize, showFlag);
    }

}
