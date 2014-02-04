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
			System.out.println("in find by login block try");
			if (query.getResultList() != null && !query.getResultList().isEmpty()){
				RegisterUser result =  (RegisterUser) query.getResultList().get(0);
				System.out.println("in RegisterUserDAO findByLogin " + result);
				return result;
			}else {
				System.out.println("in RegisterUserDAO findByLogin : null");
				return null;
			}

		} catch(javax.persistence.NoResultException e){       // Exception shows there is nothing in DB :\
			return null;
		}

	}

}
