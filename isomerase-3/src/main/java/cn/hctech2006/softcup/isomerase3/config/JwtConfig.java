package cn.hctech2006.softcup.isomerase3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

@Configuration
public class JwtConfig {
    @Autowired
            //加密方式
    JwtAccessTokenConverter jwtAccessTokenConverter;
    //tokenStore类型以及解密方式
    @Bean
    @Qualifier("tokenStore")
    TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /**
     * tokenStore加密类型转换
     * @return
     */
    @Bean
    protected JwtAccessTokenConverter jwtAccessTokenEnhancer(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //获得公钥
        Resource resource = new ClassPathResource("public.cert");
        String publicKey;
        try{
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));

        }catch (IOException e){
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;

    }
}
