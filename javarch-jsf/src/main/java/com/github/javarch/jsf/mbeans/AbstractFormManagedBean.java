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


package com.github.javarch.jsf.mbeans;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.github.javarch.jsf.context.MessageContext;
import com.github.javarch.persistence.Persistable;
import com.github.javarch.persistence.Repository;
import com.github.javarch.support.log.Logger;
import com.github.javarch.support.log.Logging;
import com.github.javarch.support.validation.Validator;
import com.google.common.base.Optional;

public abstract class AbstractFormManagedBean  <T extends Persistable<?>> extends AbstractManagedBean<T>{

	private static final long serialVersionUID = 241528714229926461L;

	@Logging
	protected Logger log;	 
	
	@Inject
	protected Repository repository;

	@Inject
	protected MessageContext messageContext;
	
	protected String outcomeSuccess;
	
	protected String outcomeOnValidationsError;

	protected String outcomeOnError;
	
	/**
	 * Define se o objeto é readyonly ou não. Pode ser utilizado em conjunto com formulários
	 * para habilitar ou não a edição de informações em formulários.
	 */
	protected boolean isReadyOnly = true;
	

	
	protected Validator useValidator(){
		return null;
	}
	
	public void performAction(ActionEvent event){
		performAction();
	}
	
	public String performAction(){

		log.debug("Iniciando performAction na entidade {}.", getEntityName() );
		 
		try{			
			checkPreconditions();
			
			Validator validator = useValidator();
			
			if( Optional.fromNullable(validator).isPresent() ){
			
				BindingResult errors = new BindException( getEntity() , getEntityName() );
				validator.validate( getEntity() , errors );
				
				if ( errors.hasErrors() ){
					log.debug("Gerando erros {} mensagens de erros no objeto FacesMessage.", errors.getErrorCount());
					
					messageContext.addError(errors);
					
					return outcomeOnValidationsError;
				}
			}
				
		 
			log.debug("execute onAction({}) ", entity );
							
			onAction( entity );

			log.debug("execute onAction {} with success!", entity );
			
			messageContext.addInfo( messageContext.getMessageI18n( getClass().getSimpleName() + ".create.success" ) );
				
			log.debug("outcome after action = {}", outcomeSuccess);

			return outcomeSuccess;			
		
		} catch ( Exception e ) {									
			log.error("Entity: " + entity + ". Detalhes do erro são:" , e);
			
			messageContext.addError( messageContext.getMessageI18n( getClass().getSimpleName()  + ".save.exception" , e.getMessage() ) );
			
			return outcomeOnError;
		}
	}

	private void checkPreconditions() {
		checkNotNull( entity , "Entidade a ser persistida não pode ser null");
		checkNotNull( repository, "Deve existir um repositório associado ao ManagedBean");
		checkNotNull( messageContext,"Deve existir uma injeção do recurso MessageContext");
	}

	public abstract void onAction(T entidade);
	
	/**
	 * Informa se as informações da view serão apenas leitura.
	 * @return
	 */
	public final boolean isReadyOnly() {
		return isReadyOnly;
	}


	/**
	 * Seta o atributo do bean como ready only.
	 * 
	 * @param isReadyOnly
	 */
	public final void setReadyOnly(boolean isReadyOnly) {
		this.isReadyOnly = isReadyOnly;
	}
}
