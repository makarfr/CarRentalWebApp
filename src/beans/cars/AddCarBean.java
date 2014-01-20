package beans.cars;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import common.Actions;

import model.Car;
import model.CarModel;
import model.enums.CarType;
import convertors.CarModelConverter;
import dao.interfaces.CarDAOInterface;
import dao.interfaces.CarModelDAOInterface;

@ManagedBean
@RequestScoped
public class AddCarBean {

    @EJB
    private CarDAOInterface<Car> carDao;
    @EJB
	private CarModelDAOInterface<CarModel> carModelDao;
    private Car car = new Car();

    public String addCar() {
        carDao.create(car);
        return Actions.CARS_VIEW.getViewUrl();
    }


    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    
    public List<CarModel> getCarModels(){
		List<CarModel> list = new LinkedList<CarModel>();
		list = carModelDao.findAll();
		CarModelConverter.getList(list);
		return list;
	}
    
	public CarType[] getTypes() {
		return CarType.values();
	}
}
