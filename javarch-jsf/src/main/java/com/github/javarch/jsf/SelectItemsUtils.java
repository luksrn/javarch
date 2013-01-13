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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.stereotype.Component;

/**
 * Classe utilitária que dada uma lista de objetos transforma eles em uma lista de select items.
 * 
 * @author Lucas Oliveira
 * 
 */
@Component
@SuppressWarnings("all")
public class SelectItemsUtils implements Serializable{
		

	/**
	 * Dada uma Lista de objetos cria uma lista de SelectItem onde o valor será
	 * uma chamada ao método get do atributo <i>idProperty</i> e o valor o retorno
	 * do método get da propriedade <i>labelProperty</i>.
	 * 
	 * Esse método não adiciona um item para indicar a necessidade de seleção do item.
	 * 
	 * 
	 * @param list - Lista de objetos que devem construir a lista de select items.
	 * @param idProperty - propriedade que define o valor das chaves do select item.
	 * @param labelProperty - pripriedade que define o rótulo do select item
	 * @return Uma lista de objetos select item.
	 */
    public List<SelectItem> createSelectItems( final List list, String idProperty, String labelProperty ) {
    	return createSelectItems(list, idProperty, labelProperty, null);
    }

    /**
	 * Dada uma Lista de objetos cria uma lista de SelectItem onde o valor será
	 * uma chamada ao método get do atributo <i>idProperty</i> e o valor o retorno
	 * do método get da propriedade <i>labelProperty</i>.
	 * 
	 * Esse método  adiciona um item para indicar a necessidade de seleção do item definido
	 * no parâmetro labelOptional
	 * 
	 * 
	 * @param list - Lista de objetos que devem construir a lista de select items.
	 * @param idProperty - propriedade que define o valor das chaves do select item.
	 * @param labelProperty - pripriedade que define o rótulo do select item
	 * @param labelOptional - Rótulo do primeiro item do select item que define a necessidade de seleção de um item.
	 * @return Uma lista de objetos select item.
	 */
    public List<SelectItem> createSelectItems( final List iter, String idProperty, String labelProperty, String labelOptional ) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        
        if ( iter == null ){
        	return selectItems;
        }
        
        if (labelOptional != null) {
        	selectItems.add(new SelectItem (null,labelOptional));
        }
        
        
        Iterator it = iter.iterator();
        while ( it.hasNext() ) {
        	Object item = (Object) it.next();
            String label;
            String id;

            try {
                BeanWrapper entity = new BeanWrapperImpl( item );
                label = "" + entity.getPropertyValue( labelProperty );
                id = entity.getPropertyValue( idProperty ).toString();           
            } catch (Exception ex) {
            	label =  "???" + labelProperty + "???";
	            id =  "???" + idProperty + "???";
            }
            
            SelectItem selectItem = new SelectItem( id, label );
            selectItems.add( selectItem );
        }
        return selectItems;
    }

}
