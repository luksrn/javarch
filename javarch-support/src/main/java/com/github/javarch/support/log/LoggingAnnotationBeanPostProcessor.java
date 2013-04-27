package com.github.javarch.support.log;

import java.lang.reflect.Field;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class LoggingAnnotationBeanPostProcessor implements MergedBeanDefinitionPostProcessor {
	
    public void postProcessMergedBeanDefinition(RootBeanDefinition rootBeanDefinition, Class beanType, String beanName) {
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Logging.class) != null) {
                injectLogger(bean, field);
            }
        }
        return bean;
    }

    private void injectLogger(Object bean, Field field) {
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, bean,
                                 LoggerFactory.getLogger(field.getDeclaringClass()));
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}