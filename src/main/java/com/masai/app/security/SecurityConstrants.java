package com.masai.app.security;

public class SecurityConstrants {
  public static final String JWT_KEY = "jwtsecretkey";
  public static final String JWT_HEADER = "Authorization";
  public static final String JWT_PREFIX = "Bearer ";
  public static final long JWT_EXPIRATION_TIME = 30000000; // 30 seconds
}

