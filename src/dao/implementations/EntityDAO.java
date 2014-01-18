package dao.implementations;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import model.Client;

import org.primefaces.model.SortOrder;

import dao.interfaces.EntityDAOInterface;

public abstract class EntityDAO<T> implements EntityDAOInterface<T>{


    @PersistenceContext(unitName = "CarRentalWebApp")
    protected EntityManager em;

    @Override
    public void create(T newInstance) {
        em.persist(newInstance);
    }

    @Override
    public T find(long id) {
        T t = em.find(getEntityClass(), id);
        return t;
    }

    @Override
    public void update(T transientObject) {
        em.merge(transientObject);
    }

    @Override
    public void delete(T persistentObject) {
        em.remove(em.merge(persistentObject));
    }

    @Override
    public List<T> findAll() {
        @SuppressWarnings("unchecked")
        CriteriaQuery<T> cq = (CriteriaQuery<T>) em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(getEntityClass()));
        List<T> ret = em.createQuery(cq).getResultList();
        return ret;
    }

    @Override
    public List<T> findRange(int start, int count) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(getEntityClass()));
        Query q = em.createQuery(cq);
        q.setMaxResults(count);
        q.setFirstResult(start);
        return q.getResultList();
    }

    @Override
    public List<T> findRange(int start, int count, String sortField, SortOrder sortOrder) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getEntityClass());
        Root<T> rt = cq.from(getEntityClass());

        cq.select(rt);
        if (sortOrder == SortOrder.ASCENDING) {
            cq.orderBy(cb.asc(rt.get(sortField)));
        } else if (sortOrder == SortOrder.DESCENDING) {
            cq.orderBy(cb.desc(rt.get(sortField)));
        }
        Query q = em.createQuery(cq);
        q.setMaxResults(count);
        q.setFirstResult(start);
        return q.getResultList();
    }

    @Override
    public long count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(getEntityClass());
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public List<T> findRange(int start, int count, String filterField, String filterValue) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> rt = cq.from(getEntityClass());
        Path path = getPath(rt, filterField);
        cq.where(cb.like(path, cb.parameter(String.class, "param")));
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        q.setParameter("param", filterValue + "%");
        q.setMaxResults(count);
        q.setFirstResult(start);
        return q.getResultList();
    }

    @Override
    public int count(String filterField, String filterValue) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> rt = cq.from(getEntityClass());
        Path path = getPath(rt, filterField);
        cq.where(cb.like(path, cb.parameter(String.class, "param")));
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        q.setParameter("param", filterValue + "%");
        return ((Long) q.getSingleResult()).intValue();
    }

    protected Path getPath(Root<T> rt, String column) {
        Path<String> path = null;
        String[] tokens = column.split("\\.");
        if (tokens.length == 1) {
            path = rt.<String>get(tokens[0]);
        } else if (tokens.length == 2) {
            path = rt.<String>get(tokens[0]).<String>get(tokens[1]);
        }
        return path;
    }

    @Override
	public List<T> findRange(int start, int count, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(getEntityClass());
		Root<T> rt = cq.from(getEntityClass());
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
    
    @Override
    public int count(Map<String, String> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery(getEntityClass());
		Root<T> rt = cq.from(getEntityClass());
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
    
    public abstract Class<T> getEntityClass();

}

