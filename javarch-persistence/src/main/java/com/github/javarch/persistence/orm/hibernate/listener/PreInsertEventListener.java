package com.github.javarch.persistence.orm.hibernate.listener;

import org.hibernate.event.spi.PreInsertEvent;

import com.github.javarch.persistence.annotation.DateCreated;

public class PreInsertEventListener implements org.hibernate.event.spi.PreInsertEventListener {

	private static final long serialVersionUID = 2939264537058810391L;

	@Override
	public boolean onPreInsert(PreInsertEvent event) {	  
		EntityDateRegister.setCurrentDateOnFieldWithAnnotation(event.getEntity(), DateCreated.class);	  
		return false;
	}

}
