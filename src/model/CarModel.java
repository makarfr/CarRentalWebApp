package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the "car_model" database table.
 * 
 */
@Entity
@Table(name="car_model")
@NamedQueries({
@NamedQuery(name="CarModel.findAll", query="SELECT c FROM CarModel c"),
@NamedQuery(name="CarModel.findByModelNameAndDesc",query="FROM CarModel WHERE carModelName=:name AND carModelDescription=:desc") 
})
public class CarModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CAR_MODEL_CAR_MODEL_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="car_model_id", insertable=false, updatable=false, unique=true, nullable=false)
	private Long carModelId;

	@Column(name="car_model_description", length=250)
	private String carModelDescription;

	@Column(name="car_model_name", nullable=false, length=250)
	private String carModelName;

	//bi-directional many-to-one association to Car
	@OneToMany(mappedBy="carModel")
	private List<Car> cars;

	public CarModel() {
	}

	public CarModel(String carModelName) {
		this.carModelName = carModelName;
	}

	public Long getCarModelId() {
		return this.carModelId;
	}

	public void setCarModelId(Long carModelId) {
		this.carModelId = carModelId;
	}

	public String getCarModelDescription() {
		return this.carModelDescription;
	}

	public void setCarModelDescription(String carModelDescription) {
		this.carModelDescription = carModelDescription;
	}

	public String getCarModelName() {
		return this.carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}

	public List<Car> getCars() {
		return this.cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	
	
	
	 @Override
	    public boolean equals(Object o) {
		 if (this == o) return true;
	        if (o == null || !(o instanceof CarModel)) return false;

	        CarModel that = (CarModel) o;

	        if (carModelId != that.carModelId) return false;
	        if (carModelDescription != null ? !carModelDescription.equals(that.carModelDescription) : that.carModelDescription != null)
	            return false;
	        if (carModelName != null ? !carModelName.equals(that.carModelName) : that.carModelName != null) return false;

	        return true;
	    }

	    @Override
	    public int hashCode() {
	        long result = carModelId;
	        result = 31 * result + (carModelName != null ? carModelName.hashCode() : 0);
	        result = 31 * result + (carModelDescription != null ? carModelDescription.hashCode() : 0);
	        return (int) result;
	    }
	

}