package beans;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("localeBean")
@SessionScoped
public class LoñalBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Locale locale;

	@PostConstruct
	public void init() {
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}
	
	public LoñalBean() {
		//locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}

	public Locale getLocale() {
		return locale;
	}

	public String getLanguage() {
		return locale.getLanguage();
	}

	public void setLanguage(String language) {
		locale = new Locale(language);
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		// this.locale = new Locale(language);
	}

}
