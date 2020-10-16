package cn.hctech2006.softcup.isoanalyze.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询结果集修改数据传输层模型")
public class QueryPutDTO {

    @ApiModelProperty(name = "quName", value = "查询结果名称")
    private String quName;
    @ApiModelProperty(name = "quTime", value = "查询时间")
    private String quTime;
    @ApiModelProperty(name = "quType", value = "查询预期结果类型")
    private String quType;
    @ApiModelProperty(name = "quResult", value = "查询真实结果")
    private String quResult;
    @ApiModelProperty(name = "quUrl", value = "查询实例数据集在线地址")
    private String quUrl;
    @ApiModelProperty(name = "quAlgo", value = "查询实例预期使用算法类型")
    private String quAlgo;
    @ApiModelProperty(name = "showFlag", value = "查询实例是否显示标志, 1显示, 0不显示")
    private String showFlag;

    public String getQuName() {
        return quName;
    }

    public void setQuName(String quName) {
        this.quName = quName;
    }

    public String getQuTime() {
        return quTime;
    }

    public void setQuTime(String quTime) {
        this.quTime = quTime;
    }

    public String getQuType() {
        return quType;
    }

    public void setQuType(String quType) {
        this.quType = quType;
    }

    public String getQuResult() {
        return quResult;
    }

    public void setQuResult(String quResult) {
        this.quResult = quResult;
    }

    public String getQuUrl() {
        return quUrl;
    }

    public void setQuUrl(String quUrl) {
        this.quUrl = quUrl;
    }

    public String getQuAlgo() {
        return quAlgo;
    }

    public void setQuAlgo(String quAlgo) {
        this.quAlgo = quAlgo;
    }

    public String getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }

}
