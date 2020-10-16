package cn.hctech2006.softcup.isomodel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "模型参数数据传输层模型")
public class ModelSmallDTO {
    @ApiModelProperty(name = "msName", value = "模型参数名", example = "cpu_rate")
    private String msName;
    @ApiModelProperty(name = "msIntro", value = "模型参数介绍", example = "cpu使用率")
    private String msIntro;

    public String getMsName() {
        return msName;
    }

    public void setMsName(String msName) {
        this.msName = msName;
    }

    public String getMsIntro() {
        return msIntro;
    }

    public void setMsIntro(String msIntro) {
        this.msIntro = msIntro;
    }
}
