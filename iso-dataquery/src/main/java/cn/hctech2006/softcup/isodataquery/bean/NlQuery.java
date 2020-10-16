package cn.hctech2006.softcup.isodataquery.bean;

import java.io.Serializable;
import java.util.Date;

public class NlQuery implements Serializable {
    private Integer id;

    private String dsId;

    private String quId;

    private String quName;

    private String quType;

    private String quResult;

    private String quUrl;

    private Date quTime;

    private String quParams;

    private String quAlgo;

    private String showFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDsId() {
        return dsId;
    }

    public void setDsId(String dsId) {
        this.dsId = dsId;
    }

    public String getQuId() {
        return quId;
    }

    public void setQuId(String quId) {
        this.quId = quId;
    }

    public String getQuName() {
        return quName;
    }

    public void setQuName(String quName) {
        this.quName = quName;
    }

    public String getQuType() {
        return quType;
    }

    public void setQuType(String quType) {
        this.quType = quType;
    }

    public String getQuResult() {
        return quResult;
    }

    public void setQuResult(String quResult) {
        this.quResult = quResult;
    }

    public String getQuUrl() {
        return quUrl;
    }

    public void setQuUrl(String quUrl) {
        this.quUrl = quUrl;
    }

    public Date getQuTime() {
        return quTime;
    }

    public void setQuTime(Date quTime) {
        this.quTime = quTime;
    }

    public String getQuParams() {
        return quParams;
    }

    public void setQuParams(String quParams) {
        this.quParams = quParams;
    }

    public String getQuAlgo() {
        return quAlgo;
    }

    public void setQuAlgo(String quAlgo) {
        this.quAlgo = quAlgo;
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
        sb.append(", dsId=").append(dsId);
        sb.append(", quId=").append(quId);
        sb.append(", quName=").append(quName);
        sb.append(", quType=").append(quType);
        sb.append(", quResult=").append(quResult);
        sb.append(", quUrl=").append(quUrl);
        sb.append(", quTime=").append(quTime);
        sb.append(", quParams=").append(quParams);
        sb.append(", quAlgo=").append(quAlgo);
        sb.append(", showFlag=").append(showFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}