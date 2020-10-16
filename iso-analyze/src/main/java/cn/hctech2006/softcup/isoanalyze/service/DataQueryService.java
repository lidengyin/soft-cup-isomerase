package cn.hctech2006.softcup.isoanalyze.service;

import cn.hctech2006.softcup.isoanalyze.common.ServerResponse;

import cn.hctech2006.softcup.isoanalyze.config.FeignHeadersInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "iso-dataquery")
public interface DataQueryService {
    /**
     * 服务间传输, 根据查询编号获取查询名
     * @param quId
     * @return
     */
    @GetMapping("/interaction/queryName/{quId}")
    public ServerResponse getQueryName(@PathVariable String quId);

    /**
     * 查询一个查询实例, 返回详细结果带查询参数
     * @param quId
     * @return
     * @throws Exception
     */
    @GetMapping("/interaction/{quId}")
    public ServerResponse queryOneToFg(@PathVariable String quId);
}
