package com.github.javarch.persistence.orm.hibernate.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
public class JdbcConfig {
	/**
	 * DataSourceConf Configuraççao utilizada na construção do SessionFactory
	 * que irá fornecer um DataSource para o SessionFactory.
	 */
	@Autowired
	private DataSourceConfig dataSourceConf;
	
	@Bean
	public JdbcTemplate jdbcTemplate (){
		return new JdbcTemplate(dataSourceConf.dataSource());
	}
}
