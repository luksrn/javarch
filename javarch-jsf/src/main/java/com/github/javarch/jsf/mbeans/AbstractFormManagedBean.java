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
import com.github.javarch.support.validation.BeanValidation;
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
	
	protected BeanValidation useBeanValidation(){
		return null;
	}
	
	public void performAction(ActionEvent event){
		performAction();
	}
	
	public String performAction(){

		log.debug("Iniciando performAction na entidade {}.", getEntityName() );
		 
		try{			
			checkPreconditions();
			
			BeanValidation validator = useBeanValidation();
			
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
}
