package beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Dealer;
import dao.implementations.DealerDAO;

@ManagedBean(name = "dealerBean")
@SessionScoped
public class DealerBean implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private Dealer dealer;
	private DealerDAO dealDAO;
	
	
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
}
