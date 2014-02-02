package beans.dealer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import model.Dealer;

import common.Actions;

import dao.interfaces.DealerDAOInterface;

@ManagedBean (name = "addDealerBean")
@RequestScoped
public class AddDealerBean {

    @EJB
    private DealerDAOInterface<Dealer> dealerDao;
  
	private Dealer dealer = new Dealer();

       public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer selectedDealer) {
		this.dealer = selectedDealer;
	}

	public String save() {
   		dealerDao.create(dealer);
   		return Actions.DEALER_VIEW.getViewUrl();
   		
   	}
}
