package com.example.healthcare.config;

import com.example.healthcare.account.domain.code.AuthorityType;
import com.example.healthcare.config.security.jwt.AuthExceptionFilter;
import com.example.healthcare.config.security.jwt.JwtAuthenticationFilter;
import com.example.healthcare.config.security.jwt.JwtAuthorizationFilter;
import com.example.healthcare.config.security.jwt.JwtUtil;
import com.example.healthcare.config.security.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig implements WebMvcConfigurer {

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final static String LOGIN_URL = "/api/v1/sign-in";
  private final static String LOGOUT_URL = "/api/v1/sign-out";


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
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(RedisTemplate redisTemplate) throws Exception {
    JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, redisTemplate);
    filter.setFilterProcessesUrl(LOGIN_URL);
    filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
    return filter;
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    // todo 이후 필요 시 추가
    return WebSecurity::ignoring; //.anyRequest().requestMatchers("");
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
      .csrf(AbstractHttpConfigurer::disable)
      .addFilterBefore(new JwtAuthorizationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class)
      .addFilterBefore(new AuthExceptionFilter(), JwtAuthorizationFilter.class)
      .sessionManagement((sessionManagement) ->
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests((authorize) ->
        authorize
          .requestMatchers("/api/v1/sy/**").hasRole(AuthorityType.SYSTEM.getValue())
          .requestMatchers("/api/v1/cm/**").hasRole(AuthorityType.COMMON.getValue())
          .requestMatchers("/api/v1/tr/**").hasRole(AuthorityType.TRAINER.getValue())
          .requestMatchers(LOGOUT_URL).authenticated()
          .requestMatchers(LOGIN_URL).permitAll()
          .anyRequest().denyAll()
      ).build();
  }
}