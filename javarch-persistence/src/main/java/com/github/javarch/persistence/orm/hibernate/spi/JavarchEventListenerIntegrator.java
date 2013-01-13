package com.github.javarch.persistence.orm.hibernate.spi;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javarch.persistence.orm.hibernate.listener.PreInsertEventListener;
import com.github.javarch.persistence.orm.hibernate.listener.PreUpdateEventListener;

/**
 * EventListener integrator. 
 * 
 * Integra  os seguintes eventos:
 * 
 * EventType.PRE_INSERT 
 * EventType.PRE_UPDATE
 * 
 * @author Lucas Oliveira
 *
 */
public class JavarchEventListenerIntegrator implements Integrator {

	private static final Logger LOG = LoggerFactory.getLogger(JavarchEventListenerIntegrator.class);
	
	@Override
	public void integrate(Configuration configuration,SessionFactoryImplementor sessionFactory,SessionFactoryServiceRegistry serviceRegistry) {
		
		LOG.info("Adicionando eventos customizados ao EventListenerRegistry.");
		EventListenerRegistry registry = serviceRegistry.getService(EventListenerRegistry.class);
	    registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener( new PreInsertEventListener() );
	    registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener( new PreUpdateEventListener() );
		
	}

	@Override
	public void integrate(MetadataImplementor metadata, SessionFactoryImplementor sessionFactory,SessionFactoryServiceRegistry serviceRegistry) {
		
	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory,SessionFactoryServiceRegistry serviceRegistry) {
				
	}

}
