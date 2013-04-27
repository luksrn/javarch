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

package com.github.javarch.jsf;

import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.github.javarch.jsf.context.MessageContext;
import com.github.javarch.persistence.Persistable;
import com.github.javarch.persistence.Repository;
import com.github.javarch.persistence.orm.hibernate.BeanValidator;
import com.github.javarch.support.SpringApplicationContext;

/**
 * 
 * Especialização de AbstractManagedBean para as operações de CRUD das entidades
 * gerenciadas. Com isso, evitamos duplicação de códgio, centralizando a lógica de
 * destas operações que estaria repetida por diversas classes em uma única classe.  
 * 
 * @author Lucas Oliveira
 *
 * @param <T>
 * 
 */ 
public abstract class AbstractCrudManagedBean<T extends Persistable<?>> extends AbstractManagedBean<T> {
	
	/**
	 * generated UID
	 */
	private static final long serialVersionUID = 5164331737906224197L;

	private static final Logger LOG = LoggerFactory.getLogger(AbstractCrudManagedBean.class);

	/**
	 * 
	 */
	protected transient DataModel<T> dataModel;
	
	/**
	 * 
	 */
	protected transient LazyDataModel<T> lazyDataModel;
	
	/**
	 * url que será retornada ao JSF após o método cadastrar.
	 */
	protected String urlRedirectDepoisDeCadastrar;
	
	/**
	 * url que será retornada ao JSF após o método atualizar
	 */
	protected String urlRedirectDepoisDeAtualizar;

	/**
	 * url que será retornada ao JSF após o método remover.
	 */
	protected String urlRedirectDepoisDeRemover;
	
	/**
	 * 
	 */
	protected String urlRedirectDepoisDeListar;
	
	/**
	 * 
	 */
	protected String urlRedirectOnErrorCadastrar;
	
	/**
	 * 
	 */
	protected String urlRedirectOnErrorAtualizar;
	
	/**
	 * 
	 */
	protected String urlRedirectOnErrorListar;
	
	/**
	 * 
	 */
	protected String urlRedirectOnErrorRemover;
	
	/**
	 * 
	 */
	private transient Repository<T> repository;
		
	@Autowired
	private MessageContext messageContext;
	
	public AbstractCrudManagedBean() {		
		super();			
		getRepository();
	}

	 
	/**
	 * Método responsável por gerar FacesMessage do tipo FacesMessage.SEVERITY_ERROR
	 * com as informações de um objeto  BindingResult.
	 *  
	 * @param erros
	 */
	protected void geraFacesMessagesErros(BindingResult erros) {
		
		Assert.notNull(erros, "BindingResult não pode ser nulo.");
		
		LOG.debug("Gerando erros {} mensagens de erros no objeto FacesMessage.", erros.getErrorCount());
				
		for (FieldError fieldError : erros.getFieldErrors()) {
			LOG.debug("Campo {} apresenta o seguinte erro de validação: {}.", fieldError.getField(), fieldError.getDefaultMessage());			
			messageContext.addError( fieldError.getDefaultMessage() );				             				
		}
				
	}
	  
	
	/**
	 * 
	 * @return
	 */
	public DataModel<T> getFindAllAsDataModel(){
		DataModel<T> dataModel = new ListDataModel<T>();
		dataModel.setWrappedData( getRepository().findAll( getGenericType() ) );
		return dataModel;
	}
	
	/**
	 * 
	 * @return
	 */
	public LazyDataModel<T> getFindAllAsLazyDataModel(){		
		if (lazyDataModel == null){
			lazyDataModel = new GenericLazyDataModel<T>(getRepository(),getGenericType());
		}
		return lazyDataModel;
	}
 
	public void atualizar(ActionEvent event){
		atualizar();
	}
	/**
	 * Método responsável por atualizar uma entidade gerenciada da base de dados.
	 * 
	 * <br/>
	 * As mensagens são internacionalizadas através das chaves: 
	 * <ul>
	 * 		<li>Para as operações realizadas com sucesso: abstractcrudmanagedbean.update.success.{@link #getEntityName()}</li>
	 * 		<li>Para as situações de excessão: abstractcrudmanagedbean.update.exception.{@link #getEntityName()}</li>
	 * <ul>
	 * 
	 * @return urlRedirectDepoisDeAtualizar caso a entidade seja atualizada com sucesso. <i>urlRedirectOnErrorRemover</i> caso o método encontre alguma exception durante o processo.
	 * 
	 */
	public String atualizar(){
		try{
			
			LOG.debug("Iniciando processo de atualização da entidade {}.", getEntityName());
		
			/**
			 * Realiza alguma possível ação antes de cadastrar.
			 */
			antesDeAtualizar();
			/**
			 * Realiza validações em anotações hibernate validator.
			 */
			
			BindingResult result = BeanValidator.validate( entidade );
			
			
			if ( result.hasErrors() ){
				LOG.debug("Ocorreram " + result.getErrorCount() + " erros de validação JSR 303 - Bean Validation. Voltando para página de cadastro da entidade " + getEntidade().getClass().getSimpleName() );
				geraFacesMessagesErros(result);
				return null;
			} else {
			
				getRepository().saveOrUpdate(entidade);

				depoisDeAtualizar();

				messageContext.addInfo( getMessage("abstractcrudmanagedbean.update.success." + getEntityName() ));
				
				return urlRedirectDepoisDeAtualizar;
			}

		}catch (Exception e) {			
			e.printStackTrace();
			messageContext.addError( getMessage("abstractcrudmanagedbean.update.exception" + getEntityName() , e.getMessage() ) );
			return urlRedirectOnErrorAtualizar;
		}
	}
	
	
	private String redirectOnErroAtualizar() {
		return null;
	}

	protected String redirectDepoisDeAtualizar() {
		return null;
	}

	/**
	 * 
	 * Método que executa alguma lógica de negócio da aplicação após a atualização da entidade
	 * gerenciada.  
	 */
	protected void depoisDeAtualizar() {
		// TODO Auto-generated method stub
		
	}

	

	/**
	 * Método que executa alguma lógica de negócio da aplicação antes da atualização da da entidade
	 * gerenciada.  
	 */
	protected void antesDeAtualizar() {
		// TODO Auto-generated method stub
		
	}

	public void remover(ActionEvent e){
		LOG.debug("Chamada através de ActionEvent.");
		remover();
	}

	/**
	 * Método responsável por remover uma entidade gerenciada da base de dados.
	 * 
	 * @return urlRedirectDepoisDeRemover caso a entidade seja removida com 
	 * sucesso.  
	 * <i>urlRedirectOnErrorRemover</i> caso o método encontre alguma exception durante o processo. 
	 */
	public String remover(){
		try{
			
			LOG.debug("Iniciando processo de remoção da entidade {}.", getEntityName());
		
			antesDeRemover();
			/**
			 * Realiza alguma validação antes de remover a entidade.
			 * 
			 * TODO rever esse metodo
			 */
		
			getRepository().delete(entidade);
						
			LOG.debug("Entidade removida com sucesso.");
		
				
			messageContext.addInfo( getMessage("abstractcrudmanagedbean.remove.success." + getEntityName() ));
				
			depoisDeRemover();

			return urlRedirectDepoisDeRemover;
		}catch (Exception e) {			
			e.printStackTrace();
			messageContext.addError( getMessage("abstractcrudmanagedbean.remove.exception." + getEntityName(), e.getMessage() ) );
			return redirectOnErroAtualizar();
		}
	}

	/**
	 * Método que executa alguma lógica de negócio da aplicação após a remoção da entidade
	 * gerenciada.  
	 */
	protected void depoisDeRemover() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Realiza alguma validação antes de remover a entidade gerenciada.
	 * 
	 * @return true se deve prosseguir com a remoção. false caso deva ser abortada.
	 */
	protected boolean antesDeRemover() {
		return true;
	}
	
	/**
	 * Carrega o uma entidade "Entidade" baseada id. Utilizado inicialmente no <f:event> após 
	 * capturar o id no f:viewParam
	 * @return
	 */
	@SuppressWarnings("all")
	public String loadAfterSetViewParamId(){
		
		if ( ! entidade.isNew() ){			
			setEntidade( (T)getRepository().findOne(getGenericType(), entidade.getId()));
		}
	 	
	 	return "";
	}


	public DataModel<T> getDataModel() {
		/**
		 * Also note that the DataModel is lazily instantiated 
		 * in the getter, because it doesn't implement Serializable 
		 * and it would otherwise be null after deserialization.
		 */
		if ( dataModel == null ){
			dataModel = new ListDataModel<T>(null);					
		}
		return dataModel;
	}

	public void setDataModel(DataModel<T> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * Por padrão retorna uma instância de defaultRepository
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Repository<T> getRepository(){
		if ( repository == null){
			repository = SpringApplicationContext.getBean("defaultRepository", Repository.class);		 
		}
		
		return repository;
	}
}
