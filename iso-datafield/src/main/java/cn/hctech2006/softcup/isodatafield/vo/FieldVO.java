package cn.hctech2006.softcup.isodatafield.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "数据表字段数据显示模型")
public class FieldVO {
    @ApiModelProperty(name = "fdId", value = "数据表字段唯一标识")
    private String fdId;
    @ApiModelProperty(name = "fdComment", value = "数据表字段注解")
    private String fdComment;
    @ApiModelProperty(name = "fdName", value = "数据表字段名")
    private String fdName;
    @ApiModelProperty(name = "fdTime", value = "数据表字段创建时间")
    private String fdTime;
    @ApiModelProperty(name = "fdType", value = "数据表字段类型")
    private String fdType;

    public String getFdType() {
        return fdType;
    }

    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getFdComment() {
        return fdComment;
    }

    public void setFdComment(String fdComment) {
        this.fdComment = fdComment;
    }

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public String getFdTime() {
        return fdTime;
    }

    public void setFdTime(String fdTime) {
        this.fdTime = fdTime;
    }
}
