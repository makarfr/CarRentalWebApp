package beans.dealer;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import common.Actions;

import model.Client;
import model.Dealer;
import dao.interfaces.DealerDAOInterface;

@ManagedBean(name = "dealerBean")
@SessionScoped
public class DealerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Dealer dealer;
	@EJB
	private DealerDAOInterface<Dealer> dealDAO;

	public DealerBean() {
		instantiateDealer();
	}

	private void instantiateDealer() {
		dealer = new Dealer();

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
}
