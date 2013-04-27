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


/**  
 * * In order to minimize dependencies, this class provides as a layer of abstraction over different logging mechanisms
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