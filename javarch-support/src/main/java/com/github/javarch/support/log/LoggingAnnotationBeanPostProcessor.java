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
package com.github.javarch.support.log;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.github.javarch.support.annotations.FieldAnnotationUtils;

/**
 * http://www.rimple.com/tech/2011/2/21/spring-corner-what-are-those-post-processor-beans-anyway.html
 * 
 * @author Lucas Oliveira <luksrn@gmail.com>
 *
 */
@Component
public class LoggingAnnotationBeanPostProcessor implements BeanPostProcessor {
	
	private static final Logger LOG = LoggerFactory.getLogger(LoggingAnnotationBeanPostProcessor.class);
	
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    	
    	LOG.trace("Searching @Logging annotation into {}", beanName );
        List<Field> fields = FieldAnnotationUtils.findFieldsWithAnnotation( bean.getClass() , Logging.class );
        for (Field field : fields) {
            if ( field.getAnnotation(Logging.class) != null ) {
            	LOG.debug("Bean {} has @Logging annotation. Injecting Logger...", beanName );
                injectLogger(bean, field);
            }
        }
        return bean;
    }

    private void injectLogger(Object bean, Field field) {
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, bean, LoggerFactory.getLogger(field.getDeclaringClass()));
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}