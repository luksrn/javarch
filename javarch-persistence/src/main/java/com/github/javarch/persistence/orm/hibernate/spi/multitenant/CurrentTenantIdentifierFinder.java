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
package com.github.javarch.persistence.orm.hibernate.spi.multitenant;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import com.github.javarch.persistence.HibernateEnviroment;
import com.github.javarch.persistence.exception.CurrentTenantIdentifierResolverException;
import com.github.javarch.support.spring.Profiles;

/**
 * Classe responsavel por buscar o {@link org.hibernate.context.spi.CurrentTenantIdentifierResolver} no
 * pacote informado atraves do atributo {@link HibernateEnviroment}.CURRENT_TENANT_IDENTIFIER_RESOLVER_PACKAGE. 
 * Caso não informado, busca através do root package.
 * 
 * @author Lucas
 *
 */
@Component
@Profile(Profiles.MULT_TENANT)
public class CurrentTenantIdentifierFinder implements BeanDefinitionRegistryPostProcessor {
		
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrentTenantIdentifierFinder.class);
	
	@Autowired
	private Environment env;		
		
	@Override
	public void postProcessBeanFactory( ConfigurableListableBeanFactory beanFactory) throws BeansException {	}

	@Override
	public void postProcessBeanDefinitionRegistry( BeanDefinitionRegistry registry) throws BeansException {
		
		String connectionProvidersPackage = env.getProperty( HibernateEnviroment.CURRENT_TENANT_IDENTIFIER_RESOLVER_PACKAGE , "");		
		LOGGER.debug("Buscando @CurrentTenantIdentifierResolver no pacote {}", connectionProvidersPackage );

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter( new AnnotationTypeFilter(CurrentTenantIdentifierResolver.class) );		
		
		Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents( connectionProvidersPackage );		
		if ( beanDefinitions.isEmpty() ){
			Set<BeanDefinition> defaultBeanDefinition = scanner.findCandidateComponents( "com.github.javarch.persistence.orm.hibernate.spi.multitenant" );
			registrarBeanDefinitions(registry, defaultBeanDefinition);
		}else {
			registrarBeanDefinitions(registry, beanDefinitions);
		}
		
	}

	private void registrarBeanDefinitions(BeanDefinitionRegistry registry, Set<BeanDefinition> beanDefinitions) {
		if ( beanDefinitions.isEmpty() || beanDefinitions.size() > 1 ){
			throw new CurrentTenantIdentifierResolverException();
		}
		
		for (BeanDefinition bd : beanDefinitions ){
			LOGGER.debug("Encontrado CurrentTenantIdentifierResolver: {}" ,bd.getBeanClassName());
			if ( !registry.containsBeanDefinition( bd.getBeanClassName() ) ){												
				String beanName = bd.getBeanClassName();				
				registry.registerBeanDefinition( beanName , bd);
			}
		}		
	}
	
}
