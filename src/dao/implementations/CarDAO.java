package dao.implementations;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import model.Car;
import model.enums.CarType;

import org.primefaces.model.SortOrder;

import dao.interfaces.CarDAOInterface;

@Stateless
public class CarDAO extends EntityDAO<Car> implements CarDAOInterface<Car> {

	@Override
	public Class<Car> getEntityClass() {
		return Car.class;
	}

	
	public long count() {
		Query query = em.createNamedQuery("Car.count");
		return (Long) query.getSingleResult();
	}

	public List<Car> findRange(int start, int count, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Car> cq = cb.createQuery(getEntityClass());
		Root<Car> rt = cq.from(getEntityClass());
		cq.select(rt);
		if (!filters.isEmpty()) {
			Predicate predicate = cb.conjunction();
			Iterator<String> it = filters.keySet().iterator();
			while (it.hasNext()) {
				String filterField = it.next();
				String filterValue = filters.get(filterField);
				if (filterField.equals("carType")) {
					predicate = cb.and(predicate, cb.equal(rt.get(filterField),
							CarType.valueOf(filterValue.toUpperCase())));
				} else if (filterField.equals("price")|| filterField.equals("value")) {
					predicate = cb.and(	predicate, cb.equal(rt.get(filterField), Double.valueOf(filterValue)));
				} else {
					predicate = cb.and(	predicate,cb.like(rt.<String> get(filterField), filterValue+ "%"));
				}
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
		Root<Car> rt = cq.from(getEntityClass());
		cq.select(cb.count(rt));
		if (!filters.isEmpty()) {
			Predicate predicate = cb.conjunction();
			Iterator<String> it = filters.keySet().iterator();
			while (it.hasNext()) {
				String filterField = it.next();
				String filterValue = filters.get(filterField);
				if (filterField.equals("carType")) {
					predicate = cb.and(predicate, cb.equal(rt.get(filterField),
							CarType.valueOf(filterValue.toUpperCase())));
			} else if (filterField.equals("price")	|| filterField.equals("value")) {
					predicate = cb.and(	predicate,cb.equal(rt.get(filterField),	Double.valueOf(filterValue)));
				} else {
					predicate = cb.and(	predicate,cb.like(rt.<String> get(filterField), filterValue+ "%"));
				}
			}
			cq.where(predicate);
		}
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}


}
