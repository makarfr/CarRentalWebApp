package beans.dealer;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.CarModel;
import model.Contract;
import model.Dealer;

import org.primefaces.model.LazyDataModel;

import common.Actions;
import convertors.CarModelConverter;

import dao.interfaces.CarModelDAOInterface;
import dao.interfaces.ContractDAOInterface;
import dao.interfaces.EntityDAOInterface;

@ManagedBean(name = "contractBean")
@SessionScoped
public class ContractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Dealer dealer;
	private Contract contract;
	@EJB
	private ContractDAOInterface<Contract> contractDAO;
	private LazyDataModel<Contract> lazyModel;
	@EJB
	private CarModelDAOInterface<CarModel> carModelDao;

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
	
	public String getCarModelName(){
		List<CarModel> list = new LinkedList<CarModel>();
		CarModel model = carModelDao.find(contract.getCarId());
		list.add(model);
		CarModelConverter.getList(list);
		return list.get(0).getCarModelName();
	}

	
	public List<Contract> getContractList() {
		List<Contract> list = contractDAO.findAll();
		return list;
	}

	public String update() {
		contractDAO.update(contract);
		return Actions.CONTRACTS_VIEW.getViewUrl();
	}

}
