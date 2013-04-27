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