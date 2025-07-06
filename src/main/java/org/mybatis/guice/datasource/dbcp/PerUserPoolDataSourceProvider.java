/*
 *    Copyright 2009-2025 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.guice.datasource.dbcp;

import jakarta.inject.Named;
import jakarta.inject.Provider;

import java.util.Map;
import java.util.Map.Entry;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.datasources.PerUserPoolDataSource;

/**
 * Provides the Apache commons-dbcp2 {@code PerUserPoolDataSource}.
 */
public final class PerUserPoolDataSourceProvider implements Provider<DataSource> {

  private final PerUserPoolDataSource dataSource = new PerUserPoolDataSource();

  @com.google.inject.Inject(optional = true)
  public void setConnectionPoolDataSource(ConnectionPoolDataSource cpds) {
    dataSource.setConnectionPoolDataSource(cpds);
  }

  @com.google.inject.Inject(optional = true)
  public void setDataSourceName(@Named("DBCP.name") String name) {
    dataSource.setDataSourceName(name);
  }

  @com.google.inject.Inject(optional = true)
  public void setDefaultAutoCommit(@Named("JDBC.autoCommit") boolean autoCommit) {
    dataSource.setDefaultAutoCommit(autoCommit);
  }

  @com.google.inject.Inject(optional = true)
  public void setDefaultReadOnly(@Named("DBCP.defaultReadOnly") boolean defaultReadOnly) {
    dataSource.setDefaultReadOnly(defaultReadOnly);
  }

  @com.google.inject.Inject(optional = true)
  public void setDefaultTransactionIsolation(
      @Named("DBCP.defaultTransactionIsolation") int defaultTransactionIsolation) {
    dataSource.setDefaultTransactionIsolation(defaultTransactionIsolation);
  }

  @com.google.inject.Inject(optional = true)
  public void setDescription(@Named("DBCP.description") String description) {
    dataSource.setDescription(description);
  }

  @com.google.inject.Inject(optional = true)
  public void setJndiEnvironment(@Named("DBCP.jndi.key") String key, @Named("DBCP.jndi.value") String value) {
    dataSource.setJndiEnvironment(key, value);
  }

  @com.google.inject.Inject(optional = true)
  public void setLoginTimeout(@Named("JDBC.loginTimeout") int loginTimeout) {
    dataSource.setLoginTimeout(loginTimeout);
  }

  @com.google.inject.Inject(optional = true)
  public void setMinEvictableIdleTimeMillis(@Named("DBCP.minEvictableIdleTimeMillis") int minEvictableIdleTimeMillis) {
    dataSource.setDefaultMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
  }

  @com.google.inject.Inject(optional = true)
  public void setNumTestsPerEvictionRun(@Named("DBCP.numTestsPerEvictionRun") int numTestsPerEvictionRun) {
    dataSource.setDefaultNumTestsPerEvictionRun(numTestsPerEvictionRun);
  }

  @com.google.inject.Inject(optional = true)
  public void setRollbackAfterValidation(@Named("DBCP.rollbackAfterValidation") boolean rollbackAfterValidation) {
    dataSource.setRollbackAfterValidation(rollbackAfterValidation);
  }

  @com.google.inject.Inject(optional = true)
  public void setTestOnBorrow(@Named("DBCP.testOnBorrow") boolean testOnBorrow) {
    dataSource.setDefaultTestOnBorrow(testOnBorrow);
  }

  @com.google.inject.Inject(optional = true)
  public void setTestOnReturn(@Named("DBCP.testOnReturn") boolean testOnReturn) {
    dataSource.setDefaultTestOnReturn(testOnReturn);
  }

  @com.google.inject.Inject(optional = true)
  public void setTestWhileIdle(@Named("DBCP.testWhileIdle") boolean testWhileIdle) {
    dataSource.setDefaultTestWhileIdle(testWhileIdle);
  }

  @com.google.inject.Inject(optional = true)
  public void setTimeBetweenEvictionRunsMillis(
      @Named("DBCP.timeBetweenEvictionRunsMillis") int timeBetweenEvictionRunsMillis) {
    dataSource.setDefaultTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
  }

  @com.google.inject.Inject(optional = true)
  public void setValidationQuery(@Named("DBCP.validationQuery") String validationQuery) {
    dataSource.setValidationQuery(validationQuery);
  }

  @com.google.inject.Inject(optional = true)
  public void setDefaultMaxTotal(@Named("DBCP.maxTotal") int maxTotal) {
    dataSource.setDefaultMaxTotal(maxTotal);
  }

  @com.google.inject.Inject(optional = true)
  public void setDefaultMaxIdle(@Named("DBCP.maxIdle") int defaultMaxIdle) {
    dataSource.setDefaultMaxIdle(defaultMaxIdle);
  }

  @com.google.inject.Inject(optional = true)
  public void setDefaultMaxWait(@Named("DBCP.maxWait") int defaultMaxWait) {
    dataSource.setDefaultMaxWaitMillis(defaultMaxWait);
  }

  /**
   * Sets the per user default auto commit.
   *
   * @param perUserDefaultAutoCommit
   *          the per user default auto commit
   */
  @com.google.inject.Inject(optional = true)
  public void setPerUserDefaultAutoCommit(@PerUserDefaultAutoCommit Map<String, Boolean> perUserDefaultAutoCommit) {
    for (Entry<String, Boolean> entry : perUserDefaultAutoCommit.entrySet()) {
      dataSource.setPerUserDefaultAutoCommit(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Sets the per user default read only.
   *
   * @param perUserDefaultReadOnly
   *          the per user default read only
   */
  @com.google.inject.Inject(optional = true)
  public void setPerUserDefaultReadOnly(@PerUserDefaultReadOnly Map<String, Boolean> perUserDefaultReadOnly) {
    for (Entry<String, Boolean> entry : perUserDefaultReadOnly.entrySet()) {
      dataSource.setPerUserDefaultReadOnly(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Sets the per user default transaction isolation.
   *
   * @param perUserDefaultTransactionIsolation
   *          the per user default transaction isolation
   */
  @com.google.inject.Inject(optional = true)
  public void setPerUserDefaultTransactionIsolation(
      @PerUserDefaultTransactionIsolation Map<String, Integer> perUserDefaultTransactionIsolation) {
    for (Entry<String, Integer> entry : perUserDefaultTransactionIsolation.entrySet()) {
      dataSource.setPerUserDefaultTransactionIsolation(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Sets the per user max total.
   *
   * @param perUserMaxTotal
   *          the per user max total
   */
  @com.google.inject.Inject(optional = true)
  public void setPerUserMaxTotal(@PerUserMaxTotal Map<String, Integer> perUserMaxTotal) {
    for (Entry<String, Integer> entry : perUserMaxTotal.entrySet()) {
      dataSource.setPerUserMaxTotal(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Sets the per user max idle.
   *
   * @param perUserMaxIdle
   *          the per user max idle
   */
  @com.google.inject.Inject(optional = true)
  public void setPerUserMaxIdle(@PerUserMaxIdle Map<String, Integer> perUserMaxIdle) {
    for (Entry<String, Integer> entry : perUserMaxIdle.entrySet()) {
      dataSource.setPerUserMaxIdle(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Sets the per user max wait.
   *
   * @param perUserMaxWait
   *          the per user max wait
   */
  @com.google.inject.Inject(optional = true)
  public void setPerUserMaxWait(@PerUserMaxWait Map<String, Integer> perUserMaxWait) {
    for (Entry<String, Integer> entry : perUserMaxWait.entrySet()) {
      dataSource.setPerUserMaxWaitMillis(entry.getKey(), (long) entry.getValue());
    }
  }

  @Override
  public DataSource get() {
    return dataSource;
  }

}
