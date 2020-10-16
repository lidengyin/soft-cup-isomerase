package cn.hctech2006.softcup.isomerase3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
   @Autowired
   private TokenStore tokenStore;
   @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 配置tokenStore
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("/**").tokenStore(tokenStore);
    }


    /**
     * URL访问控制
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        http.cors().and().csrf().disable()
                .authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // web jars
                .antMatchers("/webjars/**").permitAll()
                // 查看SQL监控（druid）
                .antMatchers("/druid/**").permitAll()
                // 首页和登录页面
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()

                .antMatchers("/upload.html").permitAll()
                .antMatchers("/templates/index.html").permitAll()
                // swagger
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
                // 验证码
                .antMatchers("/kaptcha").permitAll()
                .antMatchers("/qrcode/**").permitAll()
                // 服务监控
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/register").permitAll()

                .antMatchers("/imgUpload").permitAll()
                .antMatchers("/videoUpload").permitAll()
                .antMatchers("/fileUpload").permitAll()
                //.antMatchers("/upload/apk").permitAll()
                .antMatchers("/ueditor").permitAll()
                .antMatchers("/content").permitAll()
                .antMatchers("/dynamic").permitAll()
                .antMatchers("/field").permitAll()


                // 其他所有请求需要身份认证
                .anyRequest().authenticated();
        http.headers().frameOptions().disable();
    }

}
