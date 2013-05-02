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

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;

import com.github.javarch.jsf.GenericLazyDataModel;
import com.github.javarch.persistence.Persistable;
import com.github.javarch.persistence.Repository;


public abstract class AbstractListManagedBean<T extends Persistable<?>> extends AbstractManagedBean<T> {
		 
	private static final long serialVersionUID = -6759901699376760726L;

	@Inject
	private Repository repository;

	protected transient DataModel<T> dataModel;
	
	protected transient LazyDataModel<T> lazyDataModel;
	

	public DataModel<T> getDataModel() {
		/**
		 * Also note that the DataModel is lazily instantiated 
		 * in the getter, because it doesn't implement Serializable 
		 * and it would otherwise be null after deserialization.
		 */
		if ( dataModel == null ){
			dataModel = new ListDataModel<T>(null);					
		}
		return dataModel;
	}

	public void setDataModel(DataModel<T> dataModel) {
		this.dataModel = dataModel;
	}
	/**
	 * 
	 * @return
	 */
	public DataModel<T> getFindAllAsDataModel(){
		DataModel<T> dataModel = new ListDataModel<T>();
		dataModel.setWrappedData( repository.findAll( getGenericType() ) );
		return dataModel;
	}
	
	/**
	 * 
	 * @return
	 */
	public LazyDataModel<T> getFindAllAsLazyDataModel(){		
		if (lazyDataModel == null){
			lazyDataModel = new GenericLazyDataModel<T>( repository,  getGenericType() );
		}
		return lazyDataModel;
	}
}
