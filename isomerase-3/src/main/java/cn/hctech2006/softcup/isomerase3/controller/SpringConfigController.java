package cn.hctech2006.softcup.isomerase3.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Spring Cloud Config配置中心")
@RestController
@RequestMapping("/config")
@RefreshScope
public class SpringConfigController {
    @Value("${hello}")
    private String hello;
    @GetMapping("/hello")
    public String from(){
        return this.hello;
    }


}
