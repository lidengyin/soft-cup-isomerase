package cn.hctech2006.softcup.isomodel.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "模型详情数据展示层对象")
public class ModelDetailsVO {
    @ApiModelProperty(name = "nlName", value = "模型名称")
    private String mlName;
    @ApiModelProperty(name = "nlIntro", value = "模型介绍")
    private String mlIntro;
    @ApiModelProperty(name = "nlAlgo", value = "模型预期算法")
    private String mlAlgo;
    @ApiModelProperty(name = "mlResult", value = "模型预期结果类型")
    private String mlResult;
    @ApiModelProperty(name = "modelSmallVOS", value = "模型参数数据展示层对象")
    List<ModelSmallVO> modelSmallVOS;

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

    public List<ModelSmallVO> getModelSmallVOS() {
        return modelSmallVOS;
    }

    public void setModelSmallVOS(List<ModelSmallVO> modelSmallVOS) {
        this.modelSmallVOS = modelSmallVOS;
    }
}
