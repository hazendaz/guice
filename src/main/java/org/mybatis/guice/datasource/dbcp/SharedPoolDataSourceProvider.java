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

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;

/**
 * Provides the Apache commons-dbcp2 {@code SharedPoolDataSource}.
 */
public final class SharedPoolDataSourceProvider implements Provider<DataSource> {

  private final SharedPoolDataSource dataSource = new SharedPoolDataSource();

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

  /**
   * Sets the max total.
   *
   * @param maxTotal
   *          the new max total
   */
  @com.google.inject.Inject(optional = true)
  public void setMaxTotal(@Named("DBCP.maxTotal") final int maxTotal) {
    dataSource.setMaxTotal(maxTotal);
  }

  /**
   * Sets the max idle.
   *
   * @param maxIdle
   *          the new max idle
   */
  @com.google.inject.Inject(optional = true)
  public void setMaxIdle(@Named("DBCP.maxIdle") final int maxIdle) {
    dataSource.setDefaultMaxIdle(maxIdle);
  }

  /**
   * Sets the max wait.
   *
   * @param maxWait
   *          the new max wait
   */
  @com.google.inject.Inject(optional = true)
  public void setMaxWait(@Named("DBCP.maxWait") final int maxWait) {
    dataSource.setDefaultMaxWaitMillis(maxWait);
  }

  @Override
  public DataSource get() {
    return dataSource;
  }

}
