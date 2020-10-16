package cn.hctech2006.softcup.isoanalyze.service;

import cn.hctech2006.softcup.isoanalyze.common.ServerResponse;
import cn.hctech2006.softcup.isoanalyze.config.FeignHeadersInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "iso-datasource", qualifier = "D")
public interface DatasourceService {
    @GetMapping("dynamics/dbId/{dbId}/dtName/{dtName}")
    public ServerResponse getFields(@PathVariable String dtName, @PathVariable String dbId);
    @GetMapping(value = "/dynamics/dbName/dbId/{dbId}")
    public ServerResponse getDbNameAndDbType(@PathVariable String dbId);
    @GetMapping("dynamics/data/dbId/{dbId}/fdName/{fdName}/dtName/{dtName}")
    public ServerResponse getFields(@PathVariable String dbId, @PathVariable String fdName, @PathVariable String dtName, @RequestParam String paramsStr);
}
