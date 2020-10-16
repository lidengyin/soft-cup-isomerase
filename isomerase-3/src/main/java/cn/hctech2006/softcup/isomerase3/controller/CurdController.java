package cn.hctech2006.softcup.isomerase3.controller;


import cn.hctech2006.softcup.isomerase3.http.HttpResult;

public interface CurdController {
    public HttpResult findAll();
    public HttpResult findByDelFlag(byte delFlag);
    public HttpResult findById(Long id);
}
