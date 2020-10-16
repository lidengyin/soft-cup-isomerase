package cn.hctech2006.softcup.isomerase3.bean;

import java.io.Serializable;
import java.util.Date;

public class NlCminfo implements Serializable {
    private Integer id;

    private String cmId;

    private String cpuRate;

    private String memRate;

    private Date time;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCmId() {
        return cmId;
    }

    public void setCmId(String cmId) {
        this.cmId = cmId;
    }

    public String getCpuRate() {
        return cpuRate;
    }

    public void setCpuRate(String cpuRate) {
        this.cpuRate = cpuRate;
    }

    public String getMemRate() {
        return memRate;
    }

    public void setMemRate(String memRate) {
        this.memRate = memRate;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", cmId=").append(cmId);
        sb.append(", cpuRate=").append(cpuRate);
        sb.append(", memRate=").append(memRate);
        sb.append(", time=").append(time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}