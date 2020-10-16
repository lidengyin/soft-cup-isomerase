package cn.hctech2006.softcup.isomerase3.vo;

import java.util.List;

public class ChartBeanVo {
    private String chartType;
    private String chartOption;
    private List<String> fieldValues;

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

    public List<String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
