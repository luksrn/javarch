package com.github.javarch.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.javarch.domain.User;
import com.github.javarch.persistence.orm.hibernate.HibernateRepository;
import com.github.javarch.persistence.orm.hibernate.conf.DataSourceH2Config;
import com.github.javarch.persistence.orm.hibernate.conf.HibernateConfig;
import com.github.javarch.persistence.orm.hibernate.conf.HibernatePropertiesConfig;
import com.github.javarch.support.spring.Profiles;


/**
 * 
 * 
 * @author Lucas Oliveira
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes={HibernateConfig.class,
				DataSourceH2Config.class,
				HibernatePropertiesConfig.class},
		loader=AnnotationConfigContextLoader.class)
@ImportResource({"classpath*:/applicationContext-datasource-jndi.xml"})
@TransactionConfiguration(defaultRollback=false)
@Transactional 
@ActiveProfiles({ Profiles.TEST } )
public class PreInsertEventListenerTest {

	@Autowired
	private HibernateRepository<User> defaultRepository;	
	
	
	@Test
	public void testInsert(){
		defaultRepository.setClazz(User.class);
		
		User usuario = new User("Lucas Farias de Oliveira", "123456","luksrn@gmail.com");

		defaultRepository.save(usuario);
		assertNotNull(usuario);
		assertNotNull( usuario.getDateCreated() );
		assertNull(usuario.getLastUpdated());
	}
}
