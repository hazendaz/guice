/**
 *    Copyright 2009-2016 the original author or authors.
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
package org.mybatis.guice;

import java.io.StringReader;
import java.sql.Timestamp;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class CleanDatabaseRule implements TestRule {
    private final SqlSession sqlSession;
    private final Contact contact;
    private final Contact contactWithAddress;
    private final AddressConverter addressConverter;
    
    @Inject
    private CleanDatabaseRule(SqlSession sqlSession, Contact contact, @Named("contactWithAddress") Contact contactWithAddress, AddressConverter addressConverter) {
        this.sqlSession = sqlSession;
        this.contact = contact;
        this.contactWithAddress = contactWithAddress;
        this.addressConverter = addressConverter;
    }
    
    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                ScriptRunner runner = new ScriptRunner(sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection());
                try {
                    runner.setAutoCommit(true);
                    runner.setStopOnError(true);
                    runner.runScript(new StringReader("DELETE FROM contact; "
                            + "INSERT INTO contact (id, first_name, last_name, created) "
                            + "VALUES (1, '" + contact.getFirstName() + "', '" + contact.getLastName() + "', '" + new Timestamp(contactWithAddress.getCreated().getValue()) + "'); "
                            + "INSERT INTO contact (id, first_name, last_name, created, address) "
                            + "VALUES (2, '" + contactWithAddress.getFirstName() + "', '" + contactWithAddress.getLastName() + "', '" + new Timestamp(contactWithAddress.getCreated().getValue()) + "', '" + addressConverter.convert(contactWithAddress.getAddress()) + "'); "
                    ));
                    contact.setId(1);
                    contactWithAddress.setId(2);
                } finally {
                    runner.closeConnection();
                }
                base.evaluate();
            }
        };
    }
}
