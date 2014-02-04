package beans.clients;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import model.Client;

import org.primefaces.model.LazyDataModel;

import common.Actions;
import common.I18nHelper;

import dao.interfaces.ClientDAOInterface;

@ManagedBean(name = "clientBean")
@SessionScoped
public class ClientBean implements Serializable{


	private static final long serialVersionUID = 1L;
	private Client selectedClient;
	@EJB
	private ClientDAOInterface<Client> clientDao;
	private LazyDataModel<Client> lazyModel;

	@PostConstruct
	public void init(){
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

	public void setSelectedClient(Client client) {
		this.selectedClient = client;
	}


	public String save(ActionEvent actionEvent) {
		System.out.println("In save method start");
		
		BigDecimal startDisc = new BigDecimal(3);
		selectedClient.setClientDiscount(startDisc);
		selectedClient.setClientCardNumber(0L);
		clientDao.create(selectedClient);
		return "login";
	}

	public String update() {
		clientDao.update(selectedClient);
		return Actions.CLIENTS_VIEW.getViewUrl();
	}

}
