package com.masai.app.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter 
{
	@Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)   
			throws ServletException, IOException, java.io.IOException 
	{    // Get Authentication details  
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  
		if (null != authentication) 
		{     // Map with secret key    
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstrants.JWT_KEY.getBytes());     
			// Create the jwt token using api    
			String jwt = Jwts.builder().setIssuer("bhavesh").setSubject("Jwt Token")      
					.claim("username", authentication.getName())      
					.claim("authorities", getAuthorities(authentication.getAuthorities())).setIssuedAt(new Date())      
					.setExpiration(new Date(new Date().getTime() + 30000000)).signWith(key).compact();    
			// set jwt token in response header    
			response.setHeader(SecurityConstrants.JWT_HEADER, jwt);   
			}      filterChain.doFilter(request, response);  
			}   
	private String getAuthorities(Collection<? extends GrantedAuthority> authorities) 
	{   
		Set<String> authoritiesSet = new HashSet<>();   
		for (GrantedAuthority authority : authorities) 
		{    
			authoritiesSet.add(authority.getAuthority());   
			}   
		return String.join(",", authoritiesSet); 
		// admin,user  
		}    
	@Override  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException 
	{   // TODO Auto-generated method stub   
		return !request.getServletPath().equals("/signIn");  }  

		}
