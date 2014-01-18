package beans.cars;

import java.io.Serializable;
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

import convertors.CarModelConverter;

import dao.implementations.CarModelDAO;
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

	  
	  @PostConstruct
	    public void init() {
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
		
	public void saveCar() {
		carDao.create(car);
	}
	
	public List<CarModel> getCarModels(){
		List<CarModel> list = new LinkedList<CarModel>();
		list = carModelDao.findAll();
		CarModelConverter.getList(list);
		return list;
	}
	
	 public String addCar() {
		carDao.create(car);
       return "carView";
   }
	 
	 public String goToAdd(){
		 return "addCarView";
	 }
	
	
}
