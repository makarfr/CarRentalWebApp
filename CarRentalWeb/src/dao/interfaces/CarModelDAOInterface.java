package dao.interfaces;

import javax.ejb.Local;

import model.CarModel;

@Local
public interface CarModelDAOInterface<T> extends EntityDAOInterface<T> {

	CarModel findByModelNameAndDesc(String modelName, String modelDescription);

	
}
