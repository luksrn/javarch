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

package com.github.javarch.persistence.orm.hibernate;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

import com.github.javarch.persistence.NamedQueryParameter;
import com.github.javarch.persistence.PageRequest;
import com.github.javarch.persistence.Persistable;
import com.github.javarch.persistence.QueryParameter;
import com.github.javarch.persistence.Repository;

/**
 * 
 * @author Lucas Oliveira
 *
 * @param <T>
 */
public interface HibernateRepository<T extends Persistable<?>> extends Repository<T>{

	List<T> findAll(Class<T> clazz,Order ... order) ;    

	List<T> findAll(Class<T> clazz,Projection projecoes, Order ... order) ;
	
	/**
	 * Busca um unico registro atraves de uma namedQuery definida no objeto de dominio.
	 * 
	 * 
	 * @see #findAllByNamedQuery(String, QueryParameter)
	 * 
	 * @param namedQuery nome da NamedQuery
	 * 
	 * @return Uma instancia do banco de dados caso a consulta retorne um resultado, null caso contrario.
	 */
	T findOneByNamedQuery(String namedQuery );

	/**
	 * Busca um unico registro atraves de uma namedQuery definida no objeto de dominio.
	 * 
	 * Uso: 
	 * 
	 * Dado a seguinte classe contendo duas namedQueries;
	 * 
	 *<pre>
	 *&#64;Entity
	 *&#64;NamedQueries({
	 *	&#64;NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
	 *	&#64;NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")})
	 *public class User extends AbstractPersistable {}	 	
	 *</pre>
	 * 
	 * Chamar o seguinte codigo:
	 * 
	 *<pre>
	 *QueryParameter params = QueryParameter.with("email", "user43&#64;gmail.com");
	 *User user = defaultRepository.findOneByNamedQuery("User.findByEmail", params );
	 *
	 *</pre>
	 * 
	 * @param namedQuery nome da NamedQuery
	 * 
	 * @return Uma instancia do banco de dados caso a consulta retorne um resultado, null caso contrario.
	 */
	T findOneByNamedQuery(String namedQuery, NamedQueryParameter parameters);

	List<T> findAllByNamedQuery(String namedQuery) ;
	
	List<T> findAllByNamedQuery(String namedQuery, NamedQueryParameter parameters) ;
	
	T findOneByExample(T objeto);
	
	List<T> findAllByExample(T objeto, Order... order);
	
	List<T> findAllByExample(T objeto, PageRequest page , Order... order);
}
