package com.masai.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity 
public class SecurityConfig extends WebSecurityConfiguration {

  @Autowired
  private UserDetailsService customerDetailsService;

  @Autowired
  private JwtTokenValidatorFilter jwtTokenValidatorFilter;

  @Autowired
  private JwtTokenGeneratorFilter jwtTokenGeneratorFilter;

  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
        .requestMatchers("/signIn").permitAll().anyRequest().authenticated().and()
        .addFilterBefore(jwtTokenValidatorFilter,
                        UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(jwtTokenGeneratorFilter,
                        UsernamePasswordAuthenticationFilter.class);
    
  }

  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customerDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
