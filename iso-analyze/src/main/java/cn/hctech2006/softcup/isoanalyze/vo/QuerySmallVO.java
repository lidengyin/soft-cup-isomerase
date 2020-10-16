package cn.hctech2006.softcup.isoanalyze.vo;

import java.util.List;

public class QuerySmallVO {
    private String chartType;
    private String chartOption;
    private String[] fieldValues;
    private List<ParamDTO> paramDTOS;

    public List<ParamDTO> getParamDTOS() {
        return paramDTOS;
    }

    public void setParamDTOS(List<ParamDTO> paramDTOS) {
        this.paramDTOS = paramDTOS;
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

    public String[] getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(String[] fieldValues) {
        this.fieldValues = fieldValues;
    }
}
