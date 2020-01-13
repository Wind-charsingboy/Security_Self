package com.example.security.config;

import com.example.security.authorize.AuthorizeConfigManager;
import com.example.security.service.TestUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

@Configuration
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private AuthorizeConfigManager authorizeConfigManager;
//
//    /**
//     * 不加这个配置 会报错User must be authenticated with Spring Security before authorization can be completed.
//     * 覆盖掉configure方法 就是不用spring提供的那一套弹出登录 改为表单登录
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //代表所有的请求都需要身份认证
//        http
//                .formLogin()
//                .and()
////                .authorizeRequests()
////                    .antMatchers("aaa", "bbbb", "cccc")
////                    .permitAll()
////                .anyRequest()
////                .authenticated()
////                .and()
//                .csrf().disable();
//
//        //这段代码就可以取代掉上面注释的那一段
//        authorizeConfigManager.config(http.authorizeRequests());
//
//    }
//
//    //用我自定义的userDetailsService和自定义的密码加密器
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
////    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



}
