/*
 *    Copyright 2009-2024 the original author or authors.
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
package org.mybatis.guice.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.ProvisionException;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.guice.configuration.settings.AliasConfigurationSetting;
import org.mybatis.guice.configuration.settings.ConfigurationSetting;
import org.mybatis.guice.configuration.settings.InterceptorConfigurationSettingProvider;
import org.mybatis.guice.configuration.settings.JavaTypeAndHandlerConfigurationSettingProvider;
import org.mybatis.guice.configuration.settings.MapperConfigurationSetting;
import org.mybatis.guice.configuration.settings.TypeHandlerConfigurationSettingProvider;

@ExtendWith(MockitoExtension.class)
class ConfigurationProviderTest {
  private ConfigurationProvider configurationProvider;
  @Mock
  private TransactionFactory transactionFactory;
  @Mock
  private DataSource dataSource;
  @Mock
  private Interceptor interceptor;
  @Mock
  private TypeHandler<Alias> aliasTypeHandler;
  @Mock
  private DatabaseIdProvider databaseIdProvider;
  private Injector injector;
  private Environment environment;

  @BeforeEach
  void beforeTest() {
    environment = new Environment("test", transactionFactory, dataSource);
    configurationProvider = new ConfigurationProvider(environment);
  }

  @Test
  void get() {
    injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(Environment.class).toInstance(environment);
        bind(DataSource.class).toInstance(dataSource);
        bind(Configuration.class).toProvider(configurationProvider);
      }
    });

    Configuration configuration = injector.getInstance(Configuration.class);

    configuration.getMappedStatementNames(); // Test that configuration is valid.
    assertEquals(environment, configuration.getEnvironment());
    assertNull(configuration.getTypeAliasRegistry().getTypeAliases().get("alias"));
    assertFalse(configuration.getTypeHandlerRegistry().hasTypeHandler(Alias.class));
    assertFalse(configuration.getTypeHandlerRegistry().hasTypeHandler(Human.class));
    assertEquals(0, configuration.getMapperRegistry().getMappers().size());
    assertEquals(0, configuration.getInterceptors().size());
    assertFalse(configuration.isLazyLoadingEnabled());
    assertTrue(configuration.isAggressiveLazyLoading());
    assertFalse(configuration.isUseGeneratedKeys());
    assertTrue(configuration.isUseColumnLabel());
    assertTrue(configuration.isCacheEnabled());
    assertEquals(ExecutorType.SIMPLE, configuration.getDefaultExecutorType());
    assertEquals(AutoMappingBehavior.PARTIAL, configuration.getAutoMappingBehavior());
    assertFalse(configuration.isCallSettersOnNulls());
    assertNull(configuration.getDefaultStatementTimeout());
    assertFalse(configuration.isMapUnderscoreToCamelCase());
  }

  @Test
  void get_Optionals() throws Throwable {
    String databaseId = "test_database_id";
    final Integer defaultFetchSize = 200;
    final Integer defaultStatementTimeout = 2000;
    when(databaseIdProvider.getDatabaseId(dataSource)).thenReturn(databaseId);
    final Key<TypeHandler<Alias>> aliasTypeHandlerKey = Key.get(new TypeLiteral<TypeHandler<Alias>>() {
    });
    injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(Environment.class).toInstance(environment);
        bind(DataSource.class).toInstance(dataSource);
        bindConstant().annotatedWith(Names.named("mybatis.configuration.lazyLoadingEnabled")).to(true);
        bindConstant().annotatedWith(Names.named("mybatis.configuration.aggressiveLazyLoading")).to(false);
        bindConstant().annotatedWith(Names.named("mybatis.configuration.useGeneratedKeys")).to(true);
        bindConstant().annotatedWith(Names.named("mybatis.configuration.useColumnLabel")).to(false);
        bindConstant().annotatedWith(Names.named("mybatis.configuration.cacheEnabled")).to(false);
        bindConstant().annotatedWith(Names.named("mybatis.configuration.defaultExecutorType")).to(ExecutorType.REUSE);
        bindConstant().annotatedWith(Names.named("mybatis.configuration.autoMappingBehavior"))
            .to(AutoMappingBehavior.FULL);
        bindConstant().annotatedWith(Names.named("mybatis.configuration.callSettersOnNulls")).to(true);
        bindConstant().annotatedWith(Names.named("mybatis.configuration.defaultStatementTimeout"))
            .to(defaultStatementTimeout);
        bindConstant().annotatedWith(Names.named("mybatis.configuration.mapUnderscoreToCamelCase")).to(true);
        bind(DatabaseIdProvider.class).toInstance(databaseIdProvider);
        bind(Configuration.class).toProvider(configurationProvider);
        bind(aliasTypeHandlerKey).toInstance(aliasTypeHandler);
        bind(Interceptor.class).toInstance(interceptor);
      }
    });
    configurationProvider.addConfigurationSetting(new AliasConfigurationSetting("alias", Alias.class));
    JavaTypeAndHandlerConfigurationSettingProvider aliasTypeHandlerSetting = JavaTypeAndHandlerConfigurationSettingProvider
        .create(Alias.class, aliasTypeHandlerKey);
    injector.injectMembers(aliasTypeHandlerSetting);
    configurationProvider.addConfigurationSetting(aliasTypeHandlerSetting.get());
    TypeHandlerConfigurationSettingProvider humanTypeHandlerSetting = new TypeHandlerConfigurationSettingProvider(
        Key.get(HumanTypeHandler.class));
    injector.injectMembers(humanTypeHandlerSetting);
    configurationProvider.addConfigurationSetting(humanTypeHandlerSetting.get());
    configurationProvider.addMapperConfigurationSetting(new MapperConfigurationSetting(TestMapper.class));
    InterceptorConfigurationSettingProvider interceptorSetting = new InterceptorConfigurationSettingProvider(
        Interceptor.class);
    injector.injectMembers(interceptorSetting);
    configurationProvider.addConfigurationSetting(interceptorSetting.get());
    configurationProvider.addConfigurationSetting((new ConfigurationSetting() {
      @Override
      public void applyConfigurationSetting(Configuration configuration) {
        configuration.setDefaultFetchSize(defaultFetchSize);
      }
    }));

    Configuration configuration = injector.getInstance(Configuration.class);

    configuration.getMappedStatementNames(); // Test that configuration is valid.
    assertEquals(environment, configuration.getEnvironment());
    assertEquals(Alias.class, configuration.getTypeAliasRegistry().getTypeAliases().get("alias"));
    assertTrue(configuration.getTypeHandlerRegistry().hasTypeHandler(Alias.class));
    assertTrue(configuration.getTypeHandlerRegistry().hasTypeHandler(Human.class));
    assertEquals(1, configuration.getMapperRegistry().getMappers().size());
    assertTrue(configuration.getMapperRegistry().getMappers().contains(TestMapper.class));
    assertEquals(1, configuration.getInterceptors().size());
    assertTrue(configuration.getInterceptors().contains(interceptor));
    verify(databaseIdProvider).getDatabaseId(dataSource);
    assertEquals(databaseId, configuration.getDatabaseId());
    assertTrue(configuration.isLazyLoadingEnabled());
    assertFalse(configuration.isAggressiveLazyLoading());
    assertTrue(configuration.isUseGeneratedKeys());
    assertFalse(configuration.isUseColumnLabel());
    assertFalse(configuration.isCacheEnabled());
    assertEquals(ExecutorType.REUSE, configuration.getDefaultExecutorType());
    assertEquals(AutoMappingBehavior.FULL, configuration.getAutoMappingBehavior());
    assertTrue(configuration.isCallSettersOnNulls());
    assertEquals(defaultStatementTimeout, configuration.getDefaultStatementTimeout());
    assertTrue(configuration.isMapUnderscoreToCamelCase());
    assertEquals(defaultFetchSize, configuration.getDefaultFetchSize());
  }

  @Test
  void get_ConfigurationSettingOverwritesNamed() throws Throwable {
    injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(Environment.class).toInstance(environment);
        bind(DataSource.class).toInstance(dataSource);
        bind(Configuration.class).toProvider(configurationProvider);
        bindConstant().annotatedWith(Names.named("mybatis.configuration.lazyLoadingEnabled")).to(true);
      }
    });
    configurationProvider.addConfigurationSetting(new ConfigurationSetting() {
      @Override
      public void applyConfigurationSetting(Configuration configuration) {
        configuration.setLazyLoadingEnabled(false);
      }
    });

    Configuration configuration = injector.getInstance(Configuration.class);

    configuration.getMappedStatementNames(); // Test that configuration is valid.
    assertFalse(configuration.isLazyLoadingEnabled());
  }

  @Test
  void get_FailFast_True() throws Throwable {
    injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(Environment.class).toInstance(environment);
        bind(DataSource.class).toInstance(dataSource);
        bind(Configuration.class).toProvider(configurationProvider);
        bindConstant().annotatedWith(Names.named("mybatis.configuration.failFast")).to(true);
      }
    });

    configurationProvider.addMapperConfigurationSetting(new MapperConfigurationSetting(ErrorMapper.class));
    assertThrows(ProvisionException.class, () -> {
      injector.getInstance(Configuration.class);
    });
  }

  @Test
  void get_FailFast_False() throws Throwable {
    injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(Environment.class).toInstance(environment);
        bind(DataSource.class).toInstance(dataSource);
        bind(Configuration.class).toProvider(configurationProvider);
        bindConstant().annotatedWith(Names.named("mybatis.configuration.failFast")).to(false);
      }
    });

    configurationProvider.addMapperConfigurationSetting(new MapperConfigurationSetting(ErrorMapper.class));
    injector.getInstance(Configuration.class);

    // Success.
  }

  private static class Alias {
  }

  public static interface TestMapper {
  }

  public static class Human {
  }

  public static class HumanTypeHandler extends BaseTypeHandler<Human> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Human parameter, JdbcType jdbcType)
        throws SQLException {
    }

    @Override
    public Human getNullableResult(ResultSet rs, String columnName) throws SQLException {
      return null;
    }

    @Override
    public Human getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
      return null;
    }

    @Override
    public Human getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
      return null;
    }
  }
}
