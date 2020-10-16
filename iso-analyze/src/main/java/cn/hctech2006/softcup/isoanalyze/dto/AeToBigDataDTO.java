package cn.hctech2006.softcup.isoanalyze.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "后端大数据分析实例数据传输层数据模型")
public class AeToBigDataDTO {
    @ApiModelProperty(name = "mlName", value = "模型名称")
    private String mlName;
    @ApiModelProperty(name = "aeUrl", value = "模型数据集在线地址")
    private String aeUrl ;
    @ApiModelProperty(name = "aeId", value = "分析实例编号")
    private String aeId;

    public String getAeId() {
        return aeId;
    }

    public void setAeId(String aeId) {
        this.aeId = aeId;
    }

    public String getMlName() {
        return mlName;
    }

    public void setMlName(String mlName) {
        this.mlName = mlName;
    }

    public String getAeUrl() {
        return aeUrl;
    }

    public void setAeUrl(String aeUrl) {
        this.aeUrl = aeUrl;
    }
}
