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
