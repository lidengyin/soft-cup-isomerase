package cn.hctech2006.softcup.isouserservice.controller;


import cn.hctech2006.softcup.isouserservice.http.HttpResult;

public interface CurdController {
    public HttpResult findAll();
    public HttpResult findByDelFlag(byte delFlag);
    public HttpResult findById(Long id);
}
