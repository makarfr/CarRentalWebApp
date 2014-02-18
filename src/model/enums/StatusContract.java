package model.enums;

import java.util.Locale;
import java.util.ResourceBundle;


public enum StatusContract {
	CLOSED, NEW, ACCEPTED, REJECTED;

	public String toLocalizedString(Locale locale) {
		System.out.println("in toLocalizedString + locale " + locale);
		ResourceBundle text = ResourceBundle.getBundle(
				"i18n.text",
				locale);
		System.out.println("enum string " +  text.getString(this.toString()));
		return text.getString(this.toString());
	}
}
