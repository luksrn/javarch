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

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.javarch.persistence.NamedQueryParameter;
import com.github.javarch.persistence.PageRequest;
import com.github.javarch.persistence.QueryParameter;


/**
 * Soon
 * 
 * @author Lucas Oliveira <i>luksrn@gmail.com</i>
 *
 * @param <T>
 */
@SuppressWarnings("all")
@Component("defaultRepository")
@Transactional(propagation=Propagation.SUPPORTS)
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class DefaultHibernateRepository<T extends AbstractPersistable> implements HibernateRepository<T>{
	 
	private SessionFactory sf = null;

	private Class<T> genericClass = null;
	
	private static final Logger LOG = LoggerFactory.getLogger(DefaultHibernateRepository.class);

	@Autowired
	public DefaultHibernateRepository(SessionFactory sf) {			
		this.sf = sf;		
	}
	
	public void setClazz( Class< T > clazzToSet ){		
	      this.genericClass = clazzToSet;
	}	
	
	protected Class<T> getClazz() {
		if ( genericClass == null ) 
				throw new IllegalStateException("Não foi possível identificar qual Class será recuperada no método findOne. " +
				"É necessário chamar o método setClazz(Class<T> ) para definir o tipo do ojbeto " +
				"que está sendo manipilado");	
		return genericClass;
	}

	protected Session getCurrentSession() {
		return sf.getCurrentSession();
	}


	public T save(T entity) {
		if ( entity.isNew() ){
			getCurrentSession().save(entity);
		}else{
			getCurrentSession().update(entity);
		}
		
		return entity;
	}

	public void delete(T entity) {
		getCurrentSession().delete(entity);
	}

	public T findOne(Serializable id) { 
		return (T)getCurrentSession().get(getClazz(), id);		
	}


	public T getReference(Serializable id) {
		return (T)getCurrentSession().byId(genericClass).getReference(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() { 
		return createCriteria().list();
	}
	
	public void delete(Serializable id) {
		getCurrentSession().delete(id);
	}

	
	@SuppressWarnings("unchecked")
	public List<T> findAll(PageRequest page) {
		return createCriteria().setFirstResult(page.getStartingAt())
				.setMaxResults(page.getMaxPerPage()).list();
	}
	

	
	public Long count() { 
		Long count = (Long) createCriteria().setProjection(
							Projections.rowCount()).uniqueResult();
		return (count == null) ? 0L : count;
	}

	protected Criteria createCriteria() {
		return getCurrentSession().createCriteria(getClazz());
	}
	

	
	public T findOneByNamedQuery(String namedQuery) {
		return findOneByNamedQuery(namedQuery, null);
	}

	
	public T findOneByNamedQuery(String namedQuery, NamedQueryParameter parameters) {
		List<T> result = findAllByNamedQuery(namedQuery, parameters);
		if (! result.isEmpty() ){
			return result.get(0);
		}
		return null;
	}	
	
	
	public List<T> findAllByNamedQuery(String namedQuery) {		
		return findAllByNamedQuery(namedQuery, null);
	}
	
	
	public List<T> findAllByNamedQuery(String namedQuery, NamedQueryParameter parameters) {

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

	
	public List<T> findAll(Order... order) {
		Criteria criteria = createCriteria();
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

	
	public List<T> findAll(Projection projecoes, Order... order) {
		Criteria criteria = createCriteria();
		addOrder(criteria, order).setProjection(projecoes)
				.setResultTransformer( new AliasToBeanResultTransformer(getClazz()));
		return criteria.list();
	}
	
	/**
	 * @param
	 * @return
	 */
	public T findOneByExample(T objeto) {		
		Example example = createExemple(objeto);
		Criteria criteria = createCriteria().add(example);
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
	public List<T> findAllByExample(T objeto, Order... ordenacoes) {
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
	public List<T> findAllByExample(T objeto, PageRequest page , Order... ordenacoes) {
		Example example = createExemple(objeto);
		Criteria criteria = createCriteria().add(example);
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
	protected Example createExemple(T objeto) {

		Example example = Example.create(objeto);
		example.enableLike(MatchMode.ANYWHERE);
		example.excludeZeroes();
		example.ignoreCase();

		return example;
	}
}