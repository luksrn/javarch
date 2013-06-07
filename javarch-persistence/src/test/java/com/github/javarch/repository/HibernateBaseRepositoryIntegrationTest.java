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

package com.github.javarch.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.junit.After;
import org.junit.Before;
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
import com.github.javarch.persistence.NamedQueryParameter;
import com.github.javarch.persistence.PageRequest;
import com.github.javarch.persistence.orm.hibernate.HibernateRepository;
import com.github.javarch.persistence.orm.hibernate.conf.DataSourceH2Config;
import com.github.javarch.persistence.orm.hibernate.conf.HibernateConfig;
import com.github.javarch.persistence.orm.hibernate.conf.HibernatePropertiesConfig;
import com.github.javarch.persistence.orm.test.DataBaseTestBuilder;
import com.github.javarch.persistence.orm.test.HibernateDataBaseTestBuilder;
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
@TransactionConfiguration(defaultRollback=false)
@Transactional 
@ActiveProfiles({ Profiles.TEST , "migration" } )
public class HibernateBaseRepositoryIntegrationTest  { 

	@Autowired
	private HibernateRepository defaultRepository;	
	
	@Autowired
	private SessionFactory sf;
	
	private static boolean databaseSetup;
	
	
	@Before	
	public void execute() throws SQLException, IOException {
		
		DataBaseTestBuilder<SessionFactory> dbbuilder = new HibernateDataBaseTestBuilder(sf)
				.populeData("/sqls/HibernateBaseRepository.sql")
				.destroyData("/sqls/HibernateBaseRepository-destroy.sql");
		if ( ! databaseSetup ){
			dbbuilder.populeDatabase();		
			databaseSetup = true;
		}
	}
	
	//Avoid false positives when testing ORM code
	@After
	public void after(){
		sf.getCurrentSession().flush();
	}
	
	@Test
	public void testInjecao(){
		assertNotNull(defaultRepository);
	} 			
	
	@Test
	public void testLoad(){ 
	 
		List<User> users = defaultRepository.findAll(User.class); 
		assertNotNull(users);
		assertTrue("Esperava 50 mas encontrou " + users.size(), users.size() == 50  );
	} 
	

	@Test
	public void testLoadNamedQuerySemParametros(){
 
	 
		List<User> users = defaultRepository.findAllByNamedQuery("User.findAll", null);
		assertNotNull(users);
		assertTrue(users.size() == 50 );
	}
	

	@Test
	public void testLoadNamedQueryComParametros(){
 
		
		
		NamedQueryParameter params = NamedQueryParameter.with("email", "user43@gmail.com");
		User user = defaultRepository.findOneByNamedQuery("User.findByEmail", params );
		assertNotNull(user); 
		assertTrue(user.getNomeUsuario().equals("Nome Usuário 43" ));
	} 
	
	@Test
	public void testLoadPaginado(){
	
		List<User> users = defaultRepository.findAll(User.class, new PageRequest(20, 10));
		assertNotNull(users);		
		assertTrue(users.size() == 10 );
		assertTrue(users.get(0).getEmail().equals("user20@gmail.com"));
		assertTrue(users.get(9).getEmail().equals("user29@gmail.com"));
	} 	

	
	@Test
	public void testLoadProjecao(){
		 
		 Projection p = Projections.projectionList()
				 	.add( Projections.property("email"), "email" );

		List<User> users = defaultRepository.findAll(User.class, p);
		assertNotNull(users);
		
		for (User User : users) {
			assertNotNull(User.getEmail());
			assertNull(User.getNomeUsuario());
			assertNull(User.getSenha());
			assertTrue(User.getBlogs().isEmpty());
		}
	} 	

}
