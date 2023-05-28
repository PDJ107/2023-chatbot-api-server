package com.example.demo.intercepter;

import com.example.demo.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class UserAuthenticationInterceptor implements HandlerInterceptor {

    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        CurrentUserInfo user = new CurrentUserInfo();
        try {
            String token = request.getHeader("X-AUTH-TOKEN");
            jwtUtil.validateToken(token);
            user.setId(jwtUtil.getIdFromToken(token));

        } catch(Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        request.setAttribute("CurrentUserInfo", user);

        return true;
    }

}

