package beans.cars;

import java.util.List;
import java.util.Map;

import model.Car;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import dao.interfaces.CarDAOInterface;

public class CarLazyDataModel extends LazyDataModel<Car> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Car> data;
	private int pageSize;
	private int rowIndex;
	private int rowCount;
	private CarDAOInterface<Car> carDao;

	public CarLazyDataModel(CarDAOInterface<Car> carDao2) {
		this.carDao = carDao2;
	}

	@Override
	public List<Car> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			data = carDao.findRange(first, pageSize, sortField, sortOrder, filters);
			//		findRange(first, pageSize, sortField, sortOrder,
				//	filters);
			setRowCount(carDao.count(filters));

		} catch (NullPointerException e) {

		}

		return data;
	}

	@Override
	public boolean isRowAvailable() {
		if (data == null)
			return false;
		int index = rowIndex % pageSize;
		return index >= 0 && index < data.size();
	}

	@Override
	public Object getRowKey(Car car) {
		return car.getCarId();
	}

	@Override
	public Car getRowData() {
		if (data == null)
			return null;
		int index = rowIndex % pageSize;
		if (index > data.size()) {
			return null;
		}
		return data.get(index);
	}

	@Override
	public Car getRowData(String rowKey) {
		if (data == null)
			return null;
		for (Car car : data) {
			if (String.valueOf(car.getCarId()).equals(rowKey))
				return car;
		}
		return null;
	}

	@Override
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getRowIndex() {
		return this.rowIndex;
	}

	@Override
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	@Override
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	@Override
	public int getRowCount() {
		return this.rowCount;
	}

	@Override
	public void setWrappedData(Object list) {
		this.data = (List<Car>) list;
	}

	@Override
	public Object getWrappedData() {
		return data;
	}
}
