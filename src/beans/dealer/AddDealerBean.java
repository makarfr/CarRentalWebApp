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
  
	private Dealer selectedDealer = new Dealer();

       public Dealer getSelectedDealer() {
		return selectedDealer;
	}

	public void setSelectedDealer(Dealer selectedDealer) {
		this.selectedDealer = selectedDealer;
	}

	public String save() {
   		dealerDao.create(selectedDealer);
   		return Actions.DEALER_VIEW.getViewUrl();
   		
   	}
}
