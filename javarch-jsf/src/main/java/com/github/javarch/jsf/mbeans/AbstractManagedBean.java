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

import java.io.Serializable;

import javax.faces.context.FacesContext;
 
import org.springframework.util.ClassUtils;

import com.github.javarch.support.ParameterizedTypes;
import com.github.javarch.support.log.Logger;
import com.github.javarch.support.log.LoggerFactory;

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

	public static final String FLASH_DOMAIN_CLASS = "com.github.javarch.jsf.mbeans.abstractmanagedbean.domain.class";
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractManagedBean.class);
	/**
	 * Generated SerialVersionUID
	 */
	private static final long serialVersionUID = -1956041270484657885L;
	
	/**
	 * Entidade de dominio que será gerenciada pelo managed bean.
	 */
	protected T entity;
	
	/**
	 * Tipo do objeto gerenciado pelo managed bean.
	 */
	private Class<T> clazz;
	
		
	/**
	 * Construtor padrão do abstractManagedBean. Sua responsabilidade é inicializar o
	 * objeto do tipo genérico que será gerenciado pelo MBean.
	 */
	public AbstractManagedBean() {
		try{									
		    instanciateGenericType();			    
		}catch (Exception e) {
			throw new IllegalStateException("Entidade " + entity.getClass().getName() + " não possui construtor padrão.");
		}
	}
	

	/**
	 * Obtém o tipo da class passado como parâmetro genérico.
	 * 
	 * @return Class do parâmetro genérico.
	 */ 
	protected final Class<T> getGenericType(){
		if( clazz == null ){
			clazz = ParameterizedTypes.getRawType( getClass() );
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
	private void instanciateGenericType() throws InstantiationException,
			IllegalAccessException {
		/**
		 * Obtém um parâmetro do flash scope FLASH_CLASSE_DOMAIN que pode conter um
		 * objeto que seja necessário repassar para outro MBean (levando em consideração
		 * mbeans para casos de uso)
		 */
		Object possivelClasse = FacesContext.getCurrentInstance().getExternalContext().getFlash().get(FLASH_DOMAIN_CLASS);
		/**
		 *   Se ele for um objeto do tipo T que o mbean está gerenciado, reutiliza.
		 */		
		if ( possivelClasse != null && ClassUtils.isAssignable( getGenericType(), possivelClasse.getClass() ) ){
			setEntity( (T) possivelClasse );		
		}else{
			clazz = getGenericType();
			if( clazz != null){
				entity = clazz.newInstance();
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
	protected final String getPathPersistableEntity() {
		return "/" + getEntityName();
	}
	
	/**
	 * Obtém o nome simples da classe gerenciada pelo managed bean (minusculo).
	 * 
	 * @return 
	 */
	protected final String getEntityName(){
		return getEntity().getClass().getSimpleName().toLowerCase();
	}
	
 
	/**
	 * Obtém uma referência à entidade gerenciada pelo managed bean.
	 * 
	 * @return Objeto de dominio gerenciado pelo MBean.
	 */
	public final T getEntity() {
		return entity;
	}

	/**
	 * Seta entidade gerenciada pelo MBean.
	 * @param entidade
	 */
	public final void setEntity(T entidade) {
		this.entity = entidade;
	}


 
}
