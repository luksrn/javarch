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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.service.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.github.javarch.persistence.exception.DatabaseConfigurationException;
import com.github.javarch.support.spring.Profiles;

/**
 * Classe resposável por carregar as propriedades do Hibernate através do arquivo 
 * hibernate.properties. presente no classpath da aplicação.
 * 
 * 
 * @author Lucas Oliveira <i>luksrn@gmail.com</i>
 *
 */
@Configuration
@PropertySource("classpath:hibernate.properties")
public class HibernatePropertiesConfig  {
	
	@Autowired	
	private Environment env;
	
	/**
	 * Injetado opcionalmente, apenas se o perfil {@link Profiles#MULT_TENANT} estiver ativo.
	 */
	@Autowired(required=false)
	private CurrentTenantIdentifierResolver currentIdentifierResolver;
	

	/**
	 * Injetado opcionalmente, apenas se o perfil {@link Profiles#MULT_TENANT} estiver ativo.
	 */
	@Autowired(required=false)
	private MultiTenantConnectionProvider multiTenantConnectionProvider;
	
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * Obtém o nome dos pacotes onde contém entidades persistentes do hibernate
	 * anotadas com <i>@Entity</i>. Caso o profile envers esteja ativo o pacote
	 * com.github.javarch.persistence.orm.envers será adicionado.
	 * 
	 * @return Pacotes que serão scaneados pelo {@link LocalSessionFactoryBean} durante a 
	 * criação do {@link SessionFactory} do hibernate.
	 * 
	 * @see HibernateConfig#sessionFactory()
	 */
	public String [] packagesToScan(){
		List<String> pacotes = new ArrayList<String>();
		try { 
			String property = env.getRequiredProperty("hibernate.packscan");				
			String pacotesSplit [] = property.split(";");
			for (String pacote : pacotesSplit) {
				pacotes.add( pacote );		
			}
			if ( log.isDebugEnabled() && pacotes.isEmpty() ){
				log.debug("Nenhum pacote de entidades persistentes foi mapeado no arquivo hibernate.properties. Para registrar suas entidades persistentes use a propriedade hibernate.packscan para definir os pacotes.");
			}
			if ( env.acceptsProfiles("envers") ) {
				pacotes.add("com.github.javarch.persistence.orm.envers");
			}			
		} catch (IllegalStateException e) {
			log.error("Não foi possível encontrar a propriedade 'hibernate.packscan'. Verifique se a propriedade está definida no arquivo hibernate.properties",e);			
		}
		String [] returnPacks = new String [ pacotes.size() ];
		return pacotes.toArray( returnPacks );
	}

	/**
	 * Retorna um objeto Properties com todas as configurações de propriedades que devem
	 * ser passadas ao {@link SessionFactory}. As propriedades são definidas no arquivo
	 * hibernate.properties que deve estar presente no raiz do classpath.
	 * 
	 * @return Properties do Hibernate.
	 */
	@Bean 	
	public Properties hibernateProperties() {
		
		Properties props = new Properties();		
		
		props.put( AvailableSettings.DIALECT , env.getRequiredProperty( AvailableSettings.DIALECT ) );	
		
		if( env.acceptsProfiles( Profiles.TEST )){
			props.put( AvailableSettings.HBM2DDL_AUTO, env.getProperty(  AvailableSettings.HBM2DDL_AUTO , "update" )  );
		}
		
		if ( env.acceptsProfiles( Profiles.MULT_TENANT )){
			if ( log.isDebugEnabled() ){
				log.debug("Profile Multi Tenancy ativado! Realizando configuração...");
			}
			if ( currentIdentifierResolver == null || env.acceptsProfiles(Profiles.PRODUCTION )){
				throw new DatabaseConfigurationException("Não foi encontrado nenhum objeto CurrentIdentifierResolver.");
			}			
			props.put( AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER , currentIdentifierResolver );
			props.put( AvailableSettings.DATASOURCE , env.getRequiredProperty( AvailableSettings.DATASOURCE ));
			props.put( AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER , multiTenantConnectionProvider);
			props.put( AvailableSettings.MULTI_TENANT , env.getRequiredProperty( AvailableSettings.MULTI_TENANT ) );
		}  else {			
			
			props.put( AvailableSettings.USE_SECOND_LEVEL_CACHE, true);
			props.put( AvailableSettings.USE_QUERY_CACHE, true	); 
			props.put( AvailableSettings.CACHE_REGION_FACTORY , "org.hibernate.cache.ehcache.EhCacheRegionFactory" ); 
		}
		
	    if ( env.acceptsProfiles( Profiles.ENVERS ) ){
			props.put( "org.hibernate.envers.versionsTableSuffix","_audit");
			props.put( "org.hibernate.envers.revisionFieldName","revision");    	
			props.put( "org.hibernate.envers.default_schema", "audit"); 
		}
								
		return props;
	} 	
}
