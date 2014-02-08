package beans.dealer;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import model.Dealer;

import common.Actions;
import common.I18nHelper;

import dao.interfaces.DealerDAOInterface;

@ManagedBean (name = "addDealerBean")
@RequestScoped
public class AddDealerBean {
	
	 private String passwordSecondTime;

    @EJB
    private DealerDAOInterface<Dealer> dealerDao;
  
	private Dealer dealer = new Dealer();

       public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer selectedDealer) {
		this.dealer = selectedDealer;
	}
	
	 public String getPasswordSecondTime() {
	        return passwordSecondTime;
	    }

	    public void setPasswordSecondTime(String passwordSecondTime) {
	        this.passwordSecondTime = passwordSecondTime;
	    }

	public String save() {
		 if(dealer.getRegisterUser().getRegisterPassword().equals(passwordSecondTime) == false){
			 FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								I18nHelper.INSTANCE.getI18Message("error"),
								I18nHelper.INSTANCE
								  .getI18Message("confirm_password_didnot_match")));;
	            return null;
	        }
   		dealerDao.create(dealer);
   		return Actions.DEALER_VIEW.getViewUrl();
   		
   	}
}
