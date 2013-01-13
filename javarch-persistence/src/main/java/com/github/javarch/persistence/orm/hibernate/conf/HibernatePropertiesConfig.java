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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

/**
 * Classe resposável por carregar as propriedades do Hibernate
 * através do arquivo hibernate.properties. presente no classpath da
 * aplicação.
 * 
 * Caso o profile <b>envers</b> esteja ativdo as propriedades do envers
 * serão atribuidas.
 * 
 * @author Lucas Oliveira <i>luksrn@gmail.com</i>
 *
 */
@Configuration
@PropertySource("classpath:hibernate.properties")
public class HibernatePropertiesConfig  {
	
	@Autowired
	private Environment env;
	
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
				pacotes.add(pacote);		
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
	 * 
	 * @return
	 */
	@Bean 
	public Properties hibernateProperties() {
		Properties  props = new Properties();
		
		props.put( "hibernate.dialect", env.getRequiredProperty("hibernate.dialect") );
		props.put( "hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto","update")  );
		props.put( "hibernate.show_sql", env.getProperty("hibernate.show_sql",Boolean.class , false ));	
		props.put( "hibernate.format_sql", env.getProperty("hibernate.format_sql", Boolean.class, false));
		if ( env.acceptsProfiles("envers") ){
			props.put("org.hibernate.envers.versionsTableSuffix","_audit");
			props.put("org.hibernate.envers.revisionFieldName","revision");    	
			props.put("org.hibernate.envers.default_schema", "audit"); 
		}
		
		return props;
	} 	
}
