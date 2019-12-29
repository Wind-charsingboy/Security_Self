package com.example.security.authorize.self;

import com.example.security.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

//这个类就是用户可以自定义的写一些不需要身份认证的路径 比如/user路径需要管理员权限
//因为这个类也是实现的AuthorizeConfigProvider接口 我们之前写的那个TestAuthorizeConfigManager也会将这些装到Set中去
@Component
public class SelfAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config
                .antMatchers("/user")
                .hasRole("ADMIN");
    }
}
