package com.github.javarch.persistence.orm.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import com.github.javarch.persistence.Persistable;

public class DefaultHibernateRepositoryTest {

	private HibernateRepository instance;
	
	// mocks
	private SessionFactory sf;
	private Session session;
	
	@Before
	public void setUp() {
		sf = mock(SessionFactory.class);
		session = mock(Session.class);
		when( sf.getCurrentSession() ).thenReturn(session);
		
		instance = new DefaultHibernateRepository( sf ); 
	}
	
	@Test
	public void deveSalvarQuandoObjetoNaoForPersistente(){
		
		Persistable<Integer> persistable = mock(Persistable.class);
		
		when(persistable.isNew()).thenReturn(true);
		
		instance.saveOrUpdate( persistable );
		
		verify( persistable , times(1) ).isNew();
		verify( sf , times(1) ).getCurrentSession();
		verify( session , times(1) ).save( any(Persistable.class) );	
		verify( session , never() ).update( any(Persistable.class) );
	}
	
	@Test
	public void deveAtualizarQuandoObjetoForPersistente(){
		
		Persistable<Integer> persistable = mock(Persistable.class);
		
		when(persistable.isNew()).thenReturn(false);
		
		instance.saveOrUpdate( persistable );
		
		verify( persistable , times(1) ).isNew();
		verify( sf , times(1) ).getCurrentSession();
		verify( session , times(1) ).update( any(Persistable.class) );
		verify( session , never() ).save( any(Persistable.class) );
	}
	
	

}
