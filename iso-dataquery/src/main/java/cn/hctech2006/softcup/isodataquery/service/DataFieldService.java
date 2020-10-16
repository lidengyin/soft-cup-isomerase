package cn.hctech2006.softcup.isodataquery.service;

import cn.hctech2006.softcup.isodataquery.common.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "iso-datafield")
public interface DataFieldService {
    @GetMapping("/fields/fdId/{fdId}")
    public ServerResponse getOne(@PathVariable String fdId);
}
