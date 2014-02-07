package common;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public enum I18nHelper {
	INSTANCE;

	
	public String getI18Message(String key) {
		ResourceBundle text = ResourceBundle.getBundle("i18n/text", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		return text.getString(key);
	}
	
}
