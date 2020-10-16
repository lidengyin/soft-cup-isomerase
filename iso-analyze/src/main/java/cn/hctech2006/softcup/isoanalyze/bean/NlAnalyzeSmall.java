package cn.hctech2006.softcup.isoanalyze.bean;

import java.io.Serializable;

public class NlAnalyzeSmall implements Serializable {
    private Integer id;

    private String aeId;

    private String asId;

    private String msId;

    private String msName;

    private String qsId;

    private String qsField;

    private String showFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAeId() {
        return aeId;
    }

    public void setAeId(String aeId) {
        this.aeId = aeId;
    }

    public String getAsId() {
        return asId;
    }

    public void setAsId(String asId) {
        this.asId = asId;
    }

    public String getMsId() {
        return msId;
    }

    public void setMsId(String msId) {
        this.msId = msId;
    }

    public String getMsName() {
        return msName;
    }

    public void setMsName(String msName) {
        this.msName = msName;
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

    public String getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", aeId=").append(aeId);
        sb.append(", asId=").append(asId);
        sb.append(", msId=").append(msId);
        sb.append(", msName=").append(msName);
        sb.append(", qsId=").append(qsId);
        sb.append(", qsField=").append(qsField);
        sb.append(", showFlag=").append(showFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}