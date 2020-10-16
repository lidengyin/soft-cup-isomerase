package cn.hctech2006.softcup.isoanalyze.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "大数据分析数据传输层模型")
public class AnalyzeDTO {
    @ApiModelProperty(name = "aeName", value = "分析实例名称", example = "大数据对CUP预警数据集的分析")
    private String aeName;
    @ApiModelProperty(name = "mlId", value = "模型编号", example = "b5250528-2d6b-49d9-8d69-c5a7bbd504f5")
    private String mlId;
    @ApiModelProperty(name = "quId", value = "使用查询实例名称", example = "ccb1a282-875e-41b1-973e-7f8a4bcf7776")
    private String quId;
    @ApiModelProperty(name = "analyzeSmallDTOS", value = "模型参数数据传输层对象列表")
    private List<AnalyzeSmallDTO> analyzeSmallDTOS;

    public String getMlId() {
        return mlId;
    }

    public void setMlId(String mlId) {
        this.mlId = mlId;
    }

    public List<AnalyzeSmallDTO> getAnalyzeSmallDTOS() {
        return analyzeSmallDTOS;
    }

    public void setAnalyzeSmallDTOS(List<AnalyzeSmallDTO> analyzeSmallDTOS) {
        this.analyzeSmallDTOS = analyzeSmallDTOS;
    }

    public String getAeName() {
        return aeName;
    }

    public void setAeName(String aeName) {
        this.aeName = aeName;
    }



    public String getQuId() {
        return quId;
    }

    public void setQuId(String quId) {
        this.quId = quId;
    }

}
