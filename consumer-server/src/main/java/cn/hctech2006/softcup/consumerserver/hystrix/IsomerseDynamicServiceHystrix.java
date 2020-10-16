package cn.hctech2006.softcup.consumerserver.hystrix;

import cn.hctech2006.softcup.consumerserver.common.ServerResponse;
import cn.hctech2006.softcup.consumerserver.service.IsoeraseDynamicService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 熔断降级操作
 */
@Component
public class IsomerseDynamicServiceHystrix implements IsoeraseDynamicService {

    @RequestMapping(value = "/umbrella/dynamic/query_list_datasource.do" ,method = RequestMethod.GET)
    @Override
    public ServerResponse queryListDatasource() {
        return ServerResponse.createBySuccess("服务操作失败,现在是默认的降级操作");
    }
}
