package dao.implementations;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import model.CarModel;
import dao.interfaces.CarModelDAOInterface;

@Stateless
public class CarModelDAO extends  EntityDAO<CarModel> implements CarModelDAOInterface<CarModel>{

	@Override
	public CarModel findByModelNameAndDesc(String modelName,
			String modelDescription) {
		System.out.println("car model dao: " + em);
		Query query = em.createNamedQuery("CarModel.findByModelNameAndDesc", CarModel.class);
			query.setParameter("name", modelName);
			query.setParameter("desc", modelDescription);
			 List<CarModel> result1 =  query.getResultList();
			return result1.get(0);
			}

	@Override
	public Class<CarModel> getEntityClass() {
		return CarModel.class;
	}

}
