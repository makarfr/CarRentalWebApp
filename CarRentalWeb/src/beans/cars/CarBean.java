package beans.cars;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Car;
import model.CarModel;
import model.enums.CarType;

import org.primefaces.model.LazyDataModel;

import common.Actions;

import convertors.CarModelConverter;
import dao.interfaces.CarDAOInterface;
import dao.interfaces.CarModelDAOInterface;

@ManagedBean(name ="carBean")
@SessionScoped
public class CarBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Car car = new Car();
	@EJB
	private CarDAOInterface<Car> carDao;
	@EJB
	private CarModelDAOInterface<CarModel> carModelDao;
	private LazyDataModel<Car> lazyModel;
	
	private Date contractDateFrom;
	private Date contractDateTo;


	@PostConstruct
	public void init() {
		contractDateFrom = new Date();
		//contractDateFrom.setYear(1900);
		contractDateTo = new Date();
		//contractDateTo.setYear(2300);
		lazyModel = new CarLazyDataModel(carDao);
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public LazyDataModel<Car> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Car> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public CarType[] getTypes() {
		return CarType.values();
	}

	public List<Car> getCars(){
		List<Car> result = carDao.findAvailableBeetwenDates(contractDateFrom, contractDateTo);
		return result;
	}

	public List<CarModel> getCarModels(){
		List<CarModel> list = carModelDao.findAll(); 
		CarModelConverter.getList(list);
		return list;
	}

	public Date getContractDateFrom() {
		return contractDateFrom;
	}

	public void setContractDateFrom(Date contractDateFrom) {
		this.contractDateFrom = contractDateFrom;
	}

	public Date getContractDateTo() {
		return contractDateTo;
	}

	public void setContractDateTo(Date contractDateTo) {
		this.contractDateTo = contractDateTo;
	}

	public String addCar() {
		carDao.create(car); 
		return Actions.CARS_VIEW.getFullUrl();
	}

	public String goToAdd(){
		return Actions.ADD_CAR_VIEW.getViewUrl();
	}

	/* public List<Car> getAvailableCars() {
		 List<Car> list = new LinkedList<Car>();
		 System.out.println("getAvailableCars");
		 System.out.println("DateFrom" + contractDateFrom);
		 System.out.println("DateTo" + contractDateTo);
			list = carDao.findAvailableBeetwenDates(contractDateFrom, contractDateTo);
			return list;
	 }*/
}
