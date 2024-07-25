package com.example.healthcare.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() { // swagger Failed to fetch 오류
    return new OpenAPI()     // 서버는 https 인데, swagger 에서는 http 로 요청을 보내어 발생
      .addServersItem(new Server().url("/")); // servers url 설정으로 "/"를 주면 현재 url 로 요청됨
  }
}
