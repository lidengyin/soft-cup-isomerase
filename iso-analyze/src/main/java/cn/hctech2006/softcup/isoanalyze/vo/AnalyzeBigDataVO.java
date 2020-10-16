package cn.hctech2006.softcup.isoanalyze.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "大数据分析大数据发送参数数据传输层模型")
public class AnalyzeBigDataVO {

    @ApiModelProperty(name = "aeResult", value = "数据分析结果", example = "2020:09:09 20:20:01")
    private String aeResult;
    @ApiModelProperty(name = "alarmFlag", value = "数据预警标志, 1预警, 0不预警", example = "1")
    private String alarmFlag;

    public String getAeResult() {
        return aeResult;
    }

    public void setAeResult(String aeResult) {
        this.aeResult = aeResult;
    }

    public String getAlarmFlag() {
        return alarmFlag;
    }

    public void setAlarmFlag(String alarmFlag) {
        this.alarmFlag = alarmFlag;
    }
}
