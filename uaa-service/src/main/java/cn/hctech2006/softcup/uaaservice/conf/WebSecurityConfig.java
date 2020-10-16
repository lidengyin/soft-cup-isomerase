package cn.hctech2006.softcup.uaaservice.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override@Bean

    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    //登录认证，使用内置登录验证过滤器
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //使用JWT默认关闭跨域攻击防御
        http.csrf().disable()
                //登录异常处理
                .exceptionHandling()
                //认证失败异常处理，返回401未授权界面
                //.authenticationEntryPoint(((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .and()
                .authorizeRequests()
                //路径访问原则,已经认证
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .httpBasic();
    }
}
