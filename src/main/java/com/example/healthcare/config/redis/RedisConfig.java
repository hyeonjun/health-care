package com.example.healthcare.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableCaching
@RequiredArgsConstructor
@EnableTransactionManagement
public class RedisConfig {

  private final RedisProperties redisProperties;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(
      redisProperties.getHost(), redisProperties.getPort());
    connectionFactory.setValidateConnection(true); // 연결 유효성 검사
    return connectionFactory;
  }

  @Primary
  @Bean
  // 트랜잭션 지원. 하나의 로직이 실패하여 오류가 나는 경우 수행한 작업을 모두 취소
  public RedisTemplate<?, ?> redisTemplate() {
    RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
//    redisTemplate.setEnableTransactionSupport(true);
    return redisTemplate;
  }

  // JpaTransactionManager 사용
  @Bean
  public PlatformTransactionManager transactionManager() {
    return new JpaTransactionManager();
  }
}
