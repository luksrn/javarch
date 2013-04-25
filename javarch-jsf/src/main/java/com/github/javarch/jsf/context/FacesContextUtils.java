package com.github.javarch.jsf.context;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class FacesContextUtils {
	
	/**
	 * Obtém o FacesContext.
	 * 
	 * @return
	 */
	public FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}

	
	/**
	 * Obtém o ExternalContext
	 * @return
	 */
	public ExternalContext getExternalContext(){
		return getFacesContext().getExternalContext();
	}
	
	/**
	 * Obtém o FlashScope
	 * @return
	 */
	public  Flash getFlashScope(){
		 return getExternalContext().getFlash();
	}
	/**
	 * Obtém um objeto HttpServletRequest associado a requisição atual.
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest(){
		return (HttpServletRequest) getExternalContext().getRequest();
	}
	
	/**
	 * Obtém um objeto HttpServletResponse associado a resposta atual.
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse(){
		return (HttpServletResponse)getExternalContext().getResponse();
	}
	
	
	public Object resolveExpression(String elExpression){
		return null;
	}
	
	/**
	 * Retorna o caminho da aplicação, incluindo protocolo http.
	 * 
	 * @return Caminho da aplicação
	 */
	public String getApplicationPath() {
		HttpServletRequest request = getRequest();
		return request.getScheme() + "://" + request.getServerName()
				+ (request.getServerPort() != 80? ":" + request.getServerPort() : "")
				+ request.getContextPath();
	}
}
