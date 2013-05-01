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
