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

import com.github.javarch.support.spring.Profiles;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
 * @author Lucas Oliveira
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
	@Bean(destroyMethod="close")
	public DataSource dataSource() {
		try {
			ComboPooledDataSource dataSource = new ComboPooledDataSource();

			dataSource.setDriverClass( env.getRequiredProperty("jdbc.driver") );
			dataSource.setJdbcUrl( env.getRequiredProperty("jdbc.url") );
			dataSource.setUser( env.getRequiredProperty("jdbc.usuario") );
			dataSource.setPassword( env.getRequiredProperty("jdbc.senha") );
			// poll size
			dataSource.setInitialPoolSize( 5 );
			dataSource.setMinPoolSize( 10 );
			dataSource.setMaxPoolSize( 25 );
			dataSource.setAcquireIncrement(3);
			dataSource.setMaxStatements(0);
			// retries
			dataSource.setAcquireRetryAttempts(30);
			dataSource.setAcquireRetryDelay( 1000 ); // 1s
			dataSource.setBreakAfterAcquireFailure(false);		
			// Refresh connections
			dataSource.setMaxIdleTime(180); // 3min		
			dataSource.setMaxConnectionAge( 10 );
			// timeout testing
			dataSource.setCheckoutTimeout(5000); //5s
			dataSource.setIdleConnectionTestPeriod(60);
			dataSource.setTestConnectionOnCheckout(true);
			dataSource.setPreferredTestQuery("SELECT 1+1");
					
			return dataSource;
		}catch(Exception e){			
			e.printStackTrace();
			return null;
		}
				
	}
}
