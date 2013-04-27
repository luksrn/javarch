package com.github.javarch.jsf;

import javax.inject.Named;

import com.github.javarch.persistence.Persistable;

@Named
public abstract class AbstractCreateManagedBean <T extends Persistable<?>> extends AbstractFormManagedBean<T>{
 
	private static final long serialVersionUID = 241528714229926461L;
	
	@Override
	public void onAction(T entidade) {
		log.debug("Exectando default action");
		repository.saveOrUpdate( entidade );
	}	 
}
