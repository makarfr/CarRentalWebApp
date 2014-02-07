package dao.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.primefaces.model.SortOrder;

@Local
public interface EntityDAOInterface<T> {
	void create(T t);

	T find(long id);

	List<T> findAll();

	void update(T t);

	void delete(T t);

	abstract Class getEntityClass();

	List<T> findRange(int start, int count, String sortField, SortOrder sortOrder);

	long count();

	List<T> findRange(int start, int count, String filterField,	String filterValue);
	List<T> findRange(int start, int count, String sortField, SortOrder sortOrder, Map<String, String> filters) ;
	int count(String filterField, String filterValue);

	List<T> findRange(int start, int count);

	int count(Map<String, String> filters);

}
