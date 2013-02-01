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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.github.javarch.support.spring.Profiles;


/**
 * Classe de configuração do Hibernate 4.0.0.Final.
 * 
 * @author Lucas Oliveira <i>luksrn@gmail.com</i>
 * 
 * @see DataSourceConfig#dataSource()
 */
@Configuration
@EnableTransactionManagement  
@ComponentScan(basePackages={"com.github.javarch.persistence.orm.hibernate"})
public class HibernateConfig {	
	
	/**
	 * Ambiente no qual a aplicação está executando.
	 */
	@Autowired
	private Environment env;
	
	/**
	 * Propriedades do Hibernate.
	 */
	@Autowired
	private HibernatePropertiesConfig hibernatePropertiesConfig;
	
	/**
	 * DataSource
	 */
	@Autowired
	private DataSourceConfig dataSourceConfig;
	
	/**
	 * Gerenciador de transações.
	 * 
	 * @return Uma instância de {@link HibernateTransactionManager}.
	 */
	@Bean
	@DependsOn("sessionFactory")
	public PlatformTransactionManager transactionManager(){
		
		HibernateTransactionManager transactionManager =new HibernateTransactionManager(sessionFactory().getObject() ); 		
		if ( env.acceptsProfiles( Profiles.MULT_TENANT ) ){
			transactionManager.setAutodetectDataSource(false);
		} 
		return transactionManager;
		
	}
	
	/**
	 * Produz um objeto SessionFactory. Durante a produção do objeto é registrada
	 * as entidades persistentes que serão gerenciadas pelo Hibernate. 
	 * 
	 * @return {@link LocalSessionFactoryBean}
	 * 
	 * @see HibernatePropertiesConfig#packagesToScan()
	 * @see HibernatePropertiesConfig#hibernateProperties()
	 */
	@Bean	
	public LocalSessionFactoryBean sessionFactory(){
		
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();		
		sessionFactory.setPackagesToScan( hibernatePropertiesConfig.packagesToScan());		
		sessionFactory.setHibernateProperties( hibernatePropertiesConfig.hibernateProperties() );
		
		if ( ! env.acceptsProfiles( Profiles.MULT_TENANT ) ){
			sessionFactory.setDataSource( dataSourceConfig.dataSource() );
		}
		// Analisar viabilidade de second level cache em multitenancy.
		
		return sessionFactory;
	}
	
	/**
	 * Para validações javax.validation (JSR-303)
	 * 
	 * @return
	 */
	@Bean
	public LocalValidatorFactoryBean validator(){
		return new LocalValidatorFactoryBean();
	}
	 

}
