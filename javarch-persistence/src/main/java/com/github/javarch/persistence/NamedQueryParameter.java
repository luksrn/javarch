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
