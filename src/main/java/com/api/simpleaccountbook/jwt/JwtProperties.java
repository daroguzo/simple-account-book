package com.api.simpleaccountbook.jwt;

public class JwtProperties {
    public static final String SECRET = "jinoo";
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000L; // 1시간
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 14 * 24 * 60 * 1000L; // 14일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
