package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import model.enums.StatusCar;


/**
 * The persistent class for the "car_status" database table.
 * 
 */
@Entity
@Table(name="car_status")
@NamedQuery(name="CarStatus.findAll", query="SELECT c FROM CarStatus c")
public class CarStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CAR_STATUS_CAR_STATUS_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="car_status_id", insertable=false, updatable=false, unique=true, nullable=false)
	private long carStatusId;

	@Column(name="car_id", nullable=false)
	private long carId;

	
	@Column(name="date_from", nullable=false)
	private Date dateFrom;

	
	@Column(name="date_to", nullable=false)
	private Date dateTo;

	@Enumerated(EnumType.STRING)
	private StatusCar statusCar;

	/*//bi-directional many-to-one association to Car
	@OneToMany(mappedBy="carStatus")
	private List<Car> cars;
*/
	public CarStatus() {
	}

	public CarStatus(Car car, Date dateFrom, Date dateTo, StatusCar statusCar) {
		super();
		this.carId = car.getCarId();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.statusCar = statusCar;
	}

	public long getCarStatusId() {
		return this.carStatusId;
	}

	public void setCarStatusId(long carStatusId) {
		this.carStatusId = carStatusId;
	}

	public long getCarId() {
		return this.carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Enum<StatusCar> getStatus() {
		return this.statusCar;
	}

	public void setStatus(StatusCar status) {
		this.statusCar = status;
	}

/*	public List<Car> getCars() {
		return this.cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}*/

	/*public Car addCar(Car car) {
		getCars().add(car);
		car.setCarStatus(this);

		return car;
	}

	public Car removeCar(Car car) {
		getCars().remove(car);
		car.setCarStatus(null);

		return car;
	}*/
	
	  @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        CarStatus that = (CarStatus) o;

	        if (carStatusId != that.carStatusId) return false;
	        if (statusCar != that.statusCar) return false;
	        if (dateFrom != null ? !dateFrom.equals(that.dateFrom) : that.dateFrom != null) return false;
	        if (dateTo != null ? !dateTo.equals(that.dateTo) : that.dateTo != null) return false;

	        return true;
	    }

	    @Override
	    public int hashCode() {
	        int result = (int) carStatusId;
	        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
	        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
	        return result;
	    }

}