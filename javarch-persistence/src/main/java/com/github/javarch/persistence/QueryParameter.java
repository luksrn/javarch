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

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * http://www.adam-bien.com/roller/abien/entry/generic_crud_service_aka_dao
 * @author lucas
 *
 */
public class QueryParameter {

	private List<Object> parameters = null;

	private QueryParameter(Object value){
		this.parameters = new ArrayList<Object>();
		this.parameters.add(value);
	}
	
	public static QueryParameter with(Object value){
		return new QueryParameter(value);
	}
	
	public QueryParameter and(String name,Object value){
		this.parameters.add(value);
		return this;
	}
	
	public Object[] toArray(){
		return this.parameters.toArray();
	}
}



