package com.api.simpleaccountbook.security;

import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        // status 401 에러로 지정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");

        JSONObject json = new JSONObject();
        String message = "잘못된 토큰입니다.";
        json.put("statusCode", 401);
        json.put("code", "UNAUTHORIZED");
        json.put("message", message);

        PrintWriter out = response.getWriter();
        out.print(json);

    }
}
