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
import java.util.ArrayList;
import java.util.List;

import com.github.javarch.support.log.Logger;
import com.github.javarch.support.log.LoggerFactory;
 
public abstract class ClassAnnotationUtils extends AnnotationsUtils {

	private static Logger logger = LoggerFactory.getLogger(ClassAnnotationUtils.class);

	public static List<Class<?>> find(Class<?> clazz,Class<? extends Annotation> annotationClazz) {

		List<Class<?>> classList = new ArrayList<Class<?>>();

		return findAnnotationRecursive(clazz, annotationClazz, classList);
	}

	/**
	 * @param clazz
	 * @param annotationClass
	 * @param classes
	 * @return
	 */
	private static List<Class<?>> findAnnotationRecursive(Class<?> clazz,
			Class<? extends Annotation> annotationClass, List<Class<?>> classes) {

		logger.debug("Classname [{}]", clazz.getName());

		if (clazz.isAnnotationPresent(annotationClass)) {
			classes.add(clazz);
		}

		Class<?> superclass = clazz.getSuperclass();
		if (hasSuperClass(superclass)) {
			return findAnnotationRecursive(superclass, annotationClass, classes);
		}

		return classes;
	}
}