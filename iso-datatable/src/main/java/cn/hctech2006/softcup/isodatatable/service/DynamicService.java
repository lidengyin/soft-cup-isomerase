package cn.hctech2006.softcup.isodatatable.service;

import cn.hctech2006.softcup.isodatatable.common.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "iso-datasource",qualifier = "2")
public interface DynamicService {
    @GetMapping("/dynamics/dbId/{dbId}")
    public ServerResponse getDatatables(@PathVariable String dbId);
}
