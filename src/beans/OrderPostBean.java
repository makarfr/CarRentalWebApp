package beans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import model.Contract;
import model.RegisterUser;
import model.enums.StatusContract;
import beans.cars.SelectedCarBean;
import beans.clients.ClientBeanAddOrder;
import beans.notification.OrderDetailsSender;

import common.SessionHelper;

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
    
    private OrderDetailsSender orderDetailsSender = new OrderDetailsSender();
    
    public void precalculate() {
        contract.setCar(selectedCarBean.getCar());
        RegisterUser reg = new RegisterUser();
        reg.setRegisterId((Long) SessionHelper.getAttribute("regId"));
        System.out.println("RegisterID in OrderBean " + reg.getRegisterId());
        contract.setRegisterUser(reg);
      
    /*    del.setDealerId((Long) SessionHelper.getAttribute("id"));
        contract.setDealer(del);*/
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
        
        orderDetailsSender.send(contract);
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
        rentDays = numOfDays + 1;
    	/*rentDays = Days.daysBetween(new DateTime(dateFrom), new DateTime(dateTo)).getDays();
    	System.out.println("Rent days is " + rentDays);*/
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

	public OrderDetailsSender getOrderDetailsSender() {
		return orderDetailsSender;
	}

	public void setOrderDetailsSender(OrderDetailsSender orderDetailsSender) {
		this.orderDetailsSender = orderDetailsSender;
	}
}
