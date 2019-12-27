package com.example.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 这个类实际的作用是如果依赖了spring security 输入的用户名的密码的校验
 */
@Component
@Slf4j
public class TestUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("当前登陆用户是:{}", userName);
        return new User(userName, passwordEncoder.encode("111111"), AuthorityUtils.commaSeparatedStringToAuthorityList("admin, ROLE_USER"));
    }
}
