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

import com.github.javarch.persistence.Persistable;
import com.google.common.base.Preconditions;

public class AbstractDeleteManagedBean <T extends Persistable<?>> extends AbstractFormManagedBean<T>{
 
	private static final long serialVersionUID = 2583673165183668129L;

	@Override
	public void onAction(T entidade) {
		Preconditions.checkState( entidade.getId() != null );
		repository.delete( entidade );		
	}

}
