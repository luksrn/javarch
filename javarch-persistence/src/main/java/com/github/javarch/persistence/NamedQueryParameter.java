package com.github.javarch.persistence;

import java.util.HashMap;
import java.util.Map;

/**
 *  * http://www.adam-bien.com/roller/abien/entry/generic_crud_service_aka_dao
 * @author luksrn
 *
 */
public class NamedQueryParameter {
	
	private Map<String, Object> parameters = null;

	private NamedQueryParameter(String name,Object value){
		this.parameters = new HashMap<String, Object>();
		this.parameters.put(name, value);
	}
	
	public static NamedQueryParameter with(String name,Object value){
		return new NamedQueryParameter(name, value);
	}
	
	public NamedQueryParameter and(String name,Object value){
		this.parameters.put(name, value);
		return this;
	}
	
	public Map<String, Object> toMap(){
		return this.parameters;
	}
}
