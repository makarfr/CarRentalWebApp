package beans.dealer;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import model.CarModel;
import model.Client;
import model.Contract;
import model.Dealer;
import model.enums.StatusContract;

import org.primefaces.model.LazyDataModel;

import common.Actions;
import common.SessionHelper;
import convertors.CarModelConverter;

import dao.interfaces.CarModelDAOInterface;
import dao.interfaces.ClientDAOInterface;
import dao.interfaces.ContractDAOInterface;
import dao.interfaces.DealerDAOInterface;
import dao.interfaces.EntityDAOInterface;

@ManagedBean(name = "contractBean")
@ViewScoped
public class ContractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Dealer dealer;
	private Contract contract;
	@EJB
	private ContractDAOInterface<Contract> contractDAO;
	private LazyDataModel<Contract> lazyModel;
	@EJB
	private CarModelDAOInterface<CarModel> carModelDao;
	@EJB
	private ClientDAOInterface<Client> clientDao;
	private Client client;
	
	@PostConstruct
	public void init() {
		lazyModel = new ContractLazyDataModel(new LinkedList<Contract>(),
				contractDAO);
	}

	public ContractBean() {
		instantiate();
	}

	private void instantiate() {
		dealer = new Dealer();
		contract = new Contract();
		client = new Client();
	
	}

	

	public LazyDataModel<Contract> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Contract> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	public String getConvertedCarModelName(Long carID){
		List<CarModel> list = new LinkedList<CarModel>();
		CarModel model = carModelDao.find(carID);
		list.add(model);
		CarModelConverter.getList(list);
		if(list.isEmpty()) {
			return "NOT_DEFINED";
		} else {
		return list.get(0).getCarModelName();
		}
	}
	
	public List<Contract> getContractList() {
		List<Contract> list = contractDAO.findAll();
		return list;
	}

	public Client getClient() {
		System.out.println("getClient in contractBean " );
		return client;
	}

	public void setClient(Client client) {
		System.out.println("setClient in contractBean " + getContract().getRegisterUser().getRegisterId());
	//	client = clientDao.getByUser( contract.getRegisterUser().getRegisterId());
		this.client = client;
	}

	public String update() {
		contractDAO.update(contract);
		return Actions.CONTRACTS_VIEW.getViewUrl();
	}
	
	public String accept() {
		System.out.println(contract.getRegisterUser().getRegisterId());
		contract.setDealer(getDealerForActions());
		contract.setStatus(StatusContract.ACCEPTED);
		contractDAO.update(contract);
		return Actions.CONTRACTS_VIEW.getViewUrl();
	}
	
	public String reject() {
		contract.setDealer(getDealerForActions());
		contract.setStatus(StatusContract.REJECTED);
		contractDAO.update(contract);
		return Actions.CONTRACTS_VIEW.getViewUrl();
	}
	
	public String close() {
		contract.setDealer(getDealerForActions());
		contract.setStatus(StatusContract.CLOSED);
		contractDAO.update(contract);
		return Actions.CONTRACTS_VIEW.getViewUrl();
	}
	
	private Dealer getDealerForActions(){
		dealer = (Dealer) SessionHelper.getAttribute("dealer");
		System.out.println(" getDealerForActions " + dealer.getDealerName());
		return dealer;
	}

	/*public String showAcceptedOrders() {
		contract.setDealer(getDealerForActions());
		contract.setStatus(StatusContract.CLOSED);
		contractDAO.update(contract);
		return Actions.CONTRACTS_VIEW.getViewUrl();
	}*/
}
