package cn.hctech2006.softcup.isodataquery.bean;

import java.io.Serializable;
import java.util.Date;

public class NlDatatable implements Serializable {
    private Integer id;

    private String dbId;

    private String dtId;

    private String dtName;

    private Date dtTime;

    private String showFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getDtId() {
        return dtId;
    }

    public void setDtId(String dtId) {
        this.dtId = dtId;
    }

    public String getDtName() {
        return dtName;
    }

    public void setDtName(String dtName) {
        this.dtName = dtName;
    }

    public Date getDtTime() {
        return dtTime;
    }

    public void setDtTime(Date dtTime) {
        this.dtTime = dtTime;
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
        sb.append(", dbId=").append(dbId);
        sb.append(", dtId=").append(dtId);
        sb.append(", dtName=").append(dtName);
        sb.append(", dtTime=").append(dtTime);
        sb.append(", showFlag=").append(showFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}