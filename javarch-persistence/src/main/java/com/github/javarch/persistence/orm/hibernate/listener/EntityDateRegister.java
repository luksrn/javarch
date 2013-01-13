package com.github.javarch.persistence.orm.hibernate.listener;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.github.javarch.support.annotations.FieldAnnotationUtils;

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
