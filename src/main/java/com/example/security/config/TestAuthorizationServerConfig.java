package com.example.security.config;

import com.example.security.service.TestUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAuthorizationServer
@Slf4j
public class TestAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TestUserDetailsService userDetailsService;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    @Autowired
    private TokenStore jwtTokenStore;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("self")
                .secret(passwordEncoder.encode("selfSecret"))
                .accessTokenValiditySeconds(7200)
                //不加这个会报错Invalid redirect: http://localhost:8080/client1/login does not match one of the registered values
                //另外这个地方的localhost和127.0.0.1不是一样的
                .redirectUris("http://127.0.0.1:8080/client1/login")
                .authorizedGrantTypes("password", "refresh_token", "authorization_code")
                .scopes("all")
                .and()
                .withClient("self1")
                .secret(passwordEncoder.encode("selfSecret1"))
                .accessTokenValiditySeconds(7200)
                .redirectUris("http://127.0.0.1:8060/client2/login")
                .authorizedGrantTypes("password", "refresh_token", "authorization_code")
                .scopes("all");
    }


    //3.使用上面两个东西来生成jwt的令牌
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(jwtTokenStore);
        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null){
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancerList = new ArrayList<>();
            enhancerList.add(jwtTokenEnhancer);
            enhancerList.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancerList);
            endpoints
                    .tokenEnhancer(enhancerChain);
        }
    }

    //4.认证服务器的安全配置
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //isAuthenticated()是spring security的授权表达式
        //意思是我要访问我的认证服务器的tokenKey的时候 要经过身份认证
        //这个tokenkey就是accessTokenConverter.setSigningKey("imooc");中的imooc
        //因为应用在解析认证服务器返回的jwt的时候 他要验签名 验签名就要知道我们加密的时候的密钥是什么 这个签名的密钥就是tokenkey
        security.tokenKeyAccess("isAuthenticated()");
    }


}
