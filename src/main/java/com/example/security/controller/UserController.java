package com.example.security.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/me")
    public Object getUser(Authentication authentication) {
        return authentication;
    }

    /**
     * 解析出特定信息
     * @param user
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/me/personal")
    public Object getCurrentUserPersonal(Authentication user, HttpServletRequest request) throws UnsupportedEncodingException {
        //这个就是token的值
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        //Claims就是jwtToken转换成的对象
        Claims claims =
                //验签
                Jwts.parser().setSigningKey("self".getBytes("UTF-8"))
                        //解析
                        .parseClaimsJws(token).getBody();
        String company = (String) claims.get("company");
        System.out.println("company---" + company);
        return user;
    }
}
