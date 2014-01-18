package dao.interfaces;

import javax.ejb.Local;

import model.RegisterUser;

@Local
public interface RegisterUserDAOInterface<T> extends EntityDAOInterface<T> {

	RegisterUser findByLogin(String name);

}
