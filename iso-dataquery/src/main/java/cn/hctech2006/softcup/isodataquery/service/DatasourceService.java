package cn.hctech2006.softcup.isodataquery.service;

import cn.hctech2006.softcup.isodataquery.common.ServerResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "iso-datasource", qualifier = "2")
public interface DatasourceService {
    @GetMapping("dynamics/dbId/{dbId}/dtName/{dtName}")
    public ServerResponse getFields(@PathVariable String dtName, @PathVariable String dbId);
    @GetMapping(value = "/dynamics/dbName/dbId/{dbId}")
    public ServerResponse getDbNameAndDbType(@PathVariable String dbId);
    @GetMapping("dynamics/data/dbId/{dbId}/sql/{sql}")
    public ServerResponse getFieldsBySQL(@PathVariable String dbId, @PathVariable String sql);
}
