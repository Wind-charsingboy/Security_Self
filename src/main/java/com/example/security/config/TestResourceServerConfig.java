package com.example.security.config;

import com.example.security.authorize.AuthorizeConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 实现了资源服务器 目前的项目也是一个资源服务器
 * 这个就相当于时浏览器模式中的BrowserSecurityConfig 做一些安全配置
 * ResourceServerConfigurerAdapter是一个适配器 相当于浏览器模式中的WebSecurityConfigurerAdapter
 */
@Configuration
@EnableResourceServer
public class TestResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    /**
     * 不加这个配置 会报错User must be authenticated with Spring Security before authorization can be completed.
     * 覆盖掉configure方法 就是不用spring提供的那一套弹出登录 改为表单登录
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //代表所有的请求都需要身份认证
        http
                //这个的配置是将用户重定向到login的页面上 就是要输入用户名 密码的那个类
                .formLogin()
                .and()
//                .authorizeRequests()
//                    .antMatchers("aaa", "bbbb", "cccc")
//                    .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
                .csrf().disable();

        //这段代码就可以取代掉上面注释的那一段
        authorizeConfigManager.config(http.authorizeRequests());

    }

    //用我自定义的userDetailsService和自定义的密码加密器
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

}
