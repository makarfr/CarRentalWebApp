package common;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionHelper {

	public static <T> void putAtrributeToSession(String property, Object value) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(
				true);
		session.setAttribute(property, value);
	}

	public static Object getAttribute(String property) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(
				true);
		return session.getAttribute(property);
	}
}
