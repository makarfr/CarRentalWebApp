package beans.clients;

import java.io.Serializable;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Client;

import org.primefaces.model.LazyDataModel;

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

}
