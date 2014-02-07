package dao.implementations;

import javax.ejb.Stateless;
import javax.persistence.Query;

import model.Client;
import model.Dealer;
import dao.interfaces.DealerDAOInterface;
@Stateless
public class DealerDAO extends EntityDAO<Dealer> implements DealerDAOInterface<Dealer> {

	@Override
	public Class<Dealer> getEntityClass() {
		return Dealer.class;
	}

	public Dealer getByUser(long regId) {
		Query query = em.createNamedQuery("Dealer.getByUser", Dealer.class);
		query.setParameter("regId", regId);
	//	List<Client> result =  query.getResultList();
		Object result =  query.getSingleResult();
		return (Dealer) result;
	//	return result.get(0);
	}
}
