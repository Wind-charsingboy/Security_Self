package com.example.security.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Set;

//这个类是用来收集系统里的所有provider 最终在TestSecurityConfig中被引入
@Component
public class TestAuthorizeConfigManager implements AuthorizeConfigManager {
    @Autowired
    private Set<AuthorizeConfigProvider> authorizeConfigProviders;


    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders){
            authorizeConfigProvider.config(config);
        }
        //除了provider提供器里面的路径 其他都需要身份认证才能访问
        config.anyRequest().authenticated();
    }
}
