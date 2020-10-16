package cn.hctech2006.softcup.isomodel.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "大数据模型数据展示层模型")
public class ModelListVO {
    @ApiModelProperty(name = "mlId", value = "模型编号")
    private String mlId;
    @ApiModelProperty(name = "nlName", value = "模型名称")
    private String mlName;
    @ApiModelProperty(name = "nlIntro", value = "模型介绍")
    private String mlIntro;
    @ApiModelProperty(name = "nlAlgo", value = "模型预期算法")
    private String mlAlgo;
    @ApiModelProperty(name = "mlResult", value = "模型预期结果类型")
    private String mlResult;

    public String getMlId() {
        return mlId;
    }

    public void setMlId(String mlId) {
        this.mlId = mlId;
    }

    public String getMlName() {
        return mlName;
    }

    public void setMlName(String mlName) {
        this.mlName = mlName;
    }

    public String getMlIntro() {
        return mlIntro;
    }

    public void setMlIntro(String mlIntro) {
        this.mlIntro = mlIntro;
    }

    public String getMlAlgo() {
        return mlAlgo;
    }

    public void setMlAlgo(String mlAlgo) {
        this.mlAlgo = mlAlgo;
    }

    public String getMlResult() {
        return mlResult;
    }

    public void setMlResult(String mlResult) {
        this.mlResult = mlResult;
    }
}
