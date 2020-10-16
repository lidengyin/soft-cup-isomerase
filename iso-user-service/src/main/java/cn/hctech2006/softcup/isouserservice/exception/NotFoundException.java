package cn.hctech2006.softcup.isouserservice.exception;

public class NotFoundException extends RuntimeException {
    //异常编码
    private Long code;
    //异常自定义信息
    private String sustomMsg;

    public NotFoundException() {
    }

    public NotFoundException(Long code, String sustomMsg) {
        this.code = code;
        this.sustomMsg = sustomMsg;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getSustomMsg() {
        return sustomMsg;
    }

    public void setSustomMsg(String sustomMsg) {
        this.sustomMsg = sustomMsg;
    }
}
