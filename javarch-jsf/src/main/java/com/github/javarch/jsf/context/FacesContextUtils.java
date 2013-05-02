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
