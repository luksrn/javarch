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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.github.javarch.support.log.Logger;
import com.github.javarch.support.log.LoggerFactory;
 

public class FieldAnnotationUtils  extends AnnotationsUtils {

	private static Logger logger = LoggerFactory.getLogger(FieldAnnotationUtils.class);

	public static Field findFirstFieldWithAnnotation(final Class<?> clazz,
			final Class<? extends Annotation> annotationClass) {

		logger.debug("Classname [{}]", clazz.getName());

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			if (field.isAnnotationPresent(annotationClass)) {
				return field;
			}
		}

		Class<?> superClass = clazz.getSuperclass();

		if (hasSuperClass(superClass)) {
			return findFirstFieldWithAnnotation(superClass, annotationClass);
		}
		return null;
	}

	public static List<Field> findFieldsWithAnnotation(final Class<?> clazz,
			final Class<? extends Annotation> annotationClass) {

		List<Field> fieldsList = new ArrayList<Field>();

		return findFieldsWithAnnotationRecursive(clazz, annotationClass,
				fieldsList);
	}

	/**
	 * @param clazz
	 * @param annotationClass
	 * @param fields
	 * @return
	 */
	private static List<Field> findFieldsWithAnnotationRecursive(
			Class<?> clazz, Class<? extends Annotation> annotationClass,
			List<Field> fieldsList) {

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			if (field.isAnnotationPresent(annotationClass)) {
				fieldsList.add(field);
			}
		}

		Class<?> superClass = clazz.getSuperclass();

		if (hasSuperClass(superClass)) {
			return findFieldsWithAnnotationRecursive(superClass,
					annotationClass, fieldsList);
		}

		return fieldsList;
	}
}
