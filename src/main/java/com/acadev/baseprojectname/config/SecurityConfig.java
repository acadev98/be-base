package com.acadev.baseprojectname.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.acadev.baseprojectname.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtAuthFilter jwtAuthFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
		return http
		.cors().and()
		.csrf(AbstractHttpConfigurer::disable)
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> auth
		// Permitir solicitudes publicas
		.requestMatchers(HttpMethod.POST, "/api/auth/signup/**").permitAll()
		.requestMatchers(HttpMethod.POST, "/api/auth/signin/**").permitAll()
		.requestMatchers(HttpMethod.GET, "/api-docs/**").permitAll()
		.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
		.requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
		.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
        // Permitir solicitudes OPTIONS
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        // Privar todas las demás solicitudes
		.anyRequest().authenticated()
		)
		.authenticationManager(authenticationManager)
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
		.build();
	}

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}
}