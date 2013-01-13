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

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javarch.persistence.PageRequest;
import com.github.javarch.persistence.Persistable;
import com.github.javarch.persistence.Repository;

/**
 * 
 * @author Lucas Oliveira
 *
 * @param <T>
 */
public class GenericLazyDataModel<T extends Persistable<?>> extends LazyDataModel<T> {

	private static final long serialVersionUID = -4007004068507413082L;

	private List<T> list;

	private Repository<T> repository;
	

	private static final Logger LOG = LoggerFactory.getLogger(GenericLazyDataModel.class);

	
	public GenericLazyDataModel(Repository<T> repository) {
		super();
		this.repository = repository;
	}

	@Override
	public List<T> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		
	    try{
	    	PageRequest pageRequest = new PageRequest(startingAt, maxPerPage);
	    	
	    	LOG.debug("{}", pageRequest );
	    	
	    	list = repository.findAll(pageRequest);
	    	
	    	LOG.debug("{} returns {}", pageRequest,list );
	    	
		    // set the total of itens
	        if(getRowCount() <= 0){
	            setRowCount(repository.count().intValue());
	        }
	 	    
	    }catch (Exception e) {
	    	LOG.error("", e);
		}
	    return list;
	}

}
