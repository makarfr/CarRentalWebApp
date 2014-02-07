package dao.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.primefaces.model.SortOrder;

import model.Car;

@Local
public interface CarDAOInterface<T> extends EntityDAOInterface<T> {

	List<Car> findAvailableBeetwenDates(Date contractDateFrom,
			Date contractDateTo);

	List<Car> findRange(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters,
			String contractDateFrom, String contractDateTo);
	
	

}