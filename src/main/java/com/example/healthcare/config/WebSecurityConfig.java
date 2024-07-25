package com.example.healthcare.config;

import com.example.healthcare.account.domain.code.AuthorityType;
import com.example.healthcare.config.security.jwt.JwtAuthenticationProvider;
import com.example.healthcare.config.security.jwt.JwtAuthorizationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtAuthenticationProvider jwtAuthenticationProvider;
  private final static String LOGIN_URL = "/api/v1/auth/login";
  private final static String LOGOUT_URL = "/api/v1/auth/logout";
  private final static String TOKEN_REISSUE = "/api/v1/auth/reissue:token";


  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));
    config.setAllowedMethods(Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT", "OPTIONS", "PATCH"));
    config.setAllowedHeaders(Arrays.asList("*"));
    config.addExposedHeader("*");
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public GrantedAuthorityDefaults grantedAuthorityDefaults() {
    return new GrantedAuthorityDefaults("");
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web
      .ignoring().requestMatchers( "/h2-console/**", "/v3/api-docs",
        "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html",
        "/webjars/**", "/swagger/**");
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
      .csrf(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
      .addFilterBefore(new JwtAuthorizationFilter(jwtAuthenticationProvider), UsernamePasswordAuthenticationFilter.class)
      .sessionManagement((sessionManagement) ->
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .exceptionHandling((exceptionHandling) -> exceptionHandling
          .authenticationEntryPoint(authenticationEntryPoint())
          .accessDeniedHandler(accessDeniedHandler()))
      .authorizeHttpRequests((authorize) ->
        authorize
          .requestMatchers("/api/v1/sy/**").hasRole(AuthorityType.SYSTEM.getValue())
          .requestMatchers("/api/v1/cm/**").hasRole(AuthorityType.COMMON.getValue())
          .requestMatchers("/api/v1/tr/**").hasRole(AuthorityType.TRAINER.getValue())
          .requestMatchers(LOGOUT_URL).authenticated()
          .requestMatchers(LOGIN_URL, TOKEN_REISSUE, "/api/v1/an/**").permitAll()
          .anyRequest().denyAll()
      ).build();
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return (_, response, _) ->
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return (_, response, _) ->
      response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
  }
}