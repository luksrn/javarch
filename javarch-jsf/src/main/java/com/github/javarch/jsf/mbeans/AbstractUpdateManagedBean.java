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
package com.github.javarch.jsf.mbeans;

import com.github.javarch.persistence.Persistable;
import com.github.javarch.support.log.Logger;
import com.github.javarch.support.log.LoggerFactory;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public abstract class AbstractUpdateManagedBean<T extends Persistable<?>> extends AbstractFormManagedBean<T>{
 
	private static final long serialVersionUID = 7222941430765228865L;
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractUpdateManagedBean.class);
	 
	/**
	 * Carrega o uma entidade "Entidade" baseada id. Utilizado inicialmente no <f:event> após 
	 * capturar o id no f:viewParam
	 * @return
	 */
	@SuppressWarnings("all")
	public String loadAfterSetViewParamId(){
		LOG.debug("calling loadAfterSetViewParamId on object {}", entity );
		if ( ! entity.isNew() ){
			
			T entityLoaded = (T)repository.findOne( getGenericType(), entity.getId() );
			
			if ( !Optional.of(entityLoaded).isPresent() ) {
				LOG.debug("Entity with {} not found in database. Invoking handleEntityNotFound()", entity );
				handleEntityNotFound();
			}
			
			LOG.debug("Entity {} found. Validating entity with isValidUseEntity()", entityLoaded);
			
			if ( !isValidUseEntity( entityLoaded ) ){
				handleInvalidEntity( entityLoaded );				
			}
			
			setEntity( entityLoaded );
		}
	 	
	 	return "";
	}
	
	protected void handleEntityNotFound(){
		LOG.error("Default impl of handleEntityNotFound. No action will be performed." );
	}
	
	protected boolean isValidUseEntity(T entityLoaded) {
		return true;
	}

	private void handleInvalidEntity(T entityLoaded) {
		LOG.error("Default impl of handleInvalidEntity. No action will be performed..", entity );
	}

	@Override
	public void onAction(T entidade) {		
		Preconditions.checkState( entidade.getId() != null );
		repository.saveOrUpdate(entidade);
	}
}
