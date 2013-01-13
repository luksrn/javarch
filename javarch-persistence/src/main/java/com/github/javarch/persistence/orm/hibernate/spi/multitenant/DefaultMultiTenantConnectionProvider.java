package com.github.javarch.persistence.orm.hibernate.spi.multitenant;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.service.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

/**
 * Work in progress
 * 
 * @author Lucas Farias de Oliveira
 *
 */
//https://hibernate.onjira.com/secure/attachment/17045/DatabaseBasedMultiTenancyTest.java
class DefaultMultiTenantConnectionProvider  extends AbstractMultiTenantConnectionProvider {

	private Map<String, DataSource> dsMap;

	@Override
	protected ConnectionProvider getAnyConnectionProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
		// TODO Auto-generated method stub
		return null;
	}
	 

}
