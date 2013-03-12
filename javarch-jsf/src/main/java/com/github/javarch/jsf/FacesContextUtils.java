package com.github.javarch.jsf;

import javax.faces.context.FacesContext;

public class FacesContextUtils {
	
	/**
	 * Obtém o FacesContext.
	 * 
	 * @return
	 */
	protected FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}

}
