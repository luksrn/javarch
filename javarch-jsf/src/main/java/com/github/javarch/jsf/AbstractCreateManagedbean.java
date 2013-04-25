package com.github.javarch.jsf;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.validation.BindException;

import com.github.javarch.jsf.context.MessageContext;
import com.github.javarch.persistence.Persistable;
import com.github.javarch.persistence.Repository;
import com.github.javarch.support.log.Logger;
import com.github.javarch.support.log.LoggerFactory;
import com.github.javarch.support.validation.BeanValidation;
import com.google.common.base.Preconditions;

@Named
public abstract class AbstractCreateManagedbean <T extends Persistable<?>> extends AbstractManagedBean<T>{
 
	private static final long serialVersionUID = 241528714229926461L;

	private static final Logger LOG = LoggerFactory.getLogger(AbstractCreateManagedbean.class);	 
	
	protected String outcomeAfterCreated;
	
	protected String outcomeOnValidationsError;

	protected String outcomeOnErrorCreate;
	
	@Inject
	private Repository<T> repository;

	@Inject
	private MessageContext messageContext;
	
	public BeanValidation getBeanValidator(){
		return null;
	}
	
	public void create(ActionEvent event){
		create();
	}
	
	public String create(){

		LOG.debug("Iniciando processo de cadastro da entidade {}.", getEntityName() );
		
		Preconditions.checkNotNull(getEntidade(), "Entidade a ser persistida não pode ser null");
		Preconditions.checkNotNull(repository, "Deve existir um repositório associado ao ManagedBean");
		Preconditions.checkNotNull(messageContext,"Deve existir uma injeção do recurso MessageContext");
		
		try{			
			
			BeanValidation validator = getBeanValidator();
			
			if( validator != null ){
				BindException errors = new BindException( getEntidade() , getEntityName() );
				validator.validate( getEntidade() , errors );
				
				if ( errors.hasErrors() ){
					LOG.debug("Gerando erros {} mensagens de erros no objeto FacesMessage.", errors.getErrorCount());
					
					messageContext.addError(errors);
					
					return outcomeOnValidationsError;
				}
			}
				
		 
			LOG.debug("Tentando persistir entidade {} ", entidade );
							
			onCreate( entidade );

			LOG.debug("Entidade {} persistida com sucesso!", entidade );
			
			messageContext.addInfo( getMessage( getClass().getSimpleName() + ".create.success" ) );
				
			LOG.debug("RedirectAfterCreated = {}", outcomeAfterCreated);

			return outcomeAfterCreated;			
		
		} catch ( Exception e ) {									
			LOG.error("Ocorreu um erro ao tentar salvar entidade " + entidade + ". Detalhes do erro são:" , e);
			
			messageContext.addError( getMessage("abstractcrudmanagedbean.save.exception", e.getMessage() ) );
			
			return outcomeOnErrorCreate;
		}
	}

	protected void onCreate(T entidade){
		repository.save( entidade );
	}	 
}
