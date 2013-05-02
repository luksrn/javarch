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

import java.text.MessageFormat;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.github.javarch.jsf.CurrentLocale;
import com.github.javarch.support.Assert;
import com.github.javarch.support.MessageSourceAware;

/**
 * 
 * @author luksrn
 */
@Component
public class MessageContext {
	
	/**
	 * 
	 */
	@Autowired
	public FacesContextUtils facesContext;
	
	/**
	 * 
	 */
	@Autowired
	private CurrentLocale locale;
	
	/**
	 * Obtém uma mensagem dos arquivos de i18n definidos na arquitetura através 
	 * da classe MessageSourceAware utilizado o Locale do usuário. 
	 * 
	 * 
	 * @param code - Código que representa a chave do arquivo properties.
	 * @param args - Possíveis argumentos que a mensagem requisitada utilize.
	 * 
	 * @return Em caso de sucesso a mensagem recuperada dos arquivos properties. Caso
	 * ocorra um erro a mensagem será ???code??? 
	 * 
	 * @see MessageSourceAware
	 * 
	 */
	public String getMessageI18n(String code, String ... args ){
		return MessageSourceAware.getMessage(code, args, locale.getLocale() );
	}
	

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
	
	public void addError(BindingResult erros){
		Assert.notNull(erros, "BindingResult não pode ser nulo.");
						
		for (FieldError fieldError : erros.getFieldErrors()) {						
			addError( fieldError.getDefaultMessage() );				             				
		}			
	}
	
	public void addError(String idComponent, String msg, Object ...params ){
		add(idComponent, msg,  FacesMessage.SEVERITY_ERROR, params);
	}
	
	private void add(String componentId, String msg, Severity tipo, Object ... params) {		
		if (  msg != null && !msg.isEmpty() ){
			String mensagemFormatada = MessageFormat.format(msg, params);		
			facesContext.getFacesContext().addMessage( componentId , new FacesMessage( tipo , mensagemFormatada, "") );
		}
	}
	
	public List<FacesMessage> getMessages() {
		return facesContext.getFacesContext().getMessageList();
	}
 

	public void clear() {
		facesContext.getFacesContext().getMessageList().clear();		
	}

	public void setFacesContext(FacesContextUtils facesContext) {
		Assert.notNull(facesContext, "FacesContext can not be null.");
		this.facesContext = facesContext;
	}
}
