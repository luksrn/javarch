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

package com.github.javarch.jsf.config;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * Customização do escopo de view baseado no UIViewRoot do JSF onde 
 * será armazenado o objeto. Note que os atributos do bean e o próprio 
 * bean deve ser marcado como serializável já que ele será armazanado no view map.
 * 
 * 
 * @author Lucas Oliveira
 */
public class ViewScope implements Scope  {

	private Map<String, Object> getViewMap() {
		return FacesContext.getCurrentInstance().getViewRoot().getViewMap();
	}
	
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Map<String, Object> viewMap = getViewMap();
		
		if (viewMap.containsKey(name)){
			return viewMap.get(name);
		}else{
			Object object = objectFactory.getObject();
			viewMap.put(name, object );
			return object;
		}		
	}

	public Object remove(String name) {
		return getViewMap().remove(name);		
	}

	public void registerDestructionCallback(String name, Runnable callback) {		
		
	}

	public Object resolveContextualObject(String key) {
		return null;
	}

	public String getConversationId() {
		return null;
	}
}