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

package com.github.javarch.support;

import java.io.Serializable;
import java.util.Collection;

/**
 * Classe utilitária para trabalhar com coleções na view JSF. 
 * Através da CollectionWrapper é possível chamar o método size na view.
 * 
 * @author Lucas Oliveira
 *
 * @param <T> Tipos de objetos que estão presentes na coleção.
 */
public class CollectionWrapper<T> implements Serializable {

	private static final long serialVersionUID = -3679006982070863690L;
	
	/**
	 * Referência a coleção.
	 */
	private Collection<T> collection;

	/**
	 * Construtor padrão.
	 * 
	 * @param collection Collection que se deseja encapsular.
	 */
    public CollectionWrapper(Collection<T> collection) {
        this.collection = collection;
    }

    /**
     * Obtém a collection que está sendo encapsulada.
     * 
     * @return Collection origem.
     */
    public Collection<T> getCollection() {
        return collection;
    }

    /**
     * Obtém o tamanho da coleção.
     * 
     * @return Tamanho da coleção.
     */
    public int getSize() {
        return collection.size();
    }

}
