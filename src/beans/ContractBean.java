package beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Contract;
import dao.interfaces.ContractDAOInterface;

@ManagedBean(name = "contractBean")
@SessionScoped
public class ContractBean implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private Contract contract;
	@EJB
	private ContractDAOInterface<Contract> contractDAO;
	
	
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	public List<Contract> getContractList() {
		List<Contract> list = contractDAO.findAll();
		return list;
	}
}
