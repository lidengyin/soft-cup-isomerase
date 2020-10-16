package cn.hctech2006.softcup.isoanalyze.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@ApiModel(description = "查询数据集数据传输层")
public class QueryDTO {
    @ApiModelProperty(name = "fdId", value = "数据表字段编号", example = "711a0c79863f8ae810f4c24a449e31eea480e716010b99c0b7fe39da8453b3c704988fa0a73b4ea39987685633984717")
    private String  fdId;
    @ApiModelProperty(name = "params", value = "请求参数map", example = "{}")
    private Map<String, String> params;
    @ApiModelProperty(name = "chartType", value = "图表类型", example = "折线图")
    private String chartType;
    @ApiModelProperty(name = "chartOption", value = "图表子项", example = "纵轴")
    private String chartOption;

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getChartOption() {
        return chartOption;
    }

    public void setChartOption(String chartOption) {
        this.chartOption = chartOption;
    }
}
