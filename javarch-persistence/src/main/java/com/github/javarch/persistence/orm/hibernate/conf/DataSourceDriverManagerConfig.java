/*
* Copyright 2011 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not
* use this file except in compliance with the License. You may obtain a copy of
* the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations under
* the License.
*/

package com.github.javarch.persistence.orm.hibernate.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.github.javarch.support.spring.Profiles;

/**
 * 
 * @author Lucas Oliveira
 * @deprecated - Use JNDI no lugar!
 */
@Configuration
@PropertySource("classpath:datasource.properties")
@Profile( { Profiles.DEVELOPMENT })
public class DataSourceDriverManagerConfig implements DataSourceConfig {

	@Autowired
	private Environment env;
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
	
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName( env.getRequiredProperty("jdbc.driver") );
		dataSource.setUrl( env.getRequiredProperty("jdbc.url") );
		dataSource.setUsername( env.getRequiredProperty("jdbc.usuario") );
		dataSource.setPassword( env.getRequiredProperty("jdbc.senha") );
		
		return dataSource;		
	}
}
