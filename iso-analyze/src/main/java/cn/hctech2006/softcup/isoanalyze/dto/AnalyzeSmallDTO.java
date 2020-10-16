package cn.hctech2006.softcup.isoanalyze.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "大数据分析子项数据传输层数据模型")
public class AnalyzeSmallDTO {
    @ApiModelProperty(name = "msName", value = "大数据模型参数名称", example = "cpu_rate")
    private String msName;
    @ApiModelProperty(name = "qsField", value = "查询实例子项参数名称", example = "cpu_rate")
    private String qsField;

    public String getMsName() {
        return msName;
    }

    public void setMsName(String msName) {
        this.msName = msName;
    }

    public String getQsField() {
        return qsField;
    }

    public void setQsField(String qsField) {
        this.qsField = qsField;
    }
}
