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


package com.github.javarch.persistence.orm.test;

import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.StringUtils;

/**
 * .
 * 
 * @author Lucas Oliveira
 *
 */
public abstract class DataBaseTestBuilder<T> {

	private LinkedList<Resource> resourcesSql = new LinkedList<Resource>();

	private LinkedList<Resource> destroySql = new LinkedList<Resource>();
	
	protected T source;

	protected String SQL_COMMENT_PREFIX = "--";
	
	public DataBaseTestBuilder(T source) {
		this.source = source;
	}

	public DataBaseTestBuilder<T> populeData(String script) {
		resourcesSql.add(new ClassPathResource(script));
		return this;
	}
	
	public DataBaseTestBuilder<T> destroyData(String script) {
		destroySql.add(new ClassPathResource(script));
		return this;
	}

	private String readNextSqlScript(Resource rs) throws IOException {
		EncodedResource encoded = new EncodedResource( rs );
		LineNumberReader lnr = new LineNumberReader(encoded.getReader());
		String currentStatement = lnr.readLine();
		StringBuilder scriptBuilder = new StringBuilder();
		while (currentStatement != null) {
			if (StringUtils.hasText(currentStatement) && (SQL_COMMENT_PREFIX != null && !currentStatement.startsWith(SQL_COMMENT_PREFIX))) {
				if (scriptBuilder.length() > 0) {
					scriptBuilder.append('\n');
				}
				scriptBuilder.append(currentStatement);
			}
			currentStatement = lnr.readLine();
		}
		return scriptBuilder.toString();
	}

	public T destroyDatabase() {		
		return runFor(destroySql);
	}
	
	public T populeDatabase() {		
		return runFor(resourcesSql);
	}
	
	private T runFor(LinkedList<Resource> scripts){

		try{
			Iterator<Resource> rs = scripts.iterator();
			while (rs.hasNext()) {
				Resource resource = (Resource) rs.next();
				String sql = readNextSqlScript(resource);
				executeSQL( sql );
			}
			return source;
		}catch (IOException e) {
			return source;
		}catch (SQLException e) {
			return source;
		}
	}

	public abstract void executeSQL(final String sql) throws SQLException ;
	
}
