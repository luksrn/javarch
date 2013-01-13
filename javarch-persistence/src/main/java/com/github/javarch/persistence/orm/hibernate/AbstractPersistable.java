package com.github.javarch.persistence.orm.hibernate;


import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.github.javarch.persistence.Persistable;

/**
 * Classe que pode servir de base para as entidades do Hibernate.
 * Baseada na classe  AbstractPersistable do Spring, contudo com acesso ao setId public.
 * 
 * @author Lucas Farias
 *
 * @param <PK>
 */
@MappedSuperclass
public abstract class AbstractPersistable implements Persistable<Long> ,Serializable {


	private static final long serialVersionUID = -5554308939380869754L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Obtém o valor do Identificador da entidade.
	 */
	public Long getId() {

		return id;
	}

	/**
	 * Seta o identificador da entidade.
	 * 
	 * @param id the id to set
	 */
	public void setId(final Long id) {

		this.id = id;
	}

	/**
	 * Verifica se o objeto é transiente.
	 * 
	 * @return true se {@link #getId()} retornar nulo. false caso contrário. 
	 */
	public boolean isNew() {

		return null == getId();
	}

	/**
	 * Representação padrão de uma classe de dominio.
	 */
	@Override
	public String toString() {
		return new StringBuilder("Entity ")
			.append(this.getClass().getSimpleName() )
			.append( " with id = ")
			.append(getId())
			.toString();		
	}

	/**
	 * Testa se dois objetos são iguais baseados apenas nos identificadores.
	 * 
	 * 
	 * @return 
	 */
	@Override
	public boolean equals(Object obj) {

		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(obj.getClass())) {
			return false;
		}

		AbstractPersistable that = (AbstractPersistable) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		int hashCode = 17;

		hashCode += null == getId() ? 0 : getId().hashCode() * 31;

		return hashCode;
	}
}
