package com.github.javarch.persistence.orm.hibernate.spi.multitenant;

import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Work in progress
 * 
 * @author Lucas Farias de Oliveira
 *
 */
//@Component
class MultiTenantSessionFactory {

	@Autowired
	private SessionFactory sf;
	
	public Session getSession( final String tenantIdentifier ){
		SessionBuilder sessionBuilder = sf.withOptions();
		sessionBuilder.tenantIdentifier(tenantIdentifier);
		return sessionBuilder.openSession();
	}
}
