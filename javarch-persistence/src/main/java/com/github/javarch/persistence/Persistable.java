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

/**
 * Interface que representa todos os objetos que devem possuir identidade própria.
 * 
 * @author Lucas Oliveira
 *
 * @param <ID>
 */
public interface Persistable <ID extends Serializable> extends Serializable {

	/**
	 * Identificador do objeto persistente.
	 * @return
	 */
	ID getId();
	
	/**
	 * 
	 * @param id
	 */
	void setId(ID id);
	
	/**
	 * Verifica se o objeto esta no estado transiente ou persistente/detached.
	 * Para maiores informacoes sobre os estados dos objetos; 
	 * {@link  http://docs.jboss.org/hibernate/orm/4.1/manual/en-US/html/ch11.html#objectstate-overview}
	 * @return true se o objeto estiver no estado novo. false caso contrario.
	 */
	boolean isNew();
	
	/**
	 * Informa se o objeto está ativo ou não. Objetos ativos possuem um registro válido
	 * no banco de dados.
	 *  
	 * @return
	 */
	boolean isActive();
}
