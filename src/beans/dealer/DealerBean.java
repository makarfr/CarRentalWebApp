package beans.dealer;

import java.io.Serializable;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Contract;
import model.Dealer;

import org.primefaces.model.LazyDataModel;

import common.Actions;

import dao.interfaces.ContractDAOInterface;

@ManagedBean(name = "dealerBean")
@SessionScoped
public class DealerBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Dealer dealer;
	private Contract contract;
	@EJB
	private ContractDAOInterface<Contract> contractDAO;
	private LazyDataModel<Contract> lazyModel;

	@PostConstruct
	public void init() {
		lazyModel = new ContractLazyDataModel(new LinkedList<Contract>(),
				contractDAO);
	}

	public DealerBean() {
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

	public String update() {
		contractDAO.update(contract);
		return Actions.CONTRACTS_VIEW.getViewUrl();
	}

}
