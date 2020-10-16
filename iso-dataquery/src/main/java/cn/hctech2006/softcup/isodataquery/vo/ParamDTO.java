package cn.hctech2006.softcup.isodataquery.vo;

import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "查询参数描述")
public class ParamDTO  implements Serializable {
    @ApiModelProperty(name = "pmName", value = "比较参数名")
    private String pmName;
    @ApiModelProperty(name = "pmValue", value = "比较参数值")
    private String pmValue;
    @ApiModelProperty(name = "pmType", value = "比较参数类型")
    private String pmType;
    @ApiModelProperty(name = "judgeType", value = "比较方式")
    private String judgeType;


    public String getPmValue() {
        return pmValue;
    }

    public void setPmValue(String pmValue) {
        this.pmValue = pmValue;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public String getPmType() {
        return pmType;
    }

    public void setPmType(String pmType) {
        this.pmType = pmType;
    }

    public String getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(String judgeType) {
        this.judgeType = judgeType;
    }
}
