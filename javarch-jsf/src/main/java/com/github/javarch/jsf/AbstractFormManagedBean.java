package com.github.javarch.jsf;

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
	protected Logger log; //private static final logger log = loggerFactory.getlogger(AbstractCreateManagedBean.class);	 
	
	protected String outcomeSuccess;
	
	protected String outcomeOnValidationsError;

	protected String outcomeOnError;
	
	@Inject
	protected Repository<T> repository;

	@Inject
	protected MessageContext jsfMessageContext;
	
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
			
				BindingResult errors = new BindException( getEntidade() , getEntityName() );
				validator.validate( getEntidade() , errors );
				
				if ( errors.hasErrors() ){
					log.debug("Gerando erros {} mensagens de erros no objeto FacesMessage.", errors.getErrorCount());
					
					jsfMessageContext.addError(errors);
					
					return outcomeOnValidationsError;
				}
			}
				
		 
			log.debug("execute onAction({}) ", entidade );
							
			onAction( entidade );

			log.debug("execute onAction {} with success!", entidade );
			
			jsfMessageContext.addInfo( getMessage( getClass().getSimpleName() + ".create.success" ) );
				
			log.debug("outcome after action = {}", outcomeSuccess);

			return outcomeSuccess;			
		
		} catch ( Exception e ) {									
			log.error("Entity: " + entidade + ". Detalhes do erro são:" , e);
			
			jsfMessageContext.addError( getMessage( getClass().getSimpleName()  + ".save.exception" , e.getMessage() ) );
			
			return outcomeOnError;
		}
	}

	private void checkPreconditions() {
		checkNotNull( entidade , "Entidade a ser persistida não pode ser null");
		checkNotNull( repository, "Deve existir um repositório associado ao ManagedBean");
		checkNotNull( jsfMessageContext,"Deve existir uma injeção do recurso MessageContext");
	}

	public abstract void onAction(T entidade);
}
