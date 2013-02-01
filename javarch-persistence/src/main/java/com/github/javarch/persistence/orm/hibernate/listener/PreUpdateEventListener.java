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

package com.github.javarch.persistence.orm.hibernate.listener;

import org.hibernate.event.spi.PreUpdateEvent;

import com.github.javarch.persistence.annotation.LastUpdate;

public class PreUpdateEventListener implements org.hibernate.event.spi.PreUpdateEventListener {
 
	private static final long serialVersionUID = 6360911415858792281L;

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		EntityDateRegister.setCurrentDateOnFieldWithAnnotation(event.getEntity(), LastUpdate.class);			 
		return false;
	}	 
}
