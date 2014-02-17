package beans.clients;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.Client;
import model.RegisterUser;

import common.Actions;
import common.I18nHelper;

import dao.interfaces.ClientDAOInterface;
import dao.interfaces.RegisterUserDAOInterface;


@ManagedBean(name = "addClientBean")
@ViewScoped
public class AddClientBean {
	
	private Client client;
	@EJB
	private ClientDAOInterface<Client> clientDao;
	@EJB
	private RegisterUserDAOInterface<RegisterUser> registerUserDAO;
	private String passwordSecondTime;
	
	public AddClientBean() {
		client = new Client();
	}

	

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getPasswordSecondTime() {
		return passwordSecondTime;
	}

	public void setPasswordSecondTime(String passwordSecondTime) {
		this.passwordSecondTime = passwordSecondTime;
	}

	public String save() {
		System.out.println("in save method");
	   String registerLogin = client.getRegisterUser().getRegisterLogin();
		if (isLoginUnique(registerLogin)) {
			if(checkPassword() == true) {
			BigDecimal startDisc = new BigDecimal(3);
			client.setClientDiscount(startDisc);
			client.setClientCardNumber(0L);
			clientDao.create(client);
			System.out.println("client created, going to redirect to " + Actions.LOGIN_VIEW.getViewUrl());
			
			return Actions.LOGIN_VIEW.getFullUrl();
			}
		} else {
			System.out.println("in else save throw msg");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							I18nHelper.INSTANCE.getI18Message("error_login"),
							I18nHelper.INSTANCE
							  .getI18Message("error_login_already_exist")));
			//return Actions.REGISTER.getViewUrl();
			return null;
		}
		return null;
		
	}
	
	private boolean isLoginUnique(String userLogin) {
		return registerUserDAO.findByLogin(userLogin) == null;
	}
	
	private boolean checkPassword() {
		 if(client.getRegisterUser().getRegisterPassword().equals(passwordSecondTime) == false){
			 FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								I18nHelper.INSTANCE.getI18Message("error"),
								I18nHelper.INSTANCE
								  .getI18Message("confirm_password_didnot_match")));;
	            return  false;
		 	}
		return true;
	}
	
	
}
