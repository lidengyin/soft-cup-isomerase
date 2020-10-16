package cn.hctech2006.softcup.isomerase3.vo;
import io.swagger.annotations.ApiParam;

import java.util.Map;


public class ParamBean {
    @ApiParam(type = "query", name = "datasourceId", value = "选择的数据源", required = true,defaultValue = "8935abfd1530548fe1c7f28ac0ed3e1d")
    private String datasourceId;
    private String  field;
    private String dataTable;
    private Map<String, String> params;
    private String chartType;
    private String chartOption;

    public void setField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getChartOption() {
        return chartOption;
    }

    public void setChartOption(String chartOption) {
        this.chartOption = chartOption;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }


    public String getDataTable() {
        return dataTable;
    }

    public void setDataTable(String dataTable) {
        this.dataTable = dataTable;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
