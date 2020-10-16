package cn.hctech2006.softcup.isomerase3.bean;

import java.io.Serializable;

public class NlField implements Serializable {
    private String filedId;

    private Integer id;

    private String mainId;

    private String fieldName;

    private String fieldValue;

    private String tableName;

    private String delFlag;

    private String chartType;
    private String chartOption;

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

    private static final long serialVersionUID = 1L;

    public String getFiledId() {
        return filedId;
    }

    public void setFiledId(String filedId) {
        this.filedId = filedId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", filedId=").append(filedId);
        sb.append(", id=").append(id);
        sb.append(", mainId=").append(mainId);
        sb.append(", fieldName=").append(fieldName);
        sb.append(", fieldValue=").append(fieldValue);
        sb.append(", tableName=").append(tableName);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}