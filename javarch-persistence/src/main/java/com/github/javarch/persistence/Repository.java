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

package com.github.javarch.persistence;

import java.io.Serializable;
import java.util.List;

/**
 * Interface que implementa o padrao Repository, definido no catálogo EEA 
 * http://martinfowler.com/eaaCatalog/repository.html.
 * 
 * @author Lucas Oliveira
 * @since 0.1
 * @param <T>
 */
public interface Repository {
			
	/**
	 * Persiste uma entidade anotada com @Entity no banco de dados.
	 * 
	 * @param entity - Objeto que devera ser persistido no banco de dados.
	 * @return Uma referencia ao proprio objeto persistido. 
	 */
	<T extends Persistable<?>> T  saveOrUpdate(T entity);
	
	/**
	 * Busca um objeto pelo ID.
	 * 
	 * @see #setClazz(Class)
	 * @param id
	 * @return
	 */
	<T extends Persistable<?>> T  findOne(Class<T> clazz,Serializable id);
	

	/**
	 * Obtain an entity reference without initializing its data

		Sometimes referred to as lazy loading, the ability to obtain a reference 
		to an entity without having to load its data is hugely important. The most 
		common case being the need to create an association between an entity and 
		another, existing entity.
		
		Example 3.3. Example of obtaining an entity reference without initializing its data
		
		Book book = new Book();
		book.setAuthor( session.byId( Author.class ).getReference( authorId ) );
		
		Book book = new Book();
		book.setAuthor( entityManager.getReference( Author.class, authorId ) );
		
		
		getReference

    	Should be used in cases where the identifier is assumed to exist, where non-existence 
    	would be an actual error. Should never be used to test existence. That is because this 
    	method will prefer to create and return a proxy if the data is not already associated 
    	with the Session rather than hit the database. The quintessential use-case for using 
    	this method is to create foreign-key based associations.

	 * @param id
	 * @return
	 */
	<T extends Persistable<?>> T getReference(Class<T> clazz,Serializable id);
	
	/**
	 * Dado uma entidade definida pelo metodo {@link #setClazz(Class)}, busca todos os registros 
	 * da tabela mapeada.   
	 * 
	 * <b>Atenção:</b> Utilizar metodo com cautela.
	 * 
	 * @see #setClazz(Class)
	 * @see #findAll(PageRequest)
	 * @see #findOne(Serializable)
	 * 
	 * @return Uma lista com todos os registros do banco de dados.
	 */
	<T extends Persistable<?>> List<T> findAll(Class<T> clazz);	
	
	
	/**
	 * Dado uma entidade definida pelo metodo {@link #setClazz(Class)}, realiza uma busca paginada por 
	 * todos os registros da tabela mapeada começando por {@link PageRequest#getStartingAt()} contendo
	 * {@link PageRequest#getMaxPerPage()} registros.  
	 *   
	 * @see #setClazz(Class)
	 * @see #findOne(Serializable)
	 * @return Uma lista com todos os registros no intervalo da paginação.
	 */
	<T extends Persistable<?>> List<T> findAll(Class<T> clazz,PageRequest page);	
 
	/**
	 * Remove um registro do banco de dados pelo seu ID.
	 * 
	 * @see #setClazz(Class)
	 * @see #delete(Serializable)
	 * @param entity
	 */
	<T extends Persistable<?>> void delete(T entity);
	
	/**
	 * Count dos registros da tabela mapeada pela classe {@link #setClazz(Class)}
	 * 
	 * @see #setClazz(Class)
	 * @return
	 */
	<T extends Persistable<?>> Long count(Class<T> clazz);
	
}
