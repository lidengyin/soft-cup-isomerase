package cn.hctech2006.softcup.isomodel.bean;

import java.io.Serializable;
import java.util.Date;

public class NlModel implements Serializable {
    private Integer id;

    private String mlId;

    private String mlName;

    private String mlIntro;

    private String showFlag;

    private String mlAlgo;

    private String mlResult;

    private Date mlTime;

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

    public String getMlName() {
        return mlName;
    }

    public void setMlName(String mlName) {
        this.mlName = mlName;
    }

    public String getMlIntro() {
        return mlIntro;
    }

    public void setMlIntro(String mlIntro) {
        this.mlIntro = mlIntro;
    }

    public String getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }

    public String getMlAlgo() {
        return mlAlgo;
    }

    public void setMlAlgo(String mlAlgo) {
        this.mlAlgo = mlAlgo;
    }

    public String getMlResult() {
        return mlResult;
    }

    public void setMlResult(String mlResult) {
        this.mlResult = mlResult;
    }

    public Date getMlTime() {
        return mlTime;
    }

    public void setMlTime(Date mlTime) {
        this.mlTime = mlTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mlId=").append(mlId);
        sb.append(", mlName=").append(mlName);
        sb.append(", mlIntro=").append(mlIntro);
        sb.append(", showFlag=").append(showFlag);
        sb.append(", mlAlgo=").append(mlAlgo);
        sb.append(", mlResult=").append(mlResult);
        sb.append(", mlTime=").append(mlTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}