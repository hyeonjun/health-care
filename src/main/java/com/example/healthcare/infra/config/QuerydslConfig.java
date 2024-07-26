package com.example.healthcare.infra.config;

import com.querydsl.jpa.DefaultQueryHandler;
import com.querydsl.jpa.Hibernate5Templates;
import com.querydsl.jpa.QueryHandler;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Querydsl 5 버전에서 Hibernate 6.x 버전을 사용할 때 이슈 발생
 * 1. transform 사용 시, NoSuchMethodError
 * 2. JPAQueryFactory 에 전달되는 Template 을 HibernateTemplate 에서 JPQLTemplate 으로 변경했을 때, CaseBuilder + sum() 사용 시 에러 발생
 * 3. 아래 구성은 HibernateTemplate 을 그래도 사용하되 transform 사용을 위해 QueryHandler 를 HibernateTemplate 이
 * 기본적으로 사용하는 HibernateHandler 에서 DefaultQueryHandler 로 변경함
 * https://github.com/querydsl/querydsl/issues/3428#issuecomment-1337472853
 * https://hojun-dev.tistory.com/entry/JAVA-QueryDsl-transform-%EB%B0%8F-SqmCaseSearched-%EC%98%A4%EB%A5%98-%ED%95%B4%EA%B2%B0%EB%B0%A9%EB%B2%95-with-Hibernate-6x
 */
@Configuration
public class QuerydslConfig {

  @Bean
  public JPAQueryFactory query(EntityManager em) {
    return new JPAQueryFactory(new CustomHibernate5Templates(), em);
  }

  static class CustomHibernate5Templates extends Hibernate5Templates {
    @Override
    public QueryHandler getQueryHandler() {
      return DefaultQueryHandler.DEFAULT;
    }
  }


}
