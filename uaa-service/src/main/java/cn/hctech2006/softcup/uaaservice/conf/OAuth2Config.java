package cn.hctech2006.softcup.uaaservice.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
//开启授权服务器注解
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    /**
     * 配置客户端详情信息（Client Details）
     * 能够使用内存或者JDBC来实现客户端详情服务（ClientDetailsService）
     * 这里写在内存中写死
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //客户端名
                .withClient("user-service")
                //客户端密码
                .secret("123456")
                //作用域
                .scopes("service")
                //使用令牌刷新模式和密码模式（另外两种隐形以及授权码模式）
                //令牌失效后可以刷新
                .authorizedGrantTypes("refresh_token","password")
                //设置过期时间
                .accessTokenValiditySeconds(3600);
    }

    /**
     * 用来配置令牌端点（Token EndPoints）一级令牌（Token）的访问端点和令牌服务
     * 主要是token store类型以及tokenStore签名类型
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /**
         * tokenStore设置tokenStore类型，
         * tokenEnhancer（token增强）：设置非对称算法进行增强
         */
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtAccessTokenEnhancer())
                .authenticationManager(authenticationManager);
    }

    //获取AuthenticationManager进行密码的验证
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    /**
     * token存储方式，这里使用JwtTokenStore方式存储
     * JwtTokenStore需要使用JwtAccessTokenConvert进行token类型的转换
     * 这里是用RSA(公钥/私钥非对称加密方式)，进行token类型转换
     * @return
     */
    @Bean
    public TokenStore tokenStore(){
        //令牌存储格式以及加密转换方式
        return new JwtTokenStore(jwtAccessTokenEnhancer());
    }
    /**
     * JwtAccessToken是用来生成token的转换器的，token令牌默认是有签名的
     * ，资源服务器需要验证这个签名。此处加密以及验证签名有两种：
     * 对称加密以及非对称加密
     * 对称加密需要资源服务器和授权服务器存储同一个key值
     * 非对称加密可以使用密钥进行简爱密，暴露公钥给资源服务器验证签名
     * 引用：https://www.cnblogs.com/fp2952/p/8973613.html
     */
    /**
     * JWTs授权令牌类型转换
     * 使用加密算法进行类型转换，即加密
     * @return
     */
    protected JwtAccessTokenConverter jwtAccessTokenEnhancer(){
        //使用非对称加密RSA对JWT进行加密
        //导入证书
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                //获取上下文私钥，设置令牌转化类型的加密算法
                //或者说根据TOKEN的组成，头部，有效负载，签名
                //签名是对头部，有效负载以及密钥进行加密保护
                //即，头部，有效负载，签名保护
                new ClassPathResource("fzp-jwt.jks"), "fzp123".toCharArray()
        );

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //设置密钥对
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("fzp-jwt"));
        return converter;
    }
}
