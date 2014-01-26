package beans.dealer;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.LazyDataModel;

import common.Actions;

import model.Client;
import model.Contract;
import model.Dealer;
import dao.interfaces.DealerDAOInterface;

@ManagedBean(name = "dealerBean")
@SessionScoped
public class DealerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Dealer dealer;
	@EJB
	private DealerDAOInterface<Dealer> dealDAO;
	private LazyDataModel<Dealer> lazyModel;

	@PostConstruct
	public void init() {
		lazyModel = new DealerLazyDataModel(new LinkedList<Dealer>(),dealDAO);
	}
	
	public DealerBean() {
		instantiateDealer();
	}

	private void instantiateDealer() {
		dealer = new Dealer();

	}

	public LazyDataModel<Dealer> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Dealer> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public List<Dealer> getDealersList() {
		List<Dealer> list = dealDAO.findAll();
		return list;
	}
	public String save() {
		dealDAO.create(dealer);
		return Actions.DEALER_VIEW.getViewUrl();
		
	}
	
	public String update() {
		dealDAO.update(dealer);
		return Actions.DEALER_VIEW.getViewUrl();
	}

}
