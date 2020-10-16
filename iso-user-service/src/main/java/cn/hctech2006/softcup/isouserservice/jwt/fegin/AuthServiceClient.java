package cn.hctech2006.softcup.isouserservice.jwt.fegin;


import cn.hctech2006.softcup.isouserservice.jwt.JWT;
import cn.hctech2006.softcup.isouserservice.jwt.hystrix.AuthServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 声明式服务消费
 */
@FeignClient(value = "uaa-server",fallback = AuthServiceHystrix.class)
public interface AuthServiceClient {
    @PostMapping(value = "/oauth/token")
    public JWT getToken(@RequestHeader("Authorization") String authorization,
                        @RequestParam("grant_type") String type,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password);

}
