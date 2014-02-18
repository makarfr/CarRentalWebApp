package beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "localeBean")
@SessionScoped
public class LoñaleBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String localeCode;
	private Locale locale;

	private Map<String, Locale> languages = new LinkedHashMap<String, Locale>(2) {
		{
			put("English", Locale.ENGLISH);
			put("Ðóññêèé", new Locale("ru"));
		}
	};

	public LoñaleBean() {
		Application app = FacesContext.getCurrentInstance().getApplication();
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		localeCode = app.getDefaultLocale().getLanguage();
		
	}

	public Map<String, Locale> getLanguages() {
		return languages;
	}

	public void setLanguages(Map<String, Locale> languages) {
		this.languages = languages;
	}

	public String getLocaleCode() {
		return localeCode;
	}


	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	//value change event listener
	public void languageLocaleCodeChanged(ValueChangeEvent e){
		String newLocaleValue = e.getNewValue().toString();

		for (Map.Entry<String, Locale> entry : languages.entrySet()) {
			if (entry.getValue().getLanguage().equalsIgnoreCase(newLocaleValue)) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale(entry.getValue());
				System.out.println("new locale: " + FacesContext.getCurrentInstance().getViewRoot().getLocale());				
			}
		}
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}


}
