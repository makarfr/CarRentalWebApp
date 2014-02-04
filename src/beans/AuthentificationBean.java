package beans;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import common.Actions;
import common.I18nHelper;
import common.SessionHelper;
import common.UserRole;

import model.Client;
import model.Dealer;
import model.RegisterUser;
import dao.interfaces.ClientDAOInterface;
import dao.interfaces.DealerDAOInterface;
import dao.interfaces.RegisterUserDAOInterface;

@ManagedBean(name = "login")
@SessionScoped
public class AuthentificationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private Boolean isLogged = Boolean.FALSE;
	private Client client;
	@EJB
	private RegisterUserDAOInterface<RegisterUser> dao;
	@EJB
	private ClientDAOInterface<Client> clientDao;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean logged) {
		isLogged = logged;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AuthentificationBean(String password, String name) {
		this.password = password;
		this.name = name;
	}

	public AuthentificationBean() {
	}

	public String login() {

		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		System.out.println("I am logging in :(");

		try {
			// password = UtilityMethods.md5(password);

			request.login(name, password);
			RegisterUser user = dao.findByLogin(name);
			System.out.println("RegisterUser Role : " + user.getRole().getRoleName());
			SessionHelper.putAtrributeToSession("regId", user.getRegisterId());
			SessionHelper.putAtrributeToSession("userLogin", user.getRegisterLogin());
		

			if (request.isUserInRole(UserRole.CLIENT.name().toLowerCase())) {
				System.out.println(" Role is client ");
				this.client = clientDao.getByUser(user.getRegisterId());
				SessionHelper.putAtrributeToSession("id", client.getClientId());
				SessionHelper.putAtrributeToSession("client", client);
				
				isLogged = true;
				return Actions.CARS_VIEW.getFullUrl();
			} else if (request.isUserInRole(UserRole.ADMIN.name().toLowerCase())) {
				System.out.println(" Role is admin ");
				isLogged = true;
				return Actions.DEALER_VIEW.getFullUrl();
			} else {
				System.out.println(" Role is dealer ");
				isLogged = true;
				return Actions.CONTRACTS_VIEW.getFullUrl();
			}

		} catch (ServletException e1) {
			// UtilityMethods.facesMessage("Authentification has failed");
			// UtilityMethods.logSevere(e1);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							I18nHelper.INSTANCE.getI18Message("error_login"),
							I18nHelper.INSTANCE
									.getI18Message("error_login_message")));
			return null;
		}
	}

	public String logout() {
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		((HttpSession) ec.getSession(false)).invalidate();
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		isLogged = false;
		client = null;
		return Actions.LOGIN_VIEW.getFullUrl();

	}

}
