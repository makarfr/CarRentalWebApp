package beans.clients;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import model.Client;
import model.RegisterUser;

import org.primefaces.model.LazyDataModel;

import common.Actions;
import common.I18nHelper;

import dao.interfaces.ClientDAOInterface;
import dao.interfaces.RegisterUserDAOInterface;

@ManagedBean(name = "clientBean")
@ViewScoped
public class ClientBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Client selectedClient;
	@EJB
	private ClientDAOInterface<Client> clientDao;
	private LazyDataModel<Client> lazyModel;

	@EJB
	private RegisterUserDAOInterface<RegisterUser> registerUserDAO;
	private String passwordSecondTime;

	@PostConstruct
	public void init() {
		lazyModel = new ClientLazyDataModel(new LinkedList<Client>(), clientDao);
	}

	public ClientBean() {
		instantiateClient();
	}

	private void instantiateClient() {
		selectedClient = new Client();

	}

	public LazyDataModel<Client> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Client> lazyModel) {

		this.lazyModel = lazyModel;
	}

	public Client getSelectedClient() {
		return selectedClient;
	}

	public String getPasswordSecondTime() {
		return passwordSecondTime;
	}

	public void setPasswordSecondTime(String passwordSecondTime) {
		this.passwordSecondTime = passwordSecondTime;
	}

	public void setSelectedClient(Client client) {
		this.selectedClient = client;
	}
	

	public String save() {
		System.out.println("in save method");
	   String registerLogin = selectedClient.getRegisterUser().getRegisterLogin();
		if (isLoginUnique(registerLogin)) {
			if(checkPassword() == true) {
			BigDecimal startDisc = new BigDecimal(3);
			selectedClient.setClientDiscount(startDisc);
			selectedClient.setClientCardNumber(0L);
			clientDao.create(selectedClient);
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

	public String update() {
		clientDao.update(selectedClient);
		return Actions.CLIENTS_VIEW.getViewUrl();
	}

	private boolean isLoginUnique(String userLogin) {
		return registerUserDAO.findByLogin(userLogin) == null;
	}
	
	private boolean checkPassword() {
		 if(selectedClient.getRegisterUser().getRegisterPassword().equals(passwordSecondTime) == false){
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
