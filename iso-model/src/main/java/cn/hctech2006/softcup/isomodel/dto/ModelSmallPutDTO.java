package cn.hctech2006.softcup.isomodel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "模型参数修改数据传输层模型")
public class ModelSmallPutDTO {
//    @ApiModelProperty(name = "msId", value = "模型参数编号")
//    private String msId;
    @ApiModelProperty(name = "msName", value = "模型参数名")
    private String msName;
    @ApiModelProperty(name = "msIntro", value = "模型参数介绍")
    private String msIntro;

//    public String getMsId() {
//        return msId;
//    }
//
//    public void setMsId(String msId) {
//        this.msId = msId;
//    }

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
