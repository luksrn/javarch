package com.github.javarch.jsf.context;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FacesContextUtils {
	
	/**
	 * Obtém o FacesContext.
	 * 
	 * @return
	 */
	protected FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}

	
	/**
	 * Obtém o ExternalContext
	 * @return
	 */
	protected ExternalContext getExternalContext(){
		return getFacesContext().getExternalContext();
	}
	
	/**
	 * Obtém o FlashScope
	 * @return
	 */
	protected  Flash getFlashScope(){
		 return getExternalContext().getFlash();
	}
	/**
	 * Obtém um objeto HttpServletRequest associado a requisição atual.
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest(){
		return (HttpServletRequest) getExternalContext().getRequest();
	}
	
	/**
	 * Obtém um objeto HttpServletResponse associado a resposta atual.
	 * 
	 * @return
	 */
	protected HttpServletResponse getResponse(){
		return (HttpServletResponse)getExternalContext().getResponse();
	}
	
	
	public Object resolveExpression(String elExpression){
		return null;
	}
}
