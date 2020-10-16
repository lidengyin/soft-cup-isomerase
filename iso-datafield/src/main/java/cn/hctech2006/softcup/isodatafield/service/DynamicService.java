package cn.hctech2006.softcup.isodatafield.service;

import cn.hctech2006.softcup.isodatafield.common.ServerResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "iso-datasource", qualifier = "2")
public interface DynamicService {
    @ApiOperation(value = "sql---查询某数据表所有字段")
    @GetMapping("dynamics/dbId/{dbId}/dtName/{dtName}")
    public ServerResponse getFields(@PathVariable String dtName, @PathVariable String dbId);

}
