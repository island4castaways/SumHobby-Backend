package com.example.sumhobby.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

import com.example.sumhobby.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http security filter
		http.cors()
			.and()
			.csrf().disable()
			.httpBasic().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
				.antMatchers(
						"/", "/auth/**", "/admin/signin", 
						"/class/top-rated", "/class/**", 
						"/lecture", "/cart", "/checkout", 
						"/review/showreview", "/review/checkReview"
						).permitAll()
				.anyRequest().authenticated();
		
		//filter �벑濡�, 留� �슂泥�留덈떎 CorsFilter �떎�뻾�븳 �썑 jwtAuthenticationFilter �떎�뻾�븿
		http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
	}

}
