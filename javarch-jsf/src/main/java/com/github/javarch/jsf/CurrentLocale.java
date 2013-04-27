package com.github.javarch.jsf;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javarch.jsf.context.FacesContextUtils;

@Component
public class CurrentLocale {
	
	@Autowired
	private FacesContextUtils context;

	public Locale getLocale(){
		return context.getFacesContext().getViewRoot().getLocale();
	}
}
