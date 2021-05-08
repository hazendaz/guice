/*
 *    Copyright 2009-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.guice.configuration.settings;

import static org.mockito.Mockito.verify;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.LocalCacheScope;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocalCacheScopeConfigurationSettingTest {
  @Mock
  private Configuration configuration;

  @Test
  public void applyConfigurationSetting_Session() {
    LocalCacheScopeConfigurationSetting setting = new LocalCacheScopeConfigurationSetting(LocalCacheScope.SESSION);
    setting.applyConfigurationSetting(configuration);
    verify(configuration).setLocalCacheScope(LocalCacheScope.SESSION);
  }

  @Test
  public void applyConfigurationSetting_Statement() {
    LocalCacheScopeConfigurationSetting setting = new LocalCacheScopeConfigurationSetting(LocalCacheScope.STATEMENT);
    setting.applyConfigurationSetting(configuration);
    verify(configuration).setLocalCacheScope(LocalCacheScope.STATEMENT);
  }
}
