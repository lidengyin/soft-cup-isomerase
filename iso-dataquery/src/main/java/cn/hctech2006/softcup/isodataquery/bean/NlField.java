package cn.hctech2006.softcup.isodataquery.bean;

import java.io.Serializable;
import java.util.Date;

public class NlField implements Serializable {
    private Integer id;

    private String fdId;

    private String dtId;

    private String dbId;

    private String fdComment;

    private String fdName;

    private Date fdTime;

    private String showFlag;

    private String fdType;
    public NlField() {
    }

    public String getFdType() {
        return fdType;
    }

    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getDtId() {
        return dtId;
    }

    public void setDtId(String dtId) {
        this.dtId = dtId;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getFdComment() {
        return fdComment;
    }

    public void setFdComment(String fdComment) {
        this.fdComment = fdComment;
    }

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public Date getFdTime() {
        return fdTime;
    }

    public void setFdTime(Date fdTime) {
        this.fdTime = fdTime;
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
        sb.append(", fdId=").append(fdId);
        sb.append(", dtId=").append(dtId);
        sb.append(", dbId=").append(dbId);
        sb.append(", fdComment=").append(fdComment);
        sb.append(", fdName=").append(fdName);
        sb.append(", fdTime=").append(fdTime);
        sb.append(", showFlag=").append(showFlag);
        sb.append("]");
        return sb.toString();
    }
}