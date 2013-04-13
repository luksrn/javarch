package com.github.javarch.jsf.context;

import java.text.MessageFormat;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import com.github.javarch.support.Assert;

/**
 * 
 * @author luksrn
 *
 */
public class MessageContext {
	
	public FacesContext facesContext;
	

	/**
	 * Adiciona a mensagem da variável <i>msg</i> ao FacesContext. A mensagem adicionada terá
	 * o severity do tipo FacesMessage.SEVERITY_INFO e não será associada à nenhum componente.
	 * 
	 * @param msg Mensagem que será adicionada ao FacesContext
	 */
	public void addInfo(String msg, Object ... params) {
		add(null,msg, FacesMessage.SEVERITY_INFO , params);
	}


	public void addInfo(String idComponent, String msg, Object ...params ){
		add(idComponent,msg, FacesMessage.SEVERITY_INFO , params);
	}

	/**
	 * Adiciona a mensagem da variável <i>msg</i> ao FacesContext. A mensagem adicionada terá
	 * o severity do tipo FacesMessage.SEVERITY_ERROR e não será associada à nenhum componente.
	 * 
	 * @param msg Mensagem que será adicionada ao FacesContext
	 */
	public void addError(String msg, Object ... params) {
		add(null, msg,  FacesMessage.SEVERITY_ERROR, params);
	}
	
	public void addError(String idComponent, String msg, Object ...params ){
		add(idComponent, msg,  FacesMessage.SEVERITY_ERROR, params);
	}
	
	private void add(String componentId, String msg, Severity tipo, Object ... params) {		
		if (  msg != null && !msg.isEmpty() ){
			String mensagemFormatada = MessageFormat.format(msg, params);		
			facesContext.addMessage( componentId , new FacesMessage( tipo , mensagemFormatada, "") );
		}
	}
	
	public List<FacesMessage> getMessages() {
		return facesContext.getMessageList();
	}
 

	public void clear() {
		facesContext.getMessageList().clear();		
	}

	public void setFacesContext(FacesContext facesContext) {
		Assert.notNull(facesContext, "FacesContext can not be null.");
		this.facesContext = facesContext;
	}
}
