package cn.hctech2006.softcup.isoanalyze.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "分析实例参数数据展示层模型")
public class AnalyzeSmallVO {
    @ApiModelProperty(name = "msName", value = "大数据模型参数名称")
    private String msName;
    @ApiModelProperty(name = "qsField", value = "查询实例子项参数名称")
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
