package com.github.javarch.support.annotations;


public abstract class AnnotationsUtils {

	protected static boolean hasSuperClass(Class<?> superClass) {
		return null != superClass & !superClass.equals(Object.class);
	}


}
