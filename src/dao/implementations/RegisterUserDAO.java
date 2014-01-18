package dao.implementations;

import javax.ejb.Stateless;
import javax.persistence.Query;

import model.RegisterUser;
import dao.interfaces.RegisterUserDAOInterface;
@Stateless
public class RegisterUserDAO extends EntityDAO<RegisterUser> implements
		RegisterUserDAOInterface<RegisterUser> {

	@Override
	public Class<RegisterUser> getEntityClass() {
		return RegisterUser.class;
	}

	@Override
	public RegisterUser findByLogin(String name) {
		Query query = em.createNamedQuery("RegisterUser.findByLogin", RegisterUser.class);
		query.setParameter("login", name);
		 try{
	            RegisterUser result =  (RegisterUser) query.getResultList().get(0);
	            return result;
	        } catch(javax.persistence.NoResultException e){       // Exception shows there is nothing in DB :\
	            return null;
	        }
	}

}
