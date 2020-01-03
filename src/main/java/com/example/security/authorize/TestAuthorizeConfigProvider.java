package com.example.security.authorize;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

//权限模块的实现 例如一些在app项目 浏览器项目中都相同的代码
@Component
@Order(Integer.MIN_VALUE)
public class TestAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers("aaa", "bbbb", "cccc")
                .permitAll();
    }
}
