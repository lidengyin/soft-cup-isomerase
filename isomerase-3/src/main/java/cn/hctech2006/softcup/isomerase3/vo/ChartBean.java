package cn.hctech2006.softcup.isomerase3.vo;

import java.io.Serializable;

public class ChartBean implements Serializable {
    private String fieldValue;
    private String chartType;
    private String chartOption;

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
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
}
