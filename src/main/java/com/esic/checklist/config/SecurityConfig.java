package com.esic.checklist.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("#{'${cors.allowed-origins}'.split(',')}")
	 private String[] allowedOrigins;

//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http
//		.csrf(csrf -> csrf.disable())
//		.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//				.formLogin(login -> login.disable()) // or httpBasic if you want
//				.httpBasic(basic -> basic.disable());
//		return http.build();
//	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        return http
        		.cors(cors -> cors.configurationSource(request -> {
        	        CorsConfiguration config = new CorsConfiguration();
        	        config.setAllowedOrigins(Arrays.asList(allowedOrigins));
        	        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        	        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-CSRF-TOKEN"));
        	        config.setAllowCredentials(true);
        	        return config;
        	    }))
        	    .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/auth/login", 
                        		"/index.html",
                                "/",
                                "/static/**",
                                "/assets/**",
                                "/*.js",
                                "/*.css",
                                "/*.ico").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
