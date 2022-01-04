package com.api.simpleaccountbook.jwt;

public class JwtProperties {
    public static final String SECRET = "jinoo";
    public static final int ACCESS_TOKEN_EXPIRATION_TIME = 18000; // 3초
    public static final int REFRESH_TOKEN_EXPIRATION_TIME = 86400000; // 1일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
