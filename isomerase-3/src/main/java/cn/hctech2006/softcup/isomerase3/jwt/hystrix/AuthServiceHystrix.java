package cn.hctech2006.softcup.isomerase3.jwt.hystrix;

import cn.hctech2006.softcup.isomerase3.jwt.JWT;
import cn.hctech2006.softcup.isomerase3.jwt.fegin.AuthServiceClient;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceHystrix implements AuthServiceClient {

    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
        return null;
    }
}
