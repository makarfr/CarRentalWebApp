package beans.cars;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import model.Car;

import common.Actions;
@SessionScoped
@ManagedBean
public class SelectedCarBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Car car;

    public String choose(Car car) {
    	System.out.println("choose car " + car);
        this.car = car;
        setCar(car);
        System.out.println("after this.car = car " + this.car);
        this.car.setCarDescription("Hello");
     //   return Actions.ORDER_POST.getFullUrl();
        return Actions.ORDER_POST.getFullUrl();
    }

    public Car getCar() {
    	System.out.println("getCar" + this.car);
        return this.car;
    }

    public void setCar(Car car) {
      	System.out.println("setCar" + car);
        this.car = car;
    }
}
