package com.github.javarch.repository;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.javarch.persistence.orm.hibernate.conf.HibernateConfig;
import com.github.javarch.persistence.orm.hibernate.conf.HibernatePropertiesConfig;
import com.github.javarch.support.spring.Profiles;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		loader=AnnotationConfigContextLoader.class) 
@ActiveProfiles({ Profiles.TEST } )
public class BaseDataBaseIntegrationTest {
	

	private static final Log logger = LogFactory.getLog(BaseDataBaseIntegrationTest.class);

	@BeforeClass
	public static void beforeClass(){
		
		logger.debug("Executando @BeforeClass");
		try{
			SimpleNamingContextBuilder ctx = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
						
			EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();		
			factory.setDatabaseType(EmbeddedDatabaseType.H2);
			DataSource ds = factory.getDatabase();			
			
			//Context ctx= new InitialContext();
			ctx.bind("java:comp/env/jdbc/jnark", ds);			
		}catch(NamingException e){
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void afterClass(){
		logger.debug("Executando @AfterClass");
		SimpleNamingContextBuilder.getCurrentContextBuilder().deactivate();		
	}
	
	@Test
	public void testObterConexaoDoJndi() throws NamingException {
		Context ctx= new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/jnark");
		Assert.assertNotNull(ds);		
	}
}
