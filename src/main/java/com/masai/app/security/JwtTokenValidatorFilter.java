package com.masai.app.security;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {
	@Override  
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)    throws ServletException, IOException 
	{    String jwt = request.getHeader(SecurityConstrants.JWT_HEADER);   
	System.out.println(jwt);   
	if (jwt != null) 
	{    
		try 
		{     
			jwt = jwt.substring(7);    
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstrants.JWT_KEY.getBytes());     
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();    
			String username = String.valueOf(claims.get("username"));  
			String authorities = (String) claims.get("authorities");    
			List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);  
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, auths);    
			SecurityContextHolder.getContext().setAuthentication(auth);  
			} 
		catch (Exception e) 
		{     
			throw new BadCredentialsException("Invalid JWT token received..!");    
			}   
		}   
	filterChain.doFilter(request, response);  
	}
@Override  
protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException 
{
	// TODO Auto-generated method stub   
	return request.getServletPath().equals("/signIn");  }  
}
