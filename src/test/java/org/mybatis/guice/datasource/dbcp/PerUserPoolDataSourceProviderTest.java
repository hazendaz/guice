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

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.ConnectionPoolDataSource;

import org.apache.commons.dbcp2.datasources.PerUserPoolDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PerUserPoolDataSourceProviderTest {
  @Mock
  private ConnectionPoolDataSource connectionPoolDataSource;

  @Test
  void get() throws Throwable {
    final boolean autoCommit = true;
    final int loginTimeout = 10;
    final boolean defaultReadOnly = true;
    final int defaultTransactionIsolation = Connection.TRANSACTION_READ_COMMITTED;
    final String description = "test_description";
    final int minEvictableIdleTimeMillis = 20;
    final int numTestsPerEvictionRun = 30;
    final boolean rollbackAfterValidation = true;
    final boolean testOnBorrow = true;
    final boolean testOnReturn = true;
    final boolean testWhileIdle = true;
    final int timeBetweenEvictionRunsMillis = 40;
    final String validationQuery = "SELECT 1";
    final int maxTotal = 50;
    final int maxIdle = 60;
    final int maxWait = 70;
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(ConnectionPoolDataSource.class).toInstance(connectionPoolDataSource);
        bindConstant().annotatedWith(Names.named("JDBC.autoCommit")).to(autoCommit);
        bindConstant().annotatedWith(Names.named("JDBC.loginTimeout")).to(loginTimeout);
        bindConstant().annotatedWith(Names.named("DBCP.defaultReadOnly")).to(defaultReadOnly);
        bindConstant().annotatedWith(Names.named("DBCP.defaultTransactionIsolation")).to(defaultTransactionIsolation);
        bindConstant().annotatedWith(Names.named("DBCP.description")).to(description);
        bindConstant().annotatedWith(Names.named("DBCP.minEvictableIdleTimeMillis")).to(minEvictableIdleTimeMillis);
        bindConstant().annotatedWith(Names.named("DBCP.numTestsPerEvictionRun")).to(numTestsPerEvictionRun);
        bindConstant().annotatedWith(Names.named("DBCP.rollbackAfterValidation")).to(rollbackAfterValidation);
        bindConstant().annotatedWith(Names.named("DBCP.testOnBorrow")).to(testOnBorrow);
        bindConstant().annotatedWith(Names.named("DBCP.testOnReturn")).to(testOnReturn);
        bindConstant().annotatedWith(Names.named("DBCP.testWhileIdle")).to(testWhileIdle);
        bindConstant().annotatedWith(Names.named("DBCP.timeBetweenEvictionRunsMillis"))
            .to(timeBetweenEvictionRunsMillis);
        bindConstant().annotatedWith(Names.named("DBCP.validationQuery")).to(validationQuery);
        bindConstant().annotatedWith(Names.named("DBCP.maxTotal")).to(maxTotal);
        bindConstant().annotatedWith(Names.named("DBCP.maxIdle")).to(maxIdle);
        bindConstant().annotatedWith(Names.named("DBCP.maxWait")).to(maxWait);
      }
    });
    PerUserPoolDataSourceProvider provider = injector.getInstance(PerUserPoolDataSourceProvider.class);

    PerUserPoolDataSource dataSource = (PerUserPoolDataSource) provider.get();

    assertEquals(connectionPoolDataSource, dataSource.getConnectionPoolDataSource());
    assertEquals(autoCommit, dataSource.isDefaultAutoCommit());
    assertEquals(loginTimeout, dataSource.getLoginTimeoutDuration().getSeconds());
    assertEquals(defaultReadOnly, dataSource.isDefaultReadOnly());
    assertEquals(defaultTransactionIsolation, dataSource.getDefaultTransactionIsolation());
    assertEquals(description, dataSource.getDescription());
    assertEquals(minEvictableIdleTimeMillis, dataSource.getDefaultMinEvictableIdleDuration().toMillis());
    assertEquals(numTestsPerEvictionRun, dataSource.getDefaultNumTestsPerEvictionRun());
    assertEquals(rollbackAfterValidation, dataSource.isRollbackAfterValidation());
    assertEquals(testOnBorrow, dataSource.getDefaultTestOnBorrow());
    assertEquals(testOnReturn, dataSource.getDefaultTestOnReturn());
    assertEquals(testWhileIdle, dataSource.getDefaultTestWhileIdle());
    assertEquals(timeBetweenEvictionRunsMillis, dataSource.getDefaultDurationBetweenEvictionRuns().toMillis());
    assertEquals(validationQuery, dataSource.getValidationQuery());
    assertEquals(maxTotal, dataSource.getDefaultMaxTotal());
    assertEquals(maxIdle, dataSource.getDefaultMaxIdle());
    assertEquals(maxWait, dataSource.getDefaultMaxWait().toMillis());
  }

  @Test
  void get_OtherValues() throws Throwable {
    final boolean autoCommit = false;
    final int loginTimeout = 11;
    final boolean defaultReadOnly = false;
    final int defaultTransactionIsolation = Connection.TRANSACTION_SERIALIZABLE;
    final String description = "test_description2";
    final int minEvictableIdleTimeMillis = 21;
    final int numTestsPerEvictionRun = 31;
    final boolean rollbackAfterValidation = false;
    final boolean testOnBorrow = false;
    final boolean testOnReturn = false;
    final boolean testWhileIdle = false;
    final int timeBetweenEvictionRunsMillis = 41;
    final String validationQuery = "SELECT 2";
    final int maxTotal = 51;
    final int maxIdle = 61;
    final int maxWait = 71;
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(ConnectionPoolDataSource.class).toInstance(connectionPoolDataSource);
        bindConstant().annotatedWith(Names.named("JDBC.autoCommit")).to(autoCommit);
        bindConstant().annotatedWith(Names.named("JDBC.loginTimeout")).to(loginTimeout);
        bindConstant().annotatedWith(Names.named("DBCP.defaultReadOnly")).to(defaultReadOnly);
        bindConstant().annotatedWith(Names.named("DBCP.defaultTransactionIsolation")).to(defaultTransactionIsolation);
        bindConstant().annotatedWith(Names.named("DBCP.description")).to(description);
        bindConstant().annotatedWith(Names.named("DBCP.minEvictableIdleTimeMillis")).to(minEvictableIdleTimeMillis);
        bindConstant().annotatedWith(Names.named("DBCP.numTestsPerEvictionRun")).to(numTestsPerEvictionRun);
        bindConstant().annotatedWith(Names.named("DBCP.rollbackAfterValidation")).to(rollbackAfterValidation);
        bindConstant().annotatedWith(Names.named("DBCP.testOnBorrow")).to(testOnBorrow);
        bindConstant().annotatedWith(Names.named("DBCP.testOnReturn")).to(testOnReturn);
        bindConstant().annotatedWith(Names.named("DBCP.testWhileIdle")).to(testWhileIdle);
        bindConstant().annotatedWith(Names.named("DBCP.timeBetweenEvictionRunsMillis"))
            .to(timeBetweenEvictionRunsMillis);
        bindConstant().annotatedWith(Names.named("DBCP.validationQuery")).to(validationQuery);
        bindConstant().annotatedWith(Names.named("DBCP.maxTotal")).to(maxTotal);
        bindConstant().annotatedWith(Names.named("DBCP.maxIdle")).to(maxIdle);
        bindConstant().annotatedWith(Names.named("DBCP.maxWait")).to(maxWait);
      }
    });
    PerUserPoolDataSourceProvider provider = injector.getInstance(PerUserPoolDataSourceProvider.class);

    PerUserPoolDataSource dataSource = (PerUserPoolDataSource) provider.get();

    assertEquals(connectionPoolDataSource, dataSource.getConnectionPoolDataSource());
    assertEquals(autoCommit, dataSource.isDefaultAutoCommit());
    assertEquals(loginTimeout, dataSource.getLoginTimeoutDuration().getSeconds());
    assertEquals(defaultReadOnly, dataSource.isDefaultReadOnly());
    assertEquals(defaultTransactionIsolation, dataSource.getDefaultTransactionIsolation());
    assertEquals(description, dataSource.getDescription());
    assertEquals(minEvictableIdleTimeMillis, dataSource.getDefaultMinEvictableIdleDuration().toMillis());
    assertEquals(numTestsPerEvictionRun, dataSource.getDefaultNumTestsPerEvictionRun());
    assertEquals(rollbackAfterValidation, dataSource.isRollbackAfterValidation());
    assertEquals(testOnBorrow, dataSource.getDefaultTestOnBorrow());
    assertEquals(testOnReturn, dataSource.getDefaultTestOnReturn());
    assertEquals(testWhileIdle, dataSource.getDefaultTestWhileIdle());
    assertEquals(timeBetweenEvictionRunsMillis, dataSource.getDefaultDurationBetweenEvictionRuns().toMillis());
    assertEquals(validationQuery, dataSource.getValidationQuery());
    assertEquals(maxTotal, dataSource.getDefaultMaxTotal());
    assertEquals(maxIdle, dataSource.getDefaultMaxIdle());
    assertEquals(maxWait, dataSource.getDefaultMaxWait().toMillis());
  }

  @Test
  void get_Jndi() throws Throwable {
    final String jndiKey = "test_key";
    final String jndiValue = "test_value";
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(ConnectionPoolDataSource.class).toInstance(connectionPoolDataSource);
        bindConstant().annotatedWith(Names.named("DBCP.jndi.key")).to(jndiKey);
        bindConstant().annotatedWith(Names.named("DBCP.jndi.value")).to(jndiValue);
      }
    });
    PerUserPoolDataSourceProvider provider = injector.getInstance(PerUserPoolDataSourceProvider.class);

    PerUserPoolDataSource dataSource = (PerUserPoolDataSource) provider.get();

    assertEquals(connectionPoolDataSource, dataSource.getConnectionPoolDataSource());
    assertEquals(jndiValue, dataSource.getJndiEnvironment(jndiKey));
  }

  @Test
  void get_DataSourceName() throws Throwable {
    final String name = "test_name";
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bindConstant().annotatedWith(Names.named("DBCP.name")).to(name);
      }
    });
    PerUserPoolDataSourceProvider provider = injector.getInstance(PerUserPoolDataSourceProvider.class);

    PerUserPoolDataSource dataSource = (PerUserPoolDataSource) provider.get();

    assertEquals(name, dataSource.getDataSourceName());
  }

  @Test
  void get_PerUserDefaultAutoCommit() throws Throwable {
    final Map<String, Boolean> defaultAutoCommit = new HashMap<String, Boolean>();
    defaultAutoCommit.put("test_user", true);
    defaultAutoCommit.put("test_user2", false);
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(new TypeLiteral<Map<String, Boolean>>() {
        }).annotatedWith(PerUserDefaultAutoCommit.class).toInstance(defaultAutoCommit);
      }
    });
    PerUserPoolDataSourceProvider provider = injector.getInstance(PerUserPoolDataSourceProvider.class);

    PerUserPoolDataSource dataSource = (PerUserPoolDataSource) provider.get();

    assertEquals(true, dataSource.getPerUserDefaultAutoCommit("test_user"));
    assertEquals(false, dataSource.getPerUserDefaultAutoCommit("test_user2"));
  }

  @Test
  void get_PerUserDefaultReadOnly() throws Throwable {
    final Map<String, Boolean> defaultReadOnly = new HashMap<String, Boolean>();
    defaultReadOnly.put("test_user", false);
    defaultReadOnly.put("test_user2", true);
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(new TypeLiteral<Map<String, Boolean>>() {
        }).annotatedWith(PerUserDefaultReadOnly.class).toInstance(defaultReadOnly);
      }
    });
    PerUserPoolDataSourceProvider provider = injector.getInstance(PerUserPoolDataSourceProvider.class);

    PerUserPoolDataSource dataSource = (PerUserPoolDataSource) provider.get();

    assertEquals(false, dataSource.getPerUserDefaultReadOnly("test_user"));
    assertEquals(true, dataSource.getPerUserDefaultReadOnly("test_user2"));
  }

  @Test
  void get_PerUserDefaultTransactionIsolation() throws Throwable {
    final Map<String, Integer> defaultTransactionIsolation = new HashMap<String, Integer>();
    defaultTransactionIsolation.put("test_user", Connection.TRANSACTION_READ_COMMITTED);
    defaultTransactionIsolation.put("test_user2", Connection.TRANSACTION_SERIALIZABLE);
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(new TypeLiteral<Map<String, Integer>>() {
        }).annotatedWith(PerUserDefaultTransactionIsolation.class).toInstance(defaultTransactionIsolation);
      }
    });
    PerUserPoolDataSourceProvider provider = injector.getInstance(PerUserPoolDataSourceProvider.class);

    PerUserPoolDataSource dataSource = (PerUserPoolDataSource) provider.get();

    assertEquals((Integer) Connection.TRANSACTION_READ_COMMITTED,
        dataSource.getPerUserDefaultTransactionIsolation("test_user"));
    assertEquals((Integer) Connection.TRANSACTION_SERIALIZABLE,
        dataSource.getPerUserDefaultTransactionIsolation("test_user2"));
  }

  @Test
  void get_PerUserMaxTotal() throws Throwable {
    final Map<String, Integer> maxTotal = new HashMap<String, Integer>();
    maxTotal.put("test_user", 10);
    maxTotal.put("test_user2", 20);
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(new TypeLiteral<Map<String, Integer>>() {
        }).annotatedWith(PerUserMaxTotal.class).toInstance(maxTotal);
      }
    });
    PerUserPoolDataSourceProvider provider = injector.getInstance(PerUserPoolDataSourceProvider.class);

    PerUserPoolDataSource dataSource = (PerUserPoolDataSource) provider.get();

    assertEquals(10, dataSource.getPerUserMaxTotal("test_user"));
    assertEquals(20, dataSource.getPerUserMaxTotal("test_user2"));
  }

  @Test
  void get_PerUserMaxIdle() throws Throwable {
    final Map<String, Integer> maxIdle = new HashMap<String, Integer>();
    maxIdle.put("test_user", 30);
    maxIdle.put("test_user2", 40);
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(new TypeLiteral<Map<String, Integer>>() {
        }).annotatedWith(PerUserMaxIdle.class).toInstance(maxIdle);
      }
    });
    PerUserPoolDataSourceProvider provider = injector.getInstance(PerUserPoolDataSourceProvider.class);

    PerUserPoolDataSource dataSource = (PerUserPoolDataSource) provider.get();

    assertEquals(30, dataSource.getPerUserMaxIdle("test_user"));
    assertEquals(40, dataSource.getPerUserMaxIdle("test_user2"));
  }

  @Test
  void get_PerUserMaxWait() throws Throwable {
    final Map<String, Integer> maxWait = new HashMap<String, Integer>();
    maxWait.put("test_user", 50);
    maxWait.put("test_user2", 60);
    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(new TypeLiteral<Map<String, Integer>>() {
        }).annotatedWith(PerUserMaxWait.class).toInstance(maxWait);
      }
    });
    PerUserPoolDataSourceProvider provider = injector.getInstance(PerUserPoolDataSourceProvider.class);

    PerUserPoolDataSource dataSource = (PerUserPoolDataSource) provider.get();

    assertEquals(50, dataSource.getPerUserMaxWaitDuration("test_user").toMillis());
    assertEquals(60, dataSource.getPerUserMaxWaitDuration("test_user2").toMillis());
  }
}
