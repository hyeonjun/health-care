package com.example.healthcare.util.repository;

import com.querydsl.jpa.HQLTemplates;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLSerializer;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class NativeQueryUtil {

  private NativeQueryUtil() {
  }

  public static Query toNativeCountQuery(JPAQuery<?> jpaQuery, EntityManager em) {
    JPQLSerializer serializer = getSerializer(em, jpaQuery);
    String hql = serializer.toString();
    String sql = getSqlFromHql(em, hql);

    // Make SQL to Native Query without binding parameter
    String countSql = convertToCountQueryString(sql);
    Query nativeQuery = em.createNativeQuery(countSql);

    // Binding Parameter
    Map<String, Integer> positionalParamMap = new HashMap<>();
    int i = 1;
    for (String label : serializer.getConstantToLabel().values()) {
      positionalParamMap.put(label, i++);
    }
    Object[] paramList = getParameterList(serializer, positionalParamMap);
    for (int j = 0; j < paramList.length; j++) {
      nativeQuery.setParameter(j + 1, paramList[j]);
    }
    return nativeQuery;
  }

  public static String convertToCountQueryString(String sql) {
    String countSql = "select count(1) from (" + sql + ") _tmp_" + System.currentTimeMillis();
    String[] sqlList = countSql.split("\\?");
    int size = sqlList.length;
    StringBuilder countSQL = new StringBuilder();
    int i = 0;
    for (String s : sqlList) {
      i++;
      countSQL.append(s);
      if (size != i) {
        countSQL.append("?");
        countSQL.append(i);
      }
    }
    return countSQL.toString();
  }

  private static String getSqlFromHql(EntityManager entityManager, String hql) {
    Session session = entityManager.unwrap(Session.class);
    return session.createQuery(hql).unwrap(org.hibernate.query.Query.class).getQueryString();
  }

  private static <E> JPQLSerializer getSerializer(EntityManager entityManager, JPQLQuery<E> jpqlQuery) {
    JPQLSerializer serializer = new JPQLSerializer(HQLTemplates.DEFAULT);
    serializer.serialize(jpqlQuery.getMetadata(), false, null);
    return serializer;
  }

  private static Object[] getParameterList(JPQLSerializer serializer, Map<String, Integer> positionalParamMap) {
    Iterator<Entry<Object, String>> constantToAllLabelsIterator = serializer.getConstantToLabel().entrySet().iterator();
    Map<String, Object> paramMapLabelToValue = new HashMap<>();

    while (constantToAllLabelsIterator.hasNext()) {
      Entry<Object, String> entry = constantToAllLabelsIterator.next();
      paramMapLabelToValue.put(entry.getValue(), entry.getKey());
    }

    Object[] paramList = new Object[positionalParamMap.size()];

    for (Entry<String, Integer> entry : positionalParamMap.entrySet()) {
      Object parameter = paramMapLabelToValue.get(entry.getKey());
      if (parameter instanceof Enum) {
        parameter = parameter.toString();
      }
      paramList[entry.getValue() - 1] = parameter;
    }

    return paramList;
  }
}
