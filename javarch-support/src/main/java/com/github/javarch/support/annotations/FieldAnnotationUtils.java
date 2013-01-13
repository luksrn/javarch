package com.github.javarch.support.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
