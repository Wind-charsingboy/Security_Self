package com.example.security.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface RbacService {
    //判断当前用户能否访问
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
