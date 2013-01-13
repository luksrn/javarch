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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;



@Configuration
@Profile("test")
public class H2DataSourceConfig implements DataSourceConfig {	 
		
	private static final Logger LOG = LoggerFactory.getLogger(H2DataSourceConfig.class);
	
	
	@Bean(destroyMethod="shutdown")
	public  DataSource dataSource() { 		
		LOG.debug("Iniciando construção do EmbeddedDatabase H2");
		EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();		
		factory.setDatabaseType(EmbeddedDatabaseType.H2);
		
		Resource rs = new ClassPathResource("install.sql");
		if ( rs.exists() ){
			LOG.debug("Arquivo install.sql detectado no classpath.");
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator();		
			populator.addScript( rs );
			factory.setDatabasePopulator(populator);
		}
		
		return factory.getDatabase();
	}

}
