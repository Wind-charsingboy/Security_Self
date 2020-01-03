package com.example.security.service.impl;

import com.example.authorize.service.RbacService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

//写完这个类之后再在spring security里面写一个权限表达式 来调这个方法 最终spring security判断这个请求能不能过 都是转换为
//一个权限表达式
//在SelfAuthorizeConfigProvider这个类中去写
//最后将这个作为一个模块被添加到其他项目的pom文件中
@Component
public class RbacServiceImpl implements RbacService {
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean hasPermission = false;
        //principal可能是一个anyoumous 一个字符串 匿名的一个身份 如果没有登录的话 就是anyoumousUser
        Object principal = authentication.getPrincipal();
        //只有这个principal是UserDetails的时候 才证明我之前查过数据库 将一些用户的信息存到了这个UserDetails中
        if (principal instanceof UserDetails) {
            String userName = ((UserDetails) principal).getUsername();
            //根据上述拿到的用户名到数据库去查找用户的角色 角色拥有的菜单和按钮 最后可以拿到这个用户所有的url
            Set<String> urls = new HashSet<>();

            //从数据库里面取出的可能会是user/*这种路径 这个地方请求过来的可能是user/1这种路径
            for (String url : urls){
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}
