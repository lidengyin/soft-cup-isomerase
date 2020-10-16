package cn.hctech2006.softcup.isoanalyze.service;

import cn.hctech2006.softcup.isoanalyze.common.ServerResponse;
import cn.hctech2006.softcup.isoanalyze.config.FeignHeadersInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "iso-datafield", qualifier = "B")
public interface DataFieldService {
    @GetMapping("/fields/fdId/{fdId}")
    public ServerResponse getOne(@PathVariable String fdId);
}
