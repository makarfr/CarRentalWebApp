package dao.implementations;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import model.Client;

import org.primefaces.model.SortOrder;

import dao.interfaces.ClientDAOInterface;


@Stateless
public class ClientDAO extends EntityDAO<Client> implements ClientDAOInterface<Client> {

	public ClientDAO(){}


	@Override
	public Class<Client> getEntityClass() {
		return Client.class;
	}

	public List<Client> getByNameSurname(String nameClient,String surnameClient){
		System.out.println("ClientDAO: getByNameSurname" + em);
		Query query = em.createNamedQuery("Client.getByNameSurname", Client.class);
		query.setParameter("name",nameClient);
		query.setParameter("surname",surnameClient);
		List<Client> result =  query.getResultList();
		return result;

	}
	
	public Client getByUser(long regId) {
		Query query = em.createNamedQuery("Client.getByUser", Client.class);
		query.setParameter("regId", regId);
		List<Client> result =  query.getResultList();
	//	Object result =  query.getSingleResult();
	//	return (Client) result;
		return result.get(0);
	}

	public List<Client> findAllFromDAO(){
		Query query = em.createNamedQuery("Client.findAll", Client.class);
		List<Client> result =  query.getResultList();
		return result;

	}


	@Override
	public Client getByCardNumber(long cardNumber) {
		Query query = em.createNamedQuery("Client.getByClientdNumber", Client.class);
		query.setParameter("cardNumber", cardNumber);
		Object result =  query.getSingleResult();
		return (Client) result;
	}

	@Override
	public List<Client> findRange(int start, int count, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {

		System.out.println("ClientDAO: findRange" + em);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Client> cq = cb.createQuery(getEntityClass());
		Root<Client> rt = cq.from(getEntityClass());
		cq.select(rt);
		if (!filters.isEmpty()) {
			Predicate predicate = cb.conjunction();
			Iterator<String> it = filters.keySet().iterator();
			while (it.hasNext()) {
				String filterField = it.next();
				String filterValue = filters.get(filterField);
				predicate = cb.and(	predicate,cb.like(rt.<String> get(filterField), filterValue+ "%"));
			}
			cq.where(predicate);
		}

		if (sortField != null) {
			if (sortOrder == SortOrder.ASCENDING) {
				cq.orderBy(cb.asc(rt.get(sortField)));
			} else if (sortOrder == SortOrder.DESCENDING) {
				cq.orderBy(cb.desc(rt.get(sortField)));
			}
		}

		Query q = em.createQuery(cq);
		q.setMaxResults(count);
		q.setFirstResult(start);
		return q.getResultList();
	}

	public int count(Map<String, String> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery(getEntityClass());
		Root<Client> rt = cq.from(getEntityClass());
		cq.select(cb.count(rt));
		if (!filters.isEmpty()) {
			Predicate predicate = cb.conjunction();
			Iterator<String> it = filters.keySet().iterator();
			while (it.hasNext()) {
				String filterField = it.next();
				String filterValue = filters.get(filterField);
				predicate = cb.and(	predicate,cb.like(rt.<String> get(filterField), filterValue+ "%"));
			}
			cq.where(predicate);
		}
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	@Override
	public List<Client> findAll(int start,int end){
		Query query = em.createNamedQuery("Client.findAllLazy",Client.class);
		query.setMaxResults(end - start);
		query.setFirstResult(start);
		return query.getResultList();
	}


	@Override
	public long count() {
		Query query = em.createNamedQuery("Client.count");
		return (Long) query.getSingleResult();
	}

}