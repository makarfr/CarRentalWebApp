package dao.implementations;

import javax.ejb.Stateless;

import model.CarStatus;
import dao.interfaces.CarStatusDAOInterface;
@Stateless
public class CarStatusDAO extends EntityDAO<CarStatus> implements CarStatusDAOInterface<CarStatus> {

	@Override
	public Class<CarStatus> getEntityClass() {
		return CarStatus.class;
	}

}
