package com.github.javarch.jsf.context;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class MessageContextTest {

	private MessageContext instance;

	@Test
	public void testAddInfo() {
		
		FacesContext facesContextMock = mock(FacesContext.class);		
	//	Mockito.when(facesContextMock.addMessage(null, Mockito.any(FacesMessage.class) ) );				
		instance = new MessageContext();
	//	instance.setFacesContext(facesContextMock);
		
		// add mensagem
		instance.addInfo("Mensagem Test");
		
		// mensagem adicionada no FacesContext
		verify(facesContextMock).addMessage(null, any(FacesMessage.class));
	}

 

}
