package cn.hctech2006.softcup.isoanalyze.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description = "查询实例列表模型")
public class AnalyzeListVO {
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
    @ApiModelProperty(name = "aeId", value = "分析实例编号")
    private String aeId;
    @ApiModelProperty(name = "quId", value = "分析实例编号")
    private String quId;
    @ApiModelProperty(name = "mlId", value = "分析实例编号")
    private String mlId;

    public String getAeId() {
        return aeId;
    }

    public void setAeId(String aeId) {
        this.aeId = aeId;
    }

    public String getQuId() {
        return quId;
    }

    public void setQuId(String quId) {
        this.quId = quId;
    }

    public String getMlId() {
        return mlId;
    }

    public void setMlId(String mlId) {
        this.mlId = mlId;
    }

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
}
