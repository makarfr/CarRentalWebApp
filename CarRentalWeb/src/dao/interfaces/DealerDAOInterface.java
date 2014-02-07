package dao.interfaces;

import javax.ejb.Local;

import model.Dealer;

@Local
public interface DealerDAOInterface<T> extends EntityDAOInterface<T> {

	Dealer getByUser(long regId);
}
