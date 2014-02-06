package dao.implementations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import model.Car;
import model.CarModel;
import model.enums.CarType;

import org.primefaces.model.SortOrder;

import convertors.CarModelConverter;

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

	@Override
	public List<Car> findRange(int start, int count, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Car> cq = cb.createQuery(getEntityClass());
		Root<Car> rt = cq.from(getEntityClass());
		Metamodel m = em.getMetamodel();
		EntityType<Car> Car_ = m.entity(Car.class);
		cq.select(rt);

		if (!filters.isEmpty()) {
			Predicate predicate = cb.conjunction();
			Iterator<String> it = filters.keySet().iterator();
			while (it.hasNext()) {
				String filterField = it.next();
				String filterValue = filters.get(filterField);
				System.out.println("In find Range CarDAO filterField "
						+ filterField + "; filterValue : " + filterValue);
				if (filterField.equals("carType")) {
					predicate = cb
							.and(predicate, cb.equal(rt.get(filterField),
									CarType.valueOf(filterValue.toUpperCase())));
				} else if (filterField.equals("carPrice")) {
					System.out.println("In find Range CarDAO price else  "
							+ filterField + "; filterValue : " + filterValue);
					BigDecimal myBigD = new BigDecimal(filterValue);
					BigDecimal error = new BigDecimal("0.001");
					Path<BigDecimal> value = rt.<BigDecimal> get("carPrice");
					predicate = cb.and(
							predicate,
							cb.between(value, myBigD.subtract(error),
									myBigD.add(error)));
				} else if (filterField.equals("carYear")) {
					System.out.println("In find Range CarDAO carYear else  "
							+ filterField + "; filterValue : " + filterValue);
					Integer candidate = Integer.parseInt(filterValue);
					Expression<Integer> year = cb.function("year",
							Integer.class, rt.get(filterField));
					predicate = cb.and(predicate, cb.equal(year, candidate));
				} else {
					predicate = cb.and(
							predicate,
							cb.like(rt.<String> get(filterField), "%"
									+ filterValue + "%"));
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
					predicate = cb
							.and(predicate, cb.equal(rt.get(filterField),
									CarType.valueOf(filterValue.toUpperCase())));
				} else if (filterField.equals("carPrice")) {
					BigDecimal myBigD = new BigDecimal(filterValue);
					predicate = cb.and(predicate,
							cb.equal(rt.get(filterField), myBigD));
				} else if (filterField.equals("carYear")) {
					System.out.println("In count Range CarDAO carYear else  "
							+ filterField + "; filterValue : " + filterValue);
					Integer candidate = Integer.parseInt(filterValue);
					Expression<Integer> year = cb.function("year",
							Integer.class, rt.get(filterField));
					predicate = cb.and(predicate, cb.equal(year, candidate));

				} else {
					predicate = cb.and(
							predicate,
							cb.like(rt.<String> get(filterField), filterValue
									+ "%"));
				}
			}
			cq.where(predicate);
		}
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
