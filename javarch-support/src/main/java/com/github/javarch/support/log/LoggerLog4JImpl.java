package com	.github.javarch.support.log;

import org.apache.log4j.Level;

public class LoggerLog4JImpl implements Logger {

	// Private Data Members
	private org.apache.log4j.Logger wrappedLogger;

	public LoggerLog4JImpl(String className) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void debug(String message) {

		if (isDebugEnabled()) {
		
			try {
				wrappedLogger.log( Level.DEBUG, message );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void debug(String message, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(Throwable throwable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String message, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(String message, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(String message, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(String message, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDebugEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isErrorEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInfoEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTraceEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWarnEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
