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

package com.github.javarch.persistence.orm.hibernate.envers;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @author Lucas Oliveira
 *
 */
public class CustomRevisionEntityInforListener implements RevisionListener {

	public void newRevision(Object revisionEntity) {
		CustomRevisionEntityInfo revision = (CustomRevisionEntityInfo) revisionEntity;
 
        Object princial = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( princial instanceof UserDetails ){
        	revision.setUsername(((UserDetails)princial).getUsername());
        }else{
        	revision.setUsername( princial.toString() ); // anonimo.
        }
		
	}

}
