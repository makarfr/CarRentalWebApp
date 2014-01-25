package beans.clients;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Client;
import model.RegisterUser;

import org.primefaces.model.LazyDataModel;

import common.Actions;

import dao.implementations.RegisterUserDAO;
import dao.interfaces.ClientDAOInterface;
import dao.interfaces.RegisterUserDAOInterface;

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
	
	
	public String save() {
		 BigDecimal startDisc = new BigDecimal(3);
		 selectedClient.setClientDiscount(startDisc);
	        clientDao.create(selectedClient);
	        return "clientView";
	    }
	
	public String update() {
		clientDao.update(selectedClient);
		return Actions.CLIENTS_VIEW.getViewUrl();
	}

}
