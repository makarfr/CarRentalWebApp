package beans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import org.hibernate.Session;

import common.SessionHelper;

import model.Contract;
import model.Dealer;
import model.RegisterUser;
import model.enums.StatusContract;
import beans.cars.SelectedCarBean;
import beans.clients.ClientBean;
import beans.clients.ClientBeanAddOrder;
import dao.interfaces.ContractDAOInterface;

@ManagedBean
@RequestScoped
public class OrderPostBean {

    @EJB
    private ContractDAOInterface<Contract> contractDAO;

    private RegisterUser registerUser;
    
    @ManagedProperty(value = "#{clientBeanOrder}")
    private ClientBeanAddOrder clientBeanOrder;
    
  @ManagedProperty(value = "#{selectedCarBean}")
    private SelectedCarBean selectedCarBean;

    private int rentDays;

    private Contract contract = new Contract();
    private DecimalFormat df=new DecimalFormat("#.##");
    
    
    public void precalculate() {
        contract.setCar(selectedCarBean.getCar());
        RegisterUser reg = new RegisterUser();
        reg.setRegisterId((Long) SessionHelper.getAttribute("regId"));
        System.out.println("RegisterID in OrderBean " + reg.getRegisterId());
        contract.setRegisterUser(reg);
        Dealer del = null;// = new Dealer();
        del.setDealerId((Long) SessionHelper.getAttribute("id"));
        contract.setDealer(del);
        contract.setStatus(StatusContract.NEW);
        

        if (clientBeanOrder.getSelectedClient().getClientDiscount().compareTo(new BigDecimal(0)) > 0) {
        	BigDecimal hundred = new BigDecimal(100);
        	
        	double discount = clientBeanOrder.getSelectedClient().getClientDiscount().divide(hundred).doubleValue();
           	BigDecimal percent = new BigDecimal(discount);
        	
    	System.out.println(" ClientBeanOrder disc : " + discount);
    	
    	System.out.println("Discount percent : " + percent.setScale(2, RoundingMode.CEILING));
            contract.setTotalPrice(getTotalSum().subtract(getTotalSum().multiply(percent).setScale(2, RoundingMode.CEILING)));
    	System.out.println("Price counting  percent : " + getTotalSum().subtract(getTotalSum().multiply(percent).setScale(2, RoundingMode.CEILING)));
        } else
            contract.setTotalPrice(getTotalSum());
    }

    public String postOrder() {
        precalculate();
        contractDAO.create(contract);
          return "viewOrder";
    }

    public BigDecimal getTotalSum() {
    	BigDecimal daysRent = new BigDecimal(calculateNumberOfDaysBetween(contract.getContractDateFrom(), contract.getContractDateTo()));
    	  System.out.println(selectedCarBean.getCar().getCarPrice() + " getTotalSum");
    	BigDecimal sum = selectedCarBean.getCar().getCarPrice().multiply(daysRent);
        return sum;
    }

    private int calculateNumberOfDaysBetween(Date dateFrom, Date dateTo) {
        long startDateTime = dateFrom.getTime();
        long endDateTime = dateTo.getTime();
        long secondsPerDay = 1000 * 60 * 60 * 24;
        int numOfDays = (int) ((endDateTime - startDateTime) / secondsPerDay);
        System.out.println(numOfDays + "Days");
        rentDays = numOfDays + 1;
        return rentDays;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public RegisterUser getRegisterUser() {
        return registerUser;
    }

    public void setRegisterUser(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }

    public SelectedCarBean getSelectedCarBean() {
        return selectedCarBean;
    }

    public void setSelectedCarBean(SelectedCarBean selectedCarBean) {
        this.selectedCarBean = selectedCarBean;
    }
    
    public ClientBeanAddOrder getActiveClient() {
		return clientBeanOrder;
	}

	public void setClientBeanOrder(ClientBeanAddOrder clientBeanOrder) {
		this.clientBeanOrder = clientBeanOrder;
	}

    public int getRentDays() {
        return rentDays;
    }

    public void setRentDays(int rentDays) {
        this.rentDays = rentDays;
    }
}
