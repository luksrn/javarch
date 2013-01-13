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

import java.io.Serializable;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.github.javarch.support.MessageSourceAware;
import com.github.javarch.support.ParameterizedTypes;

/**
 * Classe abstrata que implementa o padrão Layer Supertype e tem como objetivo definir
 * uma classe que agrupa operações que são semelhantes em todas as classes de uma camada,
 * neste caso aos controllers do JSF (ManagedBeans).
 *  
 * 
 * @author Lucas Oliveira
 *
 * @param <T> Tipo do objeto que será gerenciado.
 */
public abstract class AbstractManagedBean<T> implements Serializable {

	public static final String FLASH_CLASSE_DOMAIN = "CLASSE_DOMINIO";
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractCrudManagedBean.class);
	/**
	 * Generated SerialVersionUID
	 */
	private static final long serialVersionUID = -1956041270484657885L;
	
	/**
	 * Entidade de dominio que será gerenciada pelo managed bean.
	 */
	protected T entidade;
	
	/**
	 * Tipo do objeto gerenciado pelo managed bean.
	 */
	private Class<T> clazz;
	
	/**
	 * Define se o objeto é readyonly ou não. Pode ser utilizado em conjunto com formulários
	 * para habilitar ou não a edição de informações em formulários.
	 */
	protected boolean isReadyOnly = true;
	
	
	/**
	 * Construtor padrão do abstractManagedBean. Sua responsabilidade é inicializar o
	 * objeto do tipo genérico que será gerenciado pelo MBean.
	 */
	public AbstractManagedBean() {
		try{						
			
		    instanciateGenericType();	
		    
		}catch (Exception e) {
			throw new IllegalStateException("Entidade " + entidade.getClass().getName() + " não possui construtor padrão.");
		}
	}
	

	/**
	 * Obtém o tipo da class passado como parâmetro genérico.
	 * 
	 * @return Class do parâmetro genérico.
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> getGenericType(){
		if( clazz == null ){
			clazz = ParameterizedTypes.getRawType(getClass());
		}
		return clazz;  
	}
	
	/**
	 * Cria uma instância da entidade do tipo da classe genérica definida como parâmetro.
	 * 
	 * A classe gerenciada precisa ter um construtor padrão, sem argumentos e público.
	 * 
	 * O método primeiro busca uma instancia da classe no objeto de Flash, caso encontre
	 * ele será referenciado pelo managed bean . Se nao houver nenhuma entidade, será instanciado
	 * um objeto do tipo genérico.
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	protected void instanciateGenericType() throws InstantiationException,
			IllegalAccessException {
		/**
		 * Obtém um parâmetro do flash scope FLASH_CLASSE_DOMAIN que pode conter um
		 * objeto que seja necessário repassar para outro MBean (levando em consideração
		 * mbeans para casos de uso)
		 */
		Object possivelClasse = getFlashScope().get(FLASH_CLASSE_DOMAIN);
		/**
		 *   Se ele for um objeto do tipo T que o mbean está gerenciado, reutiliza.
		 */		
		if ( possivelClasse != null && ClassUtils.isAssignable( getGenericType(), possivelClasse.getClass() ) ){
			setEntidade((T) possivelClasse );		
		}else{
			clazz = getGenericType();
			if( clazz != null){
				entidade = clazz.newInstance();
			}else{
				LOG.debug("Não foi possível definir tipo da entidade gerenciada. MBean.getEntidade() irá retornar null.");
			}
		}
	}

	/**
	 * Obtém um caminho convencionado "/{nomeDaentidade}/" que será utilizado nos redirects da arquitetura.
	 * 
	 * @return
	 */
	protected String getPathPersistableEntity() {
		return "/" + getEntityName();
	}
	
	/**
	 * Obtém o nome simples da classe gerenciada pelo managed bean (minusculo).
	 * 
	 * @return 
	 */
	protected String getEntityName(){
		return getEntidade().getClass().getSimpleName().toLowerCase();
	}
	
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
	
	/**
	 * Adiciona a mensagem da variável <i>msg</i> ao FacesContext. A mensagem adicionada terá
	 * o severity do tipo FacesMessage.SEVERITY_ERROR e não será associada à nenhum componente.
	 * 
	 * @param msg Mensagem que será adicionada ao FacesContext
	 */
	protected void addMessageError(String msg){		
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
	}
	

	/**
	 * Adiciona a mensagem da variável <i>msg</i> ao FacesContext. A mensagem adicionada terá
	 * o severity do tipo FacesMessage.SEVERITY_INFO e não será associada à nenhum componente.
	 * 
	 * @param msg Mensagem que será adicionada ao FacesContext
	 */
	protected void addMessageSuccess(String summary){		
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, ""));
	}	
	
	/**
	 * Obtém uma referência à entidade gerenciada pelo managed bean.
	 * 
	 * @return Objeto de dominio gerenciado pelo MBean.
	 */
	public T getEntidade() {
		return entidade;
	}

	/**
	 * Seta entidade gerenciada pelo MBean.
	 * @param entidade
	 */
	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}

	/**
	 * Informa se as informações da view serão apenas leitura.
	 * @return
	 */
	public boolean isReadyOnly() {
		return isReadyOnly;
	}


	/**
	 * Seta o atributo do bean como ready only.
	 * 
	 * @param isReadyOnly
	 */
	public void setReadyOnly(boolean isReadyOnly) {
		this.isReadyOnly = isReadyOnly;
	}
	
	/**
	 * Obtém o  Locale associado ao usuário.
	 * 
	 * @return Locale
	 */
	public Locale getCurrentLocale(){
		return getFacesContext().getViewRoot().getLocale();
	}
	
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
	public String getMessage(String code, String ... args ){
		return MessageSourceAware.getMessage(code, args, getCurrentLocale() );
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
