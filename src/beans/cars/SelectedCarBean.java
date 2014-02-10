package beans.cars;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Car;

import common.Actions;
@ViewScoped
@ManagedBean
public class SelectedCarBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Car car;

    public String choose(Car car) {
        this.car = car;
        return Actions.ORDER_POST.getFullUrl();
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
