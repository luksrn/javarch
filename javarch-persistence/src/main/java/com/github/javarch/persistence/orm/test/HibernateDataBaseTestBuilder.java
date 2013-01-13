package com.github.javarch.persistence.orm.test;

/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


/**
 * DataBase Builder para classes que utilizam o Hibernate.
 * 
 * @author Lucas Oliveira
 *
 */
public class HibernateDataBaseTestBuilder extends DataBaseTestBuilder<SessionFactory>{

	public HibernateDataBaseTestBuilder(SessionFactory source) {
		super(source);
	}

	@Override
	public void executeSQL(final String sql) throws SQLException {
		Session s = source.getCurrentSession();
		s.createSQLQuery(sql).executeUpdate();	
	}
}
