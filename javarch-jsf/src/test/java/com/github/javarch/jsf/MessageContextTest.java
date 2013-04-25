package com.github.javarch.jsf;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.github.javarch.jsf.context.MessageContext;

import static org.mockito.Mockito.*;

@Ignore
public class MessageContextTest {
	

	private MessageContext messageContext;
	 
	
	@Before
	public void setUp(){
		messageContext = new MessageContext();
	 	FacesContext facesContext = mock(FacesContext.class);	
		when(facesContext.getMessageList()).thenReturn( new ArrayList<FacesMessage>() );
		
	//	messageContext.setFacesContext(facesContext);
	}

	@Test
	public void test() {
		List<FacesMessage> mensagens = messageContext.getMessages();
		assertNotNull(mensagens);
	}

}
