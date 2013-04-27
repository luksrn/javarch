package com.github.javarch.support.log;


/**
 * In order to minimize dependencies, this class provides as a layer of abstraction over different logging mechanisms 
 *
 */
public class LoggerFactory {

	private static final String CLASS_NAME_LOG4J_LOGGER = "org.apache.log4j.Logger";

	private static boolean LOG4J_AVAILABLE = false;

	static {

		try {
			Class.forName(CLASS_NAME_LOG4J_LOGGER);
			LOG4J_AVAILABLE = true;
		} catch (Exception e) {
			System.err.println("org.apache.log4j.Logger not found in classpath");
			LOG4J_AVAILABLE = false;
		}
	}

	public static final Logger getLogger(Class<?> clazz) {

		String className = clazz.getName();
		Logger logger = null;

		try {

			if (LOG4J_AVAILABLE) {
				logger = new LoggerImpl(className);
			}
		}
		catch (NoClassDefFoundError e) {
			System.err.println(e.getMessage());
		}

		return logger;
	}
}