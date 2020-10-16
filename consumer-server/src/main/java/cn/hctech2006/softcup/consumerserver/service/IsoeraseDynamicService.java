package cn.hctech2006.softcup.consumerserver.service;

import cn.hctech2006.softcup.consumerserver.common.ServerResponse;
import cn.hctech2006.softcup.consumerserver.hystrix.IsomerseDynamicServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-service",contextId = "a", fallback = IsomerseDynamicServiceHystrix.class)
public interface IsoeraseDynamicService {
    @RequestMapping(value = "/umbrella/dynamic/query_list_datasource.do" ,method = RequestMethod.GET
)
    public ServerResponse queryListDatasource();
}
