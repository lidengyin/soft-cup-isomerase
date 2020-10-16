package cn.hctech2006.softcup.isoanalyze.service;

import cn.hctech2006.softcup.isoanalyze.common.ServerResponse;
import cn.hctech2006.softcup.isoanalyze.config.FeignHeadersInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "iso-model", qualifier = "A")
public interface ModelService {
    /**
     * 返回mlId, mlName的结果Map集合
     * @return
     */
    @GetMapping("/models/mdName/mdId/{mlId}")
    public ServerResponse getMlNameAndMlAlgo(@PathVariable String mlId);
}
