package com.github.javarch.jsf.context;

public interface RequestHelper {

	/**
	* Returns the value of the request attribute associated with the specified name.
	*/
	public abstract Object getRequestAttribute(String name);
	
	/**
	* Sets the value of the a request attribute using the specified name and value.
	*/
	public abstract void setRequestAttribute(String name, Object value);
	
	/**
	* @return the request context path. {@link FacesContext#getExternalContext()} {@link
	* ExternalContext#getRequestContextPath()}
	*/
	public abstract String getRequestContextPath();
	
	/**
	* Retrieves the specified parameter passed as part of the request
	*/
	public abstract String getRequestParameter(String name);
	
	/**
	* Retrieves the specified parameter passed as part of the request as a boolean. The values "yes", "true", "y", and
	* "1" are accetable values for "TRUE".
	*/
	public abstract boolean getRequestParameterAsBool(String name, boolean defaultValue);
	
	/**
	* Retrieves the specified parameter passed as part of the request as an integer.
	*/
	public abstract int getRequestParameterAsInt(String name, int defaultValue);
	
	/**
	* Retrieves the specified parameter passed as part of the request as an integer.
	*/
	public abstract long getRequestParameterAsLong(String name, long defaultValue);
	
	/**
	* Retrieves the specified parameter from the ExternalContext's request parameter map.
	*/
	public abstract String getRequestParameterFromMap(String name);
}
