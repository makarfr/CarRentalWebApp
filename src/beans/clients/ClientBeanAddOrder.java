package beans.clients;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import model.Client;

import common.SessionHelper;

@ManagedBean(name = "clientBeanOrder")
@ViewScoped
public class ClientBeanAddOrder implements Serializable{


	private static final long serialVersionUID = 1L;
	private Client selectedClient;
	
	
	public ClientBeanAddOrder() {
		selectedClient = (Client) SessionHelper.getAttribute("client");
    }

  

	public Client getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(Client selectedClient) {
		this.selectedClient = selectedClient;
	}

	
	
}
