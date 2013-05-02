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
package com.github.javarch.support.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.github.javarch.support.log.Logger;
import com.github.javarch.support.log.LoggerFactory;
 

public class MethodAnnotationUtils extends AnnotationsUtils {
	
	private static Logger logger = LoggerFactory.getLogger(MethodAnnotationUtils.class);

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
