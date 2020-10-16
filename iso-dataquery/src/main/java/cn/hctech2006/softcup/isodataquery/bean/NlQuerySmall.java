package cn.hctech2006.softcup.isodataquery.bean;

import java.io.Serializable;

public class NlQuerySmall implements Serializable {
    private Integer id;

    private String quId;

    private String qsId;

    private String qsField;

    private String qsParams;

    private String qsChartType;

    private String qsChartOption;

    private String dtName;

    private String dbType;

    private String dbDatabase;

    private String fdId;

    private static final long serialVersionUID = 1L;

    public String getDtName() {
        return dtName;
    }

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public void setDtName(String dtName) {
        this.dtName = dtName;
    }

    public String getDbDatabase() {
        return dbDatabase;
    }

    public void setDbDatabase(String dbDatabase) {
        this.dbDatabase = dbDatabase;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuId() {
        return quId;
    }

    public void setQuId(String quId) {
        this.quId = quId;
    }

    public String getQsId() {
        return qsId;
    }

    public void setQsId(String qsId) {
        this.qsId = qsId;
    }

    public String getQsField() {
        return qsField;
    }

    public void setQsField(String qsField) {
        this.qsField = qsField;
    }

    public String getQsParams() {
        return qsParams;
    }

    public void setQsParams(String qsParams) {
        this.qsParams = qsParams;
    }

    public String getQsChartType() {
        return qsChartType;
    }

    public void setQsChartType(String qsChartType) {
        this.qsChartType = qsChartType;
    }

    public String getQsChartOption() {
        return qsChartOption;
    }

    public void setQsChartOption(String qsChartOption) {
        this.qsChartOption = qsChartOption;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", quId=").append(quId);
        sb.append(", qsId=").append(qsId);
        sb.append(", qsField=").append(qsField);
        sb.append(", qsParams=").append(qsParams);
        sb.append(", qsChartType=").append(qsChartType);
        sb.append(", qsChartOption=").append(qsChartOption);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}