package com.github.javarch.jsf.mbeans;

import com.github.javarch.persistence.Persistable;
import com.google.common.base.Preconditions;

public class AbstractDeleteManagedBean <T extends Persistable<?>> extends AbstractFormManagedBean<T>{
 
	private static final long serialVersionUID = 2583673165183668129L;

	@Override
	public void onAction(T entidade) {
		Preconditions.checkState( entidade.getId() != null );
		repository.delete( entidade.getId() );		
	}

}
