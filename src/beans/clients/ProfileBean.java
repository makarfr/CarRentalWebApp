package beans.clients;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import model.Car;
import model.Client;
import model.Contract;
import model.enums.StatusContract;

import common.SessionHelper;

import dao.interfaces.ClientDAOInterface;
import dao.interfaces.ContractDAOInterface;

import static model.enums.StatusContract.*;

@RequestScoped
@ManagedBean
public class ProfileBean {

	@EJB
	private ContractDAOInterface<Contract> contractDao;

	@EJB
	private ClientDAOInterface<Client> clientDao;

	@ManagedProperty("#{clientBean}")
	private ClientBean clientBean;

	// private List<Car> cars = new ArrayList<Car>();

	private List<Contract> contract = new ArrayList<Contract>();
	private List<Contract> contractAccepted = new ArrayList<Contract>();
	private List<Contract> contractNew = new ArrayList<Contract>();
	private List<Contract> contractReject = new ArrayList<Contract>();
	private List<Contract> contractClosed = new ArrayList<Contract>();

	private Car selectedCar;

	private Client selectedClient;

	@PostConstruct
	private void init() {

		Client client = (Client) SessionHelper.getAttribute("client");
		selectedClient = client;

		Collection<Contract> clientContracts = contractDao
				.getClientContractsByRegisterId(client.getRegisterUser()
						.getRegisterId());
		Iterator it = clientContracts.iterator();
		while (it.hasNext()) {
			// Car car = ((Contract) it.next()).getCar();
			Contract contractItem = ((Contract) it.next());
			if (!contract.contains(contractItem)) {
				contract.add(contractItem);
			}
			if (!contractAccepted.contains(contractItem)
					&& (contractItem.getStatus()
							.equals(StatusContract.ACCEPTED))) {
				contractAccepted.add(contractItem);
				Collections.sort(contractNew, getContractCompare());
			}
			if (!contractNew.contains(contractItem)
					&& (contractItem.getStatus().equals(StatusContract.NEW))) {
				contractNew.add(contractItem);
				Collections.sort(contractNew, getContractCompare());
			}
			if (!contractReject.contains(contractItem)
					&& (contractItem.getStatus()
							.equals(StatusContract.REJECTED))) {
				contractReject.add(contractItem);
				Collections.sort(contractReject, getContractCompare());
			}
			if (!contractClosed.contains(contractItem)
					&& (contractItem.getStatus().equals(StatusContract.CLOSED))) {
				contractClosed.add(contractItem);
				Collections.sort(contractClosed, getContractCompare());
			}
		}

	}

	public ClientBean getClientBean() {
		return clientBean;
	}

	public void setClientBean(ClientBean clientBean) {
		this.clientBean = clientBean;
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

	public List<Contract> getContract() {
		return contract;
	}

	public void setContract(List<Contract> contract) {
		this.contract = contract;
	}

	public List<Contract> getContractAccepted() {
		return contractAccepted;
	}

	public void setContractAccepted(List<Contract> contractAccepted) {
		this.contractAccepted = contractAccepted;
	}

	public List<Contract> getContractNew() {
		return contractNew;
	}

	public void setContractNew(List<Contract> contractNew) {
		this.contractNew = contractNew;
	}

	public List<Contract> getContractReject() {
		return contractReject;
	}

	public void setContractReject(List<Contract> contractReject) {
		this.contractReject = contractReject;
	}

	public List<Contract> getContractClosed() {
		return contractClosed;
	}

	public void setContractClosed(List<Contract> contractClosed) {
		this.contractClosed = contractClosed;
	}

	private Comparator<Contract> getContractCompare() {
		Comparator<Contract> comp = new Comparator<Contract>() {

			@Override
			public int compare(Contract o1, Contract o2) {
				if (o1.getContractDateFrom().before(o2.getContractDateFrom())) {
					return 1;
				} else if (o1.getContractDateFrom().after(
						o2.getContractDateFrom())) {
					return -1;
				}
				return 0;
			}
		};
		return comp;
	}

	public void accepted() {
		contract = contractAccepted;
	}

	public void newOrders() {
		contract = contractNew;
	}

	public void closed() {
		contract = contractClosed;
	}

	public void rejected() {
		contract = contractReject;

	}

	public boolean isCarAvailableForRent(StatusContract status) {

		switch (status) {
		case REJECTED:
		case ACCEPTED:
		case NEW:
			return false;
		case CLOSED:
			return true;
		default:
			return false;
		}

	}

}
