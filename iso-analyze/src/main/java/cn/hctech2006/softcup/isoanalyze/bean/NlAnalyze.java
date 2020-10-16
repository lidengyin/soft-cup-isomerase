package cn.hctech2006.softcup.isoanalyze.bean;

import java.io.Serializable;
import java.util.Date;

public class NlAnalyze implements Serializable {
    private Integer id;

    private String aeId;

    private String aeName;

    private String aeResult;

    private String mlId;

    private String quId;

    private String alramFlag;

    private Date aeTime;

    private String aeUrl;

    private String showFlag;
    private static final long serialVersionUID = 1L;

    public String getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }

    public String getAeUrl() {
        return aeUrl;
    }

    public void setAeUrl(String aeUrl) {
        this.aeUrl = aeUrl;
    }

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

    public String getAeName() {
        return aeName;
    }

    public void setAeName(String aeName) {
        this.aeName = aeName;
    }

    public String getAeResult() {
        return aeResult;
    }

    public void setAeResult(String aeResult) {
        this.aeResult = aeResult;
    }

    public String getMlId() {
        return mlId;
    }

    public void setMlId(String mlId) {
        this.mlId = mlId;
    }

    public String getQuId() {
        return quId;
    }

    public void setQuId(String quId) {
        this.quId = quId;
    }

    public String getAlramFlag() {
        return alramFlag;
    }

    public void setAlramFlag(String alramFlag) {
        this.alramFlag = alramFlag;
    }

    public Date getAeTime() {
        return aeTime;
    }

    public void setAeTime(Date aeTime) {
        this.aeTime = aeTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", aeId=").append(aeId);
        sb.append(", aeName=").append(aeName);
        sb.append(", aeResult=").append(aeResult);
        sb.append(", mlId=").append(mlId);
        sb.append(", quId=").append(quId);
        sb.append(", alramFlag=").append(alramFlag);
        sb.append(", aeTime=").append(aeTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}