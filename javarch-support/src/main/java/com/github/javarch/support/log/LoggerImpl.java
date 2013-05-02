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
 * 
 * @author lucas
 *
 */
public class LoggerImpl implements Logger {

	// Private Data Members
	private org.slf4j.Logger wrappedLogger;

	public LoggerImpl(String className ) {
		wrappedLogger = org.slf4j.LoggerFactory.getLogger(className);
	}
 

	@Override
	public void debug(String message, Object... arguments) {
		if ( isDebugEnabled() ) {			
			try {
				wrappedLogger.debug( message , arguments );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
  
	@Override
	public void error(String message, Object... arguments) {
		if (isErrorEnabled()) {			
			try {
				wrappedLogger.error( message ,arguments );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
 

	@Override
	public void info(String message, Object... arguments) {
		if (isInfoEnabled()) {			
			try {
				wrappedLogger.info( message );
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
 
	@Override
	public void trace(String message, Object... arguments) {
		if ( isTraceEnabled() ){
			try{
				wrappedLogger.trace(message,arguments);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
 

	@Override
	public void warn(String message, Object... arguments) {
		if ( isWarnEnabled() ){
			try{
				wrappedLogger.warn(message, arguments);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	
	private boolean isDebugEnabled() {
		return wrappedLogger.isDebugEnabled();
	}

	
	private boolean isErrorEnabled() {
		return wrappedLogger.isErrorEnabled();
	}

	
	private boolean isInfoEnabled() {
		return wrappedLogger.isInfoEnabled();
	}

	
	private boolean isTraceEnabled() {
		return wrappedLogger.isTraceEnabled();
	}

	
	private boolean isWarnEnabled() {
		return wrappedLogger.isWarnEnabled();
	}
}
