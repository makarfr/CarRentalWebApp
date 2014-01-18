package dao.implementations;

import javax.ejb.Stateless;

import model.Dealer;
import dao.interfaces.DealerDAOInterface;
@Stateless
public class DealerDAO extends EntityDAO<Dealer> implements DealerDAOInterface<Dealer> {

	@Override
	public Class<Dealer> getEntityClass() {
		return Dealer.class;
	}

}
