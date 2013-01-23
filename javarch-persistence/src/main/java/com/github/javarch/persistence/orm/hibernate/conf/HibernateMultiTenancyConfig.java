package com.github.javarch.persistence.orm.hibernate.conf;

import org.hibernate.service.jdbc.connections.spi.DataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.service.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.github.javarch.persistence.orm.hibernate.spi.multitenant.CurrentTenantIdentifierFinder;
import com.github.javarch.support.spring.Profiles;

/**
 * 
 * @author lucas
 *
 */
@Configuration
@Profile( Profiles.MULT_TENANT )
public class HibernateMultiTenancyConfig {
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public MultiTenantConnectionProvider dataSourceBasedMultiTenantConnectionProvider(){
		return new DataSourceBasedMultiTenantConnectionProviderImpl();				
	}
	
	/**
	 * 
	 * @return
	 */
	@Bean 
	public CurrentTenantIdentifierFinder currentTenantIdentifierFinder(){
		return new CurrentTenantIdentifierFinder();
	}
}
