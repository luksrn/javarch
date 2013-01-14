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
