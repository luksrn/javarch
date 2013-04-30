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

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.Entity;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.javarch.persistence.NamedQueryParameter;
import com.github.javarch.persistence.PageRequest;
import com.github.javarch.persistence.Persistable;
import com.github.javarch.persistence.QueryParameter;
import com.github.javarch.support.ParameterizedTypes;
import com.github.javarch.support.log.Logger;
import com.github.javarch.support.log.LoggerFactory;
import com.google.common.base.Preconditions;

import static com.google.common.base.Preconditions.*;


/**
 * Soon
 * 
 * @author Lucas Oliveira <i>luksrn@gmail.com</i>
 *
 * @param <T>
 */
@SuppressWarnings("all")
@Component("defaultRepository")
@Transactional(propagation=Propagation.REQUIRED)
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class DefaultHibernateRepository implements HibernateRepository {
	 
	private SessionFactory sf = null;
	
	private static final Logger LOG = LoggerFactory.getLogger(DefaultHibernateRepository.class);
	
	@Autowired
	public DefaultHibernateRepository(SessionFactory sf) {			
		this.sf = sf;		
	}

	protected Session getCurrentSession() {
		return sf.getCurrentSession();
	}

	protected StatelessSession openStatelessSession(){
		return sf.openStatelessSession();
	}
	
	public <T extends Persistable<?>> T saveOrUpdate(T entity) {
		if ( entity.isNew() ){
			getCurrentSession().save(entity);
		}else{
			getCurrentSession().update(entity);
		}
		
		return entity;
	}

	public <T extends Persistable<?>> void delete(T entity) {		
		getCurrentSession().delete(entity);
	}

	public <T extends Persistable<?>> T findOne(Class<T> clazz, Serializable id) { 
		return (T)getCurrentSession().get( clazz , id);		
	}


	public <T extends Persistable<?>> T getReference(Class<T> clazz, Serializable id) {
		return (T)getCurrentSession().byId(clazz).getReference(id);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Persistable<?>> List<T> findAll(Class<T> clazz) { 
		return createCriteria(clazz).list();
	}
	
	public void delete(Serializable id) {
		getCurrentSession().delete(id);
	}

	
	@SuppressWarnings("unchecked")
	public <T extends Persistable<?>> List<T> findAll(Class<T> clazz, PageRequest page) {
		return createCriteria(clazz).setFirstResult(page.getStartingAt())
				.setMaxResults(page.getMaxPerPage()).list();
	}
	

	
	public <T extends Persistable<?>> Long count(Class<T> clazz) { 
		Long count = (Long) createCriteria(clazz).setProjection(
							Projections.rowCount()).uniqueResult();
		return (count == null) ? 0L : count;
	}

	protected <T extends Persistable<?>> Criteria createCriteria(Class<T> clazz) {
		return getCurrentSession().createCriteria(clazz);
	}	
	
	public <T extends Persistable<?>> T findOneByNamedQuery(String namedQuery) {
		return findOneByNamedQuery(namedQuery, null);
	}

	
	public <T extends Persistable<?>> T findOneByNamedQuery(String namedQuery, NamedQueryParameter parameters) {
		List<T> result = findAllByNamedQuery(namedQuery, parameters);
		if (! result.isEmpty() ){
			return result.get(0);
		}
		return null;
	}	
	
	
	public <T extends Persistable<?>> List<T> findAllByNamedQuery(String namedQuery) {		
		return findAllByNamedQuery(namedQuery, null);
	}
	
	
	public <T extends Persistable<?>> List<T> findAllByNamedQuery(String namedQuery, NamedQueryParameter parameters) {

		Query query = getCurrentSession().getNamedQuery(namedQuery);
		// Method that will populate parameters if they are passed not null and empty
		if (parameters != null && !parameters.toMap().isEmpty()) {
			populateQueryParameters(query, parameters);
		}

		return query.list();
	}

	private void populateQueryParameters(Query query, NamedQueryParameter parameters) {
		
		for (Entry<String, Object> entry : parameters.toMap().entrySet()) {			
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	
	public <T extends Persistable<?>> List<T> findAll(Class<T> clazz, Order... order) {
		Criteria criteria = createCriteria(clazz);
		addOrder(criteria, order);
		return criteria.list();
		 
	}

	private Criteria addOrder(Criteria criteria, Order... order) {
		if ( order != null ){
			for (int i = 0; i < order.length; i++) {
				criteria.addOrder( order[i] );
			}
		}
		return criteria;
	}

	
	public <T extends Persistable<?>> List<T> findAll( Class<T> clazz, Projection projecoes, Order... order) {
		Criteria criteria = createCriteria(clazz);
		addOrder(criteria, order).setProjection(projecoes)
				.setResultTransformer( new AliasToBeanResultTransformer( clazz ));
		return criteria.list();
	}
	
	/**
	 * @param
	 * @return
	 */
	public <T extends Persistable<?>> T findOneByExample(T objeto) {		
		Example example = createExemple(objeto);
		Criteria criteria = createCriteria( (Class<T>)objeto.getClass() ).add(example);
		return (T)criteria.uniqueResult();
	}
	
	/**
	 * Busca o objeto de acordo com o objeto preenchido com os valores passado
	 * como exemplo.
	 *
	 * @param objeto
	 *            utilizado para realizar a busca
	 * @param ordenacoes
	 *            lista de critérios de ordenação
	 * @return Lista de objetos retornada
	 */
	public <T extends Persistable<?>> List<T> findAllByExample(T objeto, Order... ordenacoes) {
		return findAllByExample(objeto, null, ordenacoes);
	}

	/**
	 * Busca o objeto de acordo com o objeto preenchido com os valores passado
	 * como exemplo.
	 *
	 * @param objeto
	 * @param indiceInicial
	 * @param indiceFinal
	 * @param ordenacoes
	 *            lista de critérios de ordenação.
	 * @return Lista de orden
	 */
	public <T extends Persistable<?>> List<T> findAllByExample(T objeto, PageRequest page , Order... ordenacoes) {
		Example example = createExemple(objeto);
		Criteria criteria = createCriteria( (Class<T>)objeto.getClass() ).add(example);
		if( page != null ){
			criteria.setFirstResult(page.getStartingAt());
			criteria.setMaxResults(page.getMaxPerPage());
		}
		addOrder(criteria, ordenacoes);

		return (List<T>) criteria.list();
	}

	/**
	 * Método utilizado para criar o objeto Example. Este objeto é utilizado
	 * para realizar a busca por exemplo.
	 *
	 * @param objeto
	 *            sobre o qual o Example será criado
	 * @return em objeto do tipo Example
	 */
	protected Example createExemple(Object objeto) {

		Example example = Example.create(objeto);
		example.enableLike(MatchMode.ANYWHERE);
		example.excludeZeroes();
		example.ignoreCase();

		return example;
	}
}