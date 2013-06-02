package com.github.javarch.jsf;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.javarch.jsf.context.MessageContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest( { FacesContext.class })
public class MessageContextTest {
	
	@Mock
	private FacesContext facesContext;

	private MessageContext messageContext;
	 

	@Before
	public void setUp() throws Exception {
		messageContext = new MessageContext();

        PowerMockito.mockStatic(FacesContext.class);
        PowerMockito.doReturn(this.facesContext).when(FacesContext.class, "getCurrentInstance");
        
		Mockito.when(this.facesContext.getMessageList()).thenReturn( new ArrayList<FacesMessage>() );
		
		 
	}

	@Test
	public void test() {
		//List<FacesMessage> mensagens = messageContext.getMessages();
		List<FacesMessage> mensagens = facesContext.getMessageList();
		assertNotNull(mensagens);
	}

}
