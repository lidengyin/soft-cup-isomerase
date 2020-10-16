package cn.hctech2006.softcup.isomodel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "模型修改数据传输层编号")
public class ModelPutDTO {
    @ApiModelProperty(name = "nlName", value = "模型名称")
    private String mlName;
    @ApiModelProperty(name = "nlIntro", value = "模型介绍")
    private String mlIntro;
    @ApiModelProperty(name = "nlAlgo", value = "模型预期算法")
    private String mlAlgo;
    @ApiModelProperty(name = "mlResult", value = "模型预期结果类型")
    private String mlResult;
    @ApiModelProperty(name = "showFlag", value = "显示标志, 1显示, 0不显示")
    private String showFlag;

    @ApiModelProperty(name = "modelSmallDTOS", value = "模型参数传输层对象模型列表")
    List<ModelSmallPutDTO> modelSmallPutDTOS;

    public String getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
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

    public List<ModelSmallPutDTO> getModelSmallPutDTOS() {
        return modelSmallPutDTOS;
    }

    public void setModelSmallPutDTOS(List<ModelSmallPutDTO> modelSmallPutDTOS) {
        this.modelSmallPutDTOS = modelSmallPutDTOS;
    }
}
