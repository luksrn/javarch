package com.github.javarch.support.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodAnnotationUtils extends AnnotationsUtils {
	private static Logger logger = LoggerFactory
			.getLogger(MethodAnnotationUtils.class);

	/**
	 * @param class1
	 * @param class2
	 * @return
	 */
	public static List<Method> findMethodsWithAnnotation(Class<?> clazz,
			Class<? extends Annotation> annotationClass) {

		List<Method> methods = new ArrayList<Method>();

		List<Method> methodsFound = findMethodsWithAnnotationRecursive(clazz,
				annotationClass, methods);

		if (logger.isDebugEnabled()) {
			for (Method method : methodsFound) {
				logger.debug("Method Found [{}]", method.getName());
			}
		}

		return methodsFound;

	}

	private static List<Method> findMethodsWithAnnotationRecursive(
			final Class<?> clazz,
			final Class<? extends Annotation> annotationClass,
			final List<Method> methods) {

		Method[] classMethods = clazz.getDeclaredMethods();

		for (Method method : classMethods) {
			if (method.isAnnotationPresent(annotationClass)) {
				methods.add(method);
			}
		}

		Class<?> superClass = clazz.getSuperclass();

		if (hasSuperClass(superClass)) {
			return findMethodsWithAnnotationRecursive(superClass,
					annotationClass, methods);
		}

		return methods;
	}
}
