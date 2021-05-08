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

import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AutoMappingBehaviorConfigurationSettingTest {
  @Mock
  private Configuration configuration;

  @Test
  public void applyConfigurationSetting_Full() {
    AutoMappingBehaviorConfigurationSetting setting = new AutoMappingBehaviorConfigurationSetting(
        AutoMappingBehavior.FULL);
    setting.applyConfigurationSetting(configuration);
    verify(configuration).setAutoMappingBehavior(AutoMappingBehavior.FULL);
  }

  @Test
  public void applyConfigurationSetting_None() {
    AutoMappingBehaviorConfigurationSetting setting = new AutoMappingBehaviorConfigurationSetting(
        AutoMappingBehavior.NONE);
    setting.applyConfigurationSetting(configuration);
    verify(configuration).setAutoMappingBehavior(AutoMappingBehavior.NONE);
  }

  @Test
  public void applyConfigurationSetting_Partial() {
    AutoMappingBehaviorConfigurationSetting setting = new AutoMappingBehaviorConfigurationSetting(
        AutoMappingBehavior.PARTIAL);
    setting.applyConfigurationSetting(configuration);
    verify(configuration).setAutoMappingBehavior(AutoMappingBehavior.PARTIAL);
  }
}
