package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import model.enums.CarType;


/**
 * The persistent class for the "car" database table.
 * 
 */
@Entity
@Table(name = "car")
@NamedQueries({
@NamedQuery(name="Car.findAll", query="SELECT c FROM Car c"),
@NamedQuery(name="Car.count",query="SELECT count(*) from Car")
})
public class Car implements Serializable {
	private static final long serialVersionUID = 1L;

	 @Id
	@GeneratedValue(generator = "car_id_generator", strategy = GenerationType.AUTO)
	    @SequenceGenerator(name = "car_id_generator", sequenceName = "car_car_id_seq", allocationSize = 1)
	@Column(name="car_id")
	private Long carId;

	@Column(name="car_description", length=200)
	private String carDescription;

	@Column(name="car_model_id", nullable=false,  insertable=false, updatable=false)
	private Long carModelId;

	@Column(name="car_number", nullable=false, length=20)
	private String carNumber;

	@Column(name="car_price")
	private BigDecimal carPrice;
	
	@Column(name="car_year", nullable=false)
	private Date carYear;

	//bi-directional many-to-one association to CarModel
	@ManyToOne
	@JoinColumn(name = "car_model_id")
	private CarModel carModel;


	@Column(name="car_type", nullable=false)
	@Enumerated(EnumType.STRING)
	private CarType carType;

	//bi-directional many-to-one association to Contract
	@OneToMany(mappedBy="car")
	private List<Contract> contracts;

	public Car() {
	}
	
	
	public Car(String carNumber, BigDecimal carPrice, Date carYear,
			CarModel carModel, CarType carType) {
		this.carNumber = carNumber;
		this.carPrice = carPrice;
		this.carYear = carYear;
		this.carModel = carModel;
		this.carModelId = carModel.getCarModelId();
		this.carType = carType;
	}



	public Long getCarId() {
		return this.carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public String getCarDescription() {
		return this.carDescription;
	}

	public void setCarDescription(String carDescription) {
		this.carDescription = carDescription;
	}

	public long getCarModelId() {
		return this.carModelId;
	}

	public void setCarModelId(Long carModelId) {
		this.carModelId = carModelId;
	}

	public String getCarNumber() {
		return this.carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public Date getCarYear() {
		return this.carYear;
	}

	public void setCarYear(Date carYear) {
		this.carYear = carYear;
	}

	public CarModel getCarModel() {
		return this.carModel;
	}

	public void setCarModel(CarModel carModel) {
		this.carModel = carModel;
	}

 	
	public BigDecimal getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(BigDecimal carPrice) {
		this.carPrice = carPrice;
	}

	public CarType getCarType() {
		return this.carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}



	public List<Contract> getContracts() {
		return this.contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public Contract addContract(Contract contract) {
		getContracts().add(contract);
		contract.setCar(this);

		return contract;
	}

	public Contract removeContract(Contract contract) {
		getContracts().remove(contract);
		contract.setCar(null);

		return contract;
	}
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        Car carEntity = (Car) o;

	        if (carId != carEntity.carId) return false;
	        if (carDescription != null ? !carDescription.equals(carEntity.carDescription) : carEntity.carDescription != null)
	            return false;
	        if (carNumber != null ? !carNumber.equals(carEntity.carNumber) : carEntity.carNumber != null) return false;
	        if (carYear != null ? !carYear.equals(carEntity.carYear) : carEntity.carYear != null) return false;

	        return true;
	    }

	    @Override
	    public int hashCode() {
	        long result = carId;
	        result = 31 * result + (carNumber != null ? carNumber.hashCode() : 0);
	        result = 31 * result + (carYear != null ? carYear.hashCode() : 0);
	        result = 31 * result + (carDescription != null ? carDescription.hashCode() : 0);
	        return (int) result;
	    }

		public String getCarInfo() {
			return carModel + " #" + carNumber + "," + carType + "," + carYear ;
		}

	

}