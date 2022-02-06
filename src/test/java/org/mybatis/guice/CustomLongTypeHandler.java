/*
 *    Copyright 2009-2022 the original author or authors.
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
package org.mybatis.guice;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(CustomType.class)
public class CustomLongTypeHandler extends BaseTypeHandler<CustomType> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, CustomType parameter, JdbcType jdbcType)
      throws SQLException {
    if (jdbcType == JdbcType.TIMESTAMP) {
      ps.setTimestamp(i, new Timestamp(parameter.getValue()));
    }
  }

  @Override
  public CustomType getNullableResult(ResultSet rs, String columnName) throws SQLException {
    Object value = rs.getObject(columnName);
    if (value instanceof Timestamp) {
      CustomType t = new CustomType();
      t.setValue(((Timestamp) value).getTime());
      return t;
    }
    return null;
  }

  @Override
  public CustomType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    Object value = cs.getObject(columnIndex);
    if (value instanceof Timestamp) {
      CustomType t = new CustomType();
      t.setValue(((Timestamp) value).getTime());
      return t;
    }
    return null;
  }

  @Override
  public CustomType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    Object value = rs.getObject(columnIndex);
    if (value instanceof Timestamp) {
      CustomType t = new CustomType();
      t.setValue(((Timestamp) value).getTime());
      return t;
    }
    return null;
  }
}
