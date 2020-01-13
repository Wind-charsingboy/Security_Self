package com.example.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
//        if (userName != "aa"){
//            throw new UsernameNotFoundException("用户不存在");
//        }
        //根据userName去数据库中查出密码
        //此处不加ROLE_USER会报403 Access_Denied的错误
        return new User(userName, passwordEncoder.encode("123456"),
                //SpringSecurity会自动在前面拼接一个ROLE_
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN, ROLE_USER"));
    }

    //正常从数据库中查询用户的权限信息
//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        UserMessagePO userInfPO = this.userInfoMapper.selectUserInfo(Long.valueOf(userId));
//        return null == userInfPO ? null : new MyUserDetails(userId, "", this.getAuthority(Long.valueOf(userId)), userInfPO.getLastResetPwdTime() == null ? null : new Date(userInfPO.getLastResetPwdTime()), userInfPO.getLastLogoutTime() == null ? null : new Date(userInfPO.getLastLogoutTime()));
//    }
//
//    private List<SimpleGrantedAuthority> getAuthority(Long userId) {
//        List<AuthourityInfoVO> authourityInfoVOS = this.userInfoMapper.selectAuthourityByUserId(userId);
//        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList();
//        if (null != authourityInfoVOS && authourityInfoVOS.size() > 0) {
//            Iterator var4 = authourityInfoVOS.iterator();
//
//            while(var4.hasNext()) {
//                AuthourityInfoVO authourityInfoVO = (AuthourityInfoVO)var4.next();
//                simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authourityInfoVO.getName()));
//            }
//        }
//
//        return simpleGrantedAuthorities;
//    }
}
