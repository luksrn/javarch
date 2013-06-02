package com.github.javarch.jsf.context;

import static org.junit.Assert.assertNotNull;

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

@RunWith(PowerMockRunner.class)
@PrepareForTest( { FacesContext.class })
public class MessageContextTest {
	
	@Mock
	FacesContext facesContext;

	@Mock
	FacesContextUtils facesContextUtils;
	
	MessageContext instance;
	 

	@Before
	public void setUp() throws Exception {
		instance = new MessageContext();

        PowerMockito.mockStatic(FacesContext.class);
        PowerMockito.doReturn(this.facesContext).when(FacesContext.class, "getCurrentInstance");        
		Mockito.when(this.facesContext.getMessageList()).thenReturn( new ArrayList<FacesMessage>() );
		
		Mockito.when(this.facesContextUtils.getFacesContext()).thenReturn(this.facesContext);
		
		instance.setFacesContext(facesContextUtils);
	}

	@Test
	public void deveRetornarUmaListaVazia() { 
		List<FacesMessage> mensagens = instance.getMessages();
		assertNotNull(mensagens);
	}
	
	@Test
	public void deveRetornarUmaMensagemInfoQuandoAdicionarMensagemInfo(){
		instance.addInfo("Mensagem Info!");		
 
	}

}
