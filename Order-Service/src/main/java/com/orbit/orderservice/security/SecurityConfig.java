package com.orbit.orderservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.POST, "/api/orders").hasRole("CUSTOMER")
				.requestMatchers(HttpMethod.GET, "/api/orders").hasRole("CUSTOMER")
				.requestMatchers(HttpMethod.PATCH, "/api/orders/*/cancel").hasRole("CUSTOMER")
				.requestMatchers(HttpMethod.GET, "/api/orders/*").hasAnyRole("CUSTOMER", "ADMIN")
				.requestMatchers("/api/admin/orders").hasRole("ADMIN")
				.requestMatchers("/api/admin/orders/**").hasRole("ADMIN")

				.anyRequest().authenticated()
			);

		return http.build();
	}
}