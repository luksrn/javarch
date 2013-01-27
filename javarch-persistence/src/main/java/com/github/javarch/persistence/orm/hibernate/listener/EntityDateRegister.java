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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.github.javarch.support.annotations.FieldAnnotationUtils;

/**
 * 
 * @author Lucas Oliveira
 *
 */
class EntityDateRegister {
	
	private static final Logger LOG = LoggerFactory.getLogger(EntityDateRegister.class);

	
	public static void setCurrentDateOnFieldWithAnnotation(final Object obj ,
			final Class<? extends Annotation> annotationClass) {
		try {
			if ( LOG.isDebugEnabled() ){
				LOG.debug("PreUpdateEvent - Em classe {}", obj.getClass().getName() );
			} 	 
			List<Field> campos = FieldAnnotationUtils.findFieldsWithAnnotation(obj.getClass(), annotationClass);
			for (  Field campo : campos ) { 
				ReflectionUtils.makeAccessible(campo);
				ReflectionUtils.setField(campo, obj, new Date() );
			}
		}catch(Exception e){
			LOG.error("O seguinte erro ocorreu ao tentar setar new Date() em campo anotado com {} da classe {}: {}",
					annotationClass.getName() , obj.getClass().getName() , e.getMessage() );
		}
			
	}
	
}
