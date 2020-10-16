package cn.hctech2006.softcup.isodataquery;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

//开启服务熔断
@EnableHystrix
//开启声明式服务消费客户端
@EnableFeignClients
//开启服务发现
@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@MapperScan("cn.hctech2006.softcup.isodataquery.mapper")
public class IsoDataqueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsoDataqueryApplication.class, args);
    }

}
