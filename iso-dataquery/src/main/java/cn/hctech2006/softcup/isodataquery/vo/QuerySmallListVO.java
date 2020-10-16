package cn.hctech2006.softcup.isodataquery.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询实例参数列表数据表现层对象")
public class QuerySmallListVO {
    @ApiModelProperty(name = "field", value = "实例查询字段")
    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
