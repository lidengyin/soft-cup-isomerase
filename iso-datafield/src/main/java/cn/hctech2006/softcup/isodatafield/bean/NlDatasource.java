package cn.hctech2006.softcup.isodatafield.bean;

import java.io.Serializable;

public class NlDatasource implements Serializable {
    private Integer id;

    private String mainid;

    private String field;

    private String datatable;

    private String params;

    private String chartType;

    private String chartOption;
    private String datasourceId;

    private static final long serialVersionUID = 1L;

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDatatable() {
        return datatable;
    }

    public void setDatatable(String datatable) {
        this.datatable = datatable;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mainid=").append(mainid);
        sb.append(", field=").append(field);
        sb.append(", datatable=").append(datatable);
        sb.append(", params=").append(params);
        sb.append(", chartType=").append(chartType);
        sb.append(", chartOption=").append(chartOption);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}