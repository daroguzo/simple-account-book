package com.api.simpleaccountbook.jwt;

public class JwtProperties {
    public static final String SECRET = "jinoo";
    public static final int EXPIRATION_TIME = 86400000; // 1 일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
