package com.github.javarch.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.javarch.domain.User;
import com.github.javarch.persistence.orm.hibernate.HibernateRepository;
import com.github.javarch.persistence.orm.hibernate.conf.H2DataSourceConfig;
import com.github.javarch.persistence.orm.hibernate.conf.HibernateConfig;
import com.github.javarch.persistence.orm.hibernate.conf.HibernatePropertiesConfig;


/**
 * 
 * 
 * @author Lucas Oliveira
 *
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes={HibernateConfig.class,
				H2DataSourceConfig.class,
				HibernatePropertiesConfig.class},
		loader=AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback=false)
@Transactional 
@ActiveProfiles({"test"})
public class PreUpdateEventListenerTest {

	@Autowired
	private HibernateRepository<User> defaultRepository;	
	

	@Autowired
	private SessionFactory sf;
	
	@Test
	public void testUpdate(){
		defaultRepository.setClazz(User.class);
		
		User usuario = new User("Lucas Farias de Oliveira", "123456","luksrn@gmail.com");

		defaultRepository.save(usuario);
 	 
		sf.getCurrentSession().flush();
		
		assertNull( "Após o insert, lastupdate não foi null + "     + usuario.getLastUpdated() , usuario.getLastUpdated());		
		
		usuario.setEmail("email_alterado@gmail.com");
		defaultRepository.save(usuario);
		
		sf.getCurrentSession().flush();
		 
		assertNotNull("Após o update, lastupdate foi null", usuario.getLastUpdated());
		
	}
}
