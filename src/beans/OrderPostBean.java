package beans;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import model.Contract;
import model.Dealer;
import model.RegisterUser;
import model.enums.StatusContract;
import beans.cars.SelectedCarBean;
import beans.clients.ClientBean;
import dao.interfaces.ContractDAOInterface;

@ManagedBean
@RequestScoped
public class OrderPostBean {

    @EJB
    private ContractDAOInterface<Contract> contractDAO;

    private RegisterUser registerUser;
    
    @ManagedProperty(value = "#{clientBean}")
    private ClientBean clientBean;
    
 

  @ManagedProperty(value = "#{selectedCarBean}")
    private SelectedCarBean selectedCarBean;

    private int rentDays;

    private Contract contract = new Contract();

    public void precalculate() {
        contract.setCar(selectedCarBean.getCar());
        RegisterUser reg = new RegisterUser();
        reg.setRegisterId(2);
        contract.setRegisterUser(reg);
        Dealer del = new Dealer();
        del.setDealerId(2);
        contract.setDealer(del);
        contract.setStatus(StatusContract.NEW);
   System.out.println(clientBean);
   System.out.println(clientBean.getSelectedClient());
        if (clientBean.getSelectedClient().getClientDiscount() > 0) {
        	BigDecimal percent = new BigDecimal(clientBean.getSelectedClient().getClientDiscount() / 100);
            contract.setTotalPrice(getTotalSum().subtract(getTotalSum().multiply(percent)));
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
    
    public ClientBean getClientBean() {
		return clientBean;
	}

	public void setClientBean(ClientBean clientBean) {
		this.clientBean = clientBean;
	}

    public int getRentDays() {
        return rentDays;
    }

    public void setRentDays(int rentDays) {
        this.rentDays = rentDays;
    }
}
