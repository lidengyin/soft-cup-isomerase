package cn.hctech2006.softcup.isomodel.bean;

import java.io.Serializable;

public class NlModelSmall implements Serializable {
    private Integer id;

    private String mlId;

    private String msId;

    private String msName;

    private String msIntro;

    private String showFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMlId() {
        return mlId;
    }

    public void setMlId(String mlId) {
        this.mlId = mlId;
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

    public String getMsIntro() {
        return msIntro;
    }

    public void setMsIntro(String msIntro) {
        this.msIntro = msIntro;
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
        sb.append(", mlId=").append(mlId);
        sb.append(", msId=").append(msId);
        sb.append(", msName=").append(msName);
        sb.append(", msIntro=").append(msIntro);
        sb.append(", showFlag=").append(showFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}