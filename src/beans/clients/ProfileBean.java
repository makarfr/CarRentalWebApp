package beans.clients;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import common.SessionHelper;

import model.Car;
import model.Client;
import model.Contract;
import dao.interfaces.ClientDAOInterface;
import dao.interfaces.ContractDAOInterface;

@RequestScoped
@ManagedBean
public class ProfileBean {

    @EJB
    private ContractDAOInterface<Contract> contractDao;

    @EJB
    private ClientDAOInterface<Client> clientDao;

    @ManagedProperty("#{clientBean}")
    private ClientBean clientBean;

    private List<Car> cars = new ArrayList<Car>();

    private Car selectedCar;
    
    private Client selectedClient;

    @PostConstruct
    private void init() {
    //	Client client = new Client();
    	Client client = (Client) SessionHelper.getAttribute("client");
    	selectedClient = client;
    	System.out.println("In clientProfile " +client.getClientName() );
       // Client client = clientDao.find(clientBean.getSelectedClient().getClientId());
        Collection<Contract> clientCars = contractDao.getClientContractsByRegisterId(client.getRegisterUser().getRegisterId());
        Iterator it = clientCars.iterator();
        while (it.hasNext()) {
            Car car = ((Contract) it.next()).getCar();
            if (!cars.contains(car))
                cars.add(car);
        }
    }

 
    public ClientBean getClientBean() {
		return clientBean;
	}


	public void setClientBean(ClientBean clientBean) {
		this.clientBean = clientBean;
	}


	public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }


	public Client getSelectedClient() {
		return selectedClient;
	}


	public void setSelectedClient(Client selectedClient) {
		this.selectedClient = selectedClient;
	}
}
