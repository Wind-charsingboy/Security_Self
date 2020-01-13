package com.example.security.authorize.self;

import com.example.security.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

//这个类就是用户可以自定义的写一些不需要身份认证的路径 比如/user路径需要管理员权限
//因为这个类也是实现的AuthorizeConfigProvider接口 我们之前写的那个TestAuthorizeConfigManager也会将这些装到Set中去
//@Component
//@Order(Integer.MAX_VALUE)
public class SelfAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //暂时注掉 为rbac提供测试
//        config
//                .antMatchers("/user")
//                .hasRole("ADMIN");

        //rbacService就是RbacServiceImpl这个bean在spring容器中的名字
        //这个配置要最后生效 所以在上面加一个@Order标签 会在集合的最后被读取出来
        config.anyRequest()
                .access("@rbacServiceImpl.hasPermission(request, authentication)");
    }
}
