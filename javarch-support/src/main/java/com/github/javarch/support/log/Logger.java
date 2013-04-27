package com.github.javarch.support.log;


public interface Logger {
 
	public void debug(String message, Object... arguments);
 
	public void error(Throwable throwable);

	public void error(String message, Object... arguments);
 
	public void info(String message, Object... arguments);

	public void trace(String message, Object... arguments);
 
	public void warn(String message, Object... arguments);

	public boolean isDebugEnabled();

	public boolean isErrorEnabled();

	public boolean isInfoEnabled();

	public boolean isTraceEnabled();

	public boolean isWarnEnabled();

}
