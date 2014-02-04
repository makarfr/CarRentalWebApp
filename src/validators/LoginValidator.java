package validators;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import model.RegisterUser;

import common.I18nHelper;
import dao.interfaces.RegisterUserDAOInterface;

@FacesValidator("loginValidator")
public class LoginValidator implements Validator {

	@EJB
	private RegisterUserDAOInterface<RegisterUser> regUserDao;

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent,
			Object o) throws ValidatorException {

		// UIInput fromLoginComponent = (UIInput)
		// uiComponent.getAttributes().get("login");
		System.out.println(" something in login validator");
		String userLogin = o.toString();
		System.out.println("in Validator user login " + userLogin);

		// (String) fromLoginComponent.getValue();
		RegisterUser reg = null;
		try {
			reg = regUserDao.findByLogin(userLogin);

		} catch (NullPointerException e) {
			System.out.println("in Null Throw");
			
		}

		if (reg.getRegisterId() != null) {
			makeErrorMessage(I18nHelper.INSTANCE
					.getI18Message("error_login_already_exist"));
			
		}

	}

	private static void makeErrorMessage(String message) {
		FacesMessage msg = new FacesMessage(null, I18nHelper.INSTANCE
				.getI18Message("error_login_already_exist"));
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
	}

}