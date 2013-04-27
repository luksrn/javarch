package com.github.javarch.support.log;



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
	public void error(Throwable throwable) {
		
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

	@Override
	public boolean isDebugEnabled() {
		return wrappedLogger.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return wrappedLogger.isErrorEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return wrappedLogger.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return wrappedLogger.isTraceEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return wrappedLogger.isWarnEnabled();
	}
}
