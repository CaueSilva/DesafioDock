package com.docktech.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private static final String[] PUBLIC_MATCHERS = {"/h2-console/**", "/api/auth/**"};
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JWTUtil jwtUtil;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        if (Arrays.asList(env.getActiveProfiles()).isEmpty() || Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.cors().and().csrf().disable();
        http.authorizeHttpRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil, userDetailsService));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
    

	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
	
    @Autowired
    public void initialize(AuthenticationManagerBuilder builder) throws Exception {
    	builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
}
