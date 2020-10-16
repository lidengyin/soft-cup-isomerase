package cn.hctech2006.softcup.isomodel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "模型数据传输层对象")
public class ModelDTO {
    @ApiModelProperty(name = "nlName", value = "模型名称", example = "CUP使用率预警分析模型")
    private String mlName;
    @ApiModelProperty(name = "nlIntro", value = "模型介绍", example = "这是一个对CPU使用达到预警时间报警的模型")
    private String mlIntro;
    @ApiModelProperty(name = "nlAlgo", value = "模型预期算法", example = "Python")
    private String mlAlgo;
    @ApiModelProperty(name = "mlResult", value = "模型预期结果类型", example = "时间")
    private String mlResult;
    @ApiModelProperty(name = "modelSmallDTOS", value = "模型参数传输层对象模型列表")
    List<ModelSmallDTO> modelSmallDTOS;

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

    public List<ModelSmallDTO> getModelSmallDTOS() {
        return modelSmallDTOS;
    }

    public void setModelSmallDTOS(List<ModelSmallDTO> modelSmallDTOS) {
        this.modelSmallDTOS = modelSmallDTOS;
    }
}
