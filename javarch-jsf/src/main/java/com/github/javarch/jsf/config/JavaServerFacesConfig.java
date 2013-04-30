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

package com.github.javarch.jsf.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


/**
 * Configurações comuns da aplicação.
 * 
 * @author Lucas Oliveira
 *
 */
@Configuration
@ComponentScan( 
		basePackages = {"com.github.javarch.jsf"}
		, excludeFilters= { 
			@ComponentScan.Filter( Configuration.class ) 
		} )
@ImportResource({"classpath*:/applicationContext-support.xml"})
public class JavaServerFacesConfig {
		
	/**
	 * 
	 * Registra escopos personalizáveis para aplicação. Esta configuração possui
	 * a equivalente em XML como:
	 * 
	 * <pre>
	 * {@code
	 *	<bean class="org.springframework.beans.factory.config.CustomScopeConfigurer">
	 *       <property name="scopes">
	 *           <map>
	 *               <entry key="view">
	 *                   <bean class="com.github.luksrn.webapp.core.spring.support.ViewScope"/>
	 *               </entry>
	 *               <entry key="thread">
	 *                   <bean class="com.github.luksrn.webapp.core.spring.support.ThreadScope"/>
	 *               </entry>
	 *           </map>
	 *       </property>
	 *   </bean>
	 *   }	
	 * </pre>
	 * 
	 * 
	 * @return
	 */
	@Bean
	public CustomScopeConfigurer escoposPersonalizaveis(){
		CustomScopeConfigurer custom = new CustomScopeConfigurer();
		
		Map<String, Object> escopos = new HashMap<String, Object>();		
		escopos.put("view",  new ViewScope() );			
		custom.setScopes(escopos);
		
		return custom;		
	}
}
