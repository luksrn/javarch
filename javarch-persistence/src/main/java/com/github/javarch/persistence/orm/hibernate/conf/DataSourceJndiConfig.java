package com.github.javarch.persistence.orm.hibernate.conf;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.github.javarch.support.spring.Profiles;

@Configuration
@Profile( Profiles.PRODUCTION )
public class DataSourceJndiConfig implements DataSourceConfig {

	@Autowired
	private Environment env;
	
	@Override
	public DataSource dataSource() {
		try{
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup( env.getRequiredProperty("jndi.url") );
			return ds;
		}catch(NamingException e){
			
			return null;
		}		
	}
}
