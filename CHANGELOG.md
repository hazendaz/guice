Mybatis GUICE Changelog
-----------------------

Releases are now tracked via github releases.  This file carries over prior changes.xml style as no longer supported by maven and does not work on modern jdks.  Going forwards look at github release notes.

## 4.0.0 maintenance release ##

- Updates Guice to version 7 and use Jakarta namespace.
    - Fixes #576
    - Fixes #574
- Replaces "jdbc:mysql" URL with "jdbc:mariadb" for MariaDB in JdbcHelper
    - Fixes #589

## 3.17 maintenance release ##

- Add use failover mode and load balancing mode of MariaDB connector/j.
    - Fixes #374

## 3.12 maintenance release ##

- Adds support for HikariCP.
    - Fixes #129
- Replaces Apache Commons DBCP version 1 by Apache Commons DBCP version 2. Some properties are replaced.
        BasicDataSourceProvider:
        maxActive -&gt; maxTotal;
        maxWait -&gt; maxWaitMillis;
        DriverAdapterCPDSProvider:
        maxIdle removed;
        PerUserPoolDataSource:
        minEvictableIdleTimeMillis -&gt; defaultMinEvictableIdleTimeMillis;
        numTestsPerEvictionRun -&gt; defaultNumTestsPerEvictionRun;
        testOnBorrow -&gt; defaultTestOnBorrow;
        testOnReturn -&gt; defaultTestOnReturn;
        testWhileIdle -&gt; defaultTestWhileIdle;
        timeBetweenEvictionRunsMillis -&gt; defaultTimeBetweenEvictionRunsMillis;
        maxActive -&gt; defaultMaxTotal;
        maxIdle -&gt; defaultMaxIdle;
        maxWait -&gt; defaultMaxWaitMillis;
        @PerUserMaxActive -> @PerUserMaxTotal;
        @PerUserMaxWait -> @PerUserMaxWaitMillis;
        SharedPoolDataSourceProvider:
        minEvictableIdleTimeMillis -&gt; defaultMinEvictableIdleTimeMillis;
        numTestsPerEvictionRun -&gt; defaultNumTestsPerEvictionRun;
        testOnBorrow -&gt; defaultTestOnBorrow;
        testOnReturn -&gt; defaultTestOnReturn;
        testWhileIdle -&gt; defaultTestWhileIdle;
        timeBetweenEvictionRunsMillis -&gt; defaultTimeBetweenEvictionRunsMillis;
        maxActive -&gt; defaultMaxTotal;
        maxIdle -&gt; defaultMaxIdle;
        maxWait -&gt; defaultMaxWaitMillis;
    - Fixes #137
    - Fixes #138
- Removed support for BoneCP.
    - Fixes #139

## 3.11 maintenance release ##

- Allow TxTransactionalMethodInterceptor to commit the transaction on some exceptions.
    - Fixes #129
- Fixes use of Configuration provider.
    - Fixes #118
- Fixes OSGI imports.
    - Fixes #126

## 3.10 maintenance release ##

- Allow Druid connection properties to be set using an instance of Properties.
    - Fixes #112
- Make JdbcUrlAntFormatter class public to allow users to format their custom URLs like in JdbcHelper.
- Fixes following configuration properties for BoneCP data source.
    - idleConnectionTestPeriod
    - idleConnectionTestPeriodInMinutes
    - idleConnectionTestPeriodInSeconds
    - Fixes #111
- Fixes ProvisionException when setting login timeout for BasicDataSource
    - Fixes #110
- Fixes use of data source name configuration property for SharedPoolDataSourceProvider.
    - Fixes #115
- Fixes generic types in PerUserPoolDataSourceModule.
    - Fixes #114
- Fixes readOnly property for Druid data source.
    - Fixes #116
- Removed dependency on guice multibindings.

## 3.9 maintenance release ##

- Restored setting configuration using properties. Only properties that existed in version 3.7 will be supported in the future.
- Fixes constructor injection for TypeHandlers.
- Added support for Druid data source.

## 3.8 maintenance release ##

- Default Environment ID for XML configuration changed from "development" to the value of "default" attribute of &lt;environments&gt; element.
- Added support for Druid data source.
- Configuration can be customized using ConfigurationSetting interface.

## 3.7.1 maintenance release ##

- Fixes concurrency error with JTA.

## 3.7 maintenance release ##

- Default scripting language can be used.
- Generic type handlers can be used.
- Constructor injection will not work on type handlers due to support for generic type handlers.
- Use field or setter injection instead.
- Injection will work for ObjectFactory and ObjectWrapperFactory when using XML configuration.

## 3.4 maintenance release ##

- Alias scanner will now respect the @Alias annotation of MyBatis.
- Code cleanup, optimized the imports and some java-doc fixes.
- Updated some internal dependencies.
    - log4j 1.2.16 -> 1.2.17
    - hsqldb 2.2.4 -> 2.2.9

## 3.3 maintenance release ##

- Added resources/JDBC custom ClassLoader support.
- Missing mapUnderscoreToCamelCase property in mybatis-guice. Due to Google Code 422
- Inject dependencies in TypeHandlers defined in the XML
- Made org.mybatis.guice.datasource.c3p0.C3p0DataSourceProvider username/password optional (users can specify them in the JDBC URL).
- Made org.mybatis.guice.datasource.dbcp.BasicDataSourceProvider username/password optional (users can specify them in the JDBC URL).
- Made org.mybatis.guice.datasource.dbcp.DriverAdapterCPDSProvider username/password optional (users can specify them in the JDBC URL).
- Made org.mybatis.guice.datasource.builtin.PooledDataSourceProvider username/password optional (users can specify them in the JDBC URL).
- Made org.mybatis.guice.datasource.builtin.UnpooledDataSourceProvider username/password optional (users can specify them in the JDBC URL).
- Internal code polishing.

## 3.2 maintenance release ##

- Remove useless dependencies in the final artifact.

## 3.1 maintenance release ##

- Simplified @Transactional behavior, deprecated @Transactional#autoCommit()

## 3.0 maintenance release ##

- (XML)MyBatisModule now works as proper Guice module
- Fixed Provider&lt;DatSource&gt; type, must be JSR330'ed, thanks to Marzia Forli

## 1.1.0 maintenance release ##

- Remove mapper order restriction when referencing SQL fragment in another file.  Fixes Google Code 179
- Fixed org.mybatis.guice.configuration.ConfigurationProvider injections point, added missing setters.
- Guice dependency upgrated to 3.0.
- Modules are JSR330'ed.
- Added BoneCP in the supported datasource providers.
- Added H2DB in the supported JDBC Helpers.
- @org.mybatis.guice.transactional.Transactional#exceptionMessage() expressed using java.util.Formatter pattern and no more with java.text.MessageFormat#format().

## 1.0.0 maintenance release ##

- Added missing SqlSession -> SqlSessionManager binding, so users can request managed SqlSession injections.
- Hidden internal APIs

## 1.0.0-RC4 maintenance release ##

- Finalized XML MyBatis configuration support.
- Documented XML MyBatis configuration support.
- Removed the over engineered 'transactionfactory' package.
- Google Guice extension dependencies moved to 'compile' scope.
- MyBatis dependency upgraded to 3.0.3

## 1.0.0-RC3 maintenance release ##

- Started developing the XML prototypal support (undocumented yet).
- Avoid ConfigurationProviderInjectionListener.afterInjection() randomically called twice.
- Added the @org.mybatis.guice.transactional.Transactional.rollbackOnly parameter.
- Added the org.mybatis.guice.MyBatisModule#addSimpleAliases(Collection&lt;Class&lt;?&gt;&gt;).
- Added the org.mybatis.guice.MyBatisModule#addInterceptorsClasses(Collection&lt;Class&lt;? extends Interceptor&gt;&gt;).
- Added the org.mybatis.guice.MyBatisModule#addMapperClasses(Collection&lt;Class&lt;?&gt;&gt;).

## 1.0.0-RC2 maintenance release ##

- ErrorContext leaks in ThreadLocal fixes Google Code 104
- Added the JdbcHelper to simplify the JDBC Drivers configuration.

## 1.0.0-RC1 maintenance release ##

- Imported and moved the original iBaGuice codebase.
