package cn.hctech2006.softcup.isodatafield.service;

import cn.hctech2006.softcup.isodatafield.common.ServerResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "iso-datatable", qualifier = "1")
public interface DataTableService {

    @ApiOperation(value = "服务间调用--获取某个数据表的详细信息")
    @GetMapping("/datatables/{dtId}")
    public ServerResponse getOne(@PathVariable String dtId);
}
