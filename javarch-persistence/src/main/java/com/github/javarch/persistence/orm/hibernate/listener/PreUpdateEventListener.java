package com.github.javarch.persistence.orm.hibernate.listener;

import org.hibernate.event.spi.PreUpdateEvent;

import com.github.javarch.persistence.annotation.LastUpdate;

public class PreUpdateEventListener implements org.hibernate.event.spi.PreUpdateEventListener {
 
	private static final long serialVersionUID = 6360911415858792281L;

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		EntityDateRegister.setCurrentDateOnFieldWithAnnotation(event.getEntity(), LastUpdate.class);			 
		return false;
	}	 
}
