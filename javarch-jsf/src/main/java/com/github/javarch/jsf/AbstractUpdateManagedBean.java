package com.github.javarch.jsf;

import com.github.javarch.persistence.Persistable;
import com.github.javarch.persistence.Repository;
import com.github.javarch.support.log.Logger;
import com.github.javarch.support.log.LoggerFactory;
import com.google.common.base.Optional;

public abstract class AbstractUpdateManagedBean<T extends Persistable<?>> extends AbstractFormManagedBean<T>{
 
	private static final long serialVersionUID = 7222941430765228865L;
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractUpdateManagedBean.class);
	
	private Repository<T> repository;

	/**
	 * Carrega o uma entidade "Entidade" baseada id. Utilizado inicialmente no <f:event> após 
	 * capturar o id no f:viewParam
	 * @return
	 */
	@SuppressWarnings("all")
	public String loadAfterSetViewParamId(){
		
		if ( ! entidade.isNew() ){
			T entityLoaded = (T)repository.findOne( getGenericType(), entidade.getId() );
			if ( !Optional.of(entityLoaded).isPresent() ) {
				handleEntityNotFound();
			}
			if ( isValidUseEntity( entityLoaded ) ){
				setEntidade( entityLoaded );
				return "";
			}
			throw new IllegalRequestAttributeException();
		}
	 	
	 	return "";
	}
	
	protected boolean isValidUseEntity(T entityLoaded) {
		return false;
	}

	protected void handleEntityNotFound(){
		LOG.error("Entity {} not found in databse.", entidade );
	}

	@Override
	public void onAction(T entidade) {		
		repository.saveOrUpdate(entidade);
	}
}
