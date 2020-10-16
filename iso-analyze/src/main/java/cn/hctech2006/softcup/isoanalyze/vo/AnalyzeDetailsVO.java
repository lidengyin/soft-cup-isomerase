package cn.hctech2006.softcup.isoanalyze.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "数据分析实例详情数据传输层模型")
public class AnalyzeDetailsVO {
    @ApiModelProperty(name = "quName", value = "查询实例名称")
    private String quName;
    @ApiModelProperty(name = "mlName", value = "模型实例名称")
    private String mlName;
    @ApiModelProperty(name = "aeResult", value = "数据分析结果")
    private String aeResult;
    @ApiModelProperty(name = "alramFlag", value = "数据分析预警信号")
    private String alramFlag;
    @ApiModelProperty(name = "aeTime", value = "数据分析创建时间")
    private String aeTime;
    @ApiModelProperty(name = "aeName", value = "分析实例名称")
    private String aeName;
    @ApiModelProperty(name = "AnalyzeSmallVO", value = "模型参数数据展示层对象列表")
    private List<AnalyzeSmallVO> analyzeSmallVOS;

    public String getQuName() {
        return quName;
    }

    public void setQuName(String quName) {
        this.quName = quName;
    }

    public String getMlName() {
        return mlName;
    }

    public void setMlName(String mlName) {
        this.mlName = mlName;
    }

    public String getAeResult() {
        return aeResult;
    }

    public void setAeResult(String aeResult) {
        this.aeResult = aeResult;
    }

    public String getAlramFlag() {
        return alramFlag;
    }

    public void setAlramFlag(String alramFlag) {
        this.alramFlag = alramFlag;
    }

    public String getAeTime() {
        return aeTime;
    }

    public void setAeTime(String aeTime) {
        this.aeTime = aeTime;
    }

    public String getAeName() {
        return aeName;
    }

    public void setAeName(String aeName) {
        this.aeName = aeName;
    }

    public List<AnalyzeSmallVO> getAnalyzeSmallVOS() {
        return analyzeSmallVOS;
    }

    public void setAnalyzeSmallVOS(List<AnalyzeSmallVO> analyzeSmallVOS) {
        this.analyzeSmallVOS = analyzeSmallVOS;
    }
}
