package dao.implementations;

import javax.ejb.Stateless;

import model.Contract;
import dao.interfaces.ContractDAOInterface;

@Stateless
public class ContractDAO extends EntityDAO<Contract> implements ContractDAOInterface<Contract> {

	@Override
	public Class<Contract> getEntityClass() {
		return Contract.class;
	}

}
