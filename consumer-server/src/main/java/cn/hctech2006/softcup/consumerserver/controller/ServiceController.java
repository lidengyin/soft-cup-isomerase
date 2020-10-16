package cn.hctech2006.softcup.consumerserver.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "服务消费管理器")
@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 获取所有服务
     * @return
     */
    @ApiOperation(value = "获取所有服务")
    @GetMapping("/services")
    public Object services(){
        return discoveryClient.getInstances("user-service");
    }

    /**
     * 从所有服务中选择一个服务,轮询
     * @return
     */
    @ApiOperation(value = "从所有服务中选择一个服务, 轮询")
    @GetMapping("/discover")
    public Object discovery(){
        return loadBalancerClient.choose("user-service").getUri().toASCIIString();

    }
}
