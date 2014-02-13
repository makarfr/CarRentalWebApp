package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;

import model.enums.StatusContract;


/**
 * The persistent class for the "contract" database table.
 * 
 */
@Entity
@Table(name="contract")
@NamedQueries({
@NamedQuery(name="Contract.findAll", query="SELECT c FROM Contract c"),
@NamedQuery(name ="Contract.getClientContractsByRegisterId", query= "SELECT c FROM Contract c join c.registerUser r where r.registerId =:regId")
})
public class Contract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="contract_contract_id_seq" )
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="contract_id", insertable=false, updatable=false, unique=true, nullable=false)
	private Long contractId;

	@Column(name="car_id", nullable=false, insertable=false, updatable=false)
	private Long carId;

	
	@Column(name="contract_date_from", nullable=false)
	private Date contractDateFrom;

	
	@Column(name="contract_date_to", nullable=false)
	private Date contractDateTo;
	
	@DecimalMin("1.00")
	@Column(name="total_price", nullable=false)
	private BigDecimal totalPrice;

	//bi-directional many-to-one association to Car
	@ManyToOne
	@JoinColumn(name ="car_id")
	private Car car;

	//bi-directional many-to-one association to Dealer
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name ="dealer_id")
	private Dealer dealer;

	//bi-directional many-to-one association to RegisterUser
	@ManyToOne
	 @JoinColumn(name = "register_id")
	private RegisterUser registerUser;

@Column(name = "status_contract")
	@Enumerated(EnumType.STRING)
	private StatusContract statusContract;

	public Contract() {
	}

	public Contract(Car car, Date contractDateFrom, Date contractDateTo,
BigDecimal totalPrice, Dealer dealer, RegisterUser registerUser,
			StatusContract statusContract) {
		super();
		this.carId = car.getCarId();
		this.contractDateFrom = contractDateFrom;
		this.contractDateTo = contractDateTo;
		this.totalPrice = totalPrice;
		this.car = car;
		this.dealer = dealer;
		this.registerUser = registerUser;
		this.statusContract = statusContract;
	}

	public Long getContractId() {
		return this.contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Long getCarId() {
		return this.carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public Date getContractDateFrom() {
		return this.contractDateFrom;
	}

	public void setContractDateFrom(Date contractDateFrom) {
		this.contractDateFrom = contractDateFrom;
	}

	public Date getContractDateTo() {
		return this.contractDateTo;
	}

	public void setContractDateTo(Date contractDateTo) {
		this.contractDateTo = contractDateTo;
	}


	public BigDecimal getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(BigDecimal bigDecimal) {
		this.totalPrice = bigDecimal;
	}

	public Car getCar() {
		return this.car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public RegisterUser getRegisterUser() {
		return this.registerUser;
	}

	public void setRegisterUser(RegisterUser registerUser) {
		this.registerUser = registerUser;
	}

	public StatusContract getStatus() {
		return this.statusContract;
	}

	public void setStatus(StatusContract statusContract) {
		this.statusContract = statusContract;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract that = (Contract) o;

        if (car.getCarId() != that.car.getCarId()) return false;
        if (contractId != that.contractId) return false;
        if (dealer != that.dealer) return false;
        if (registerUser.getRegisterId() != that.registerUser.getRegisterId()) return false;
        if (totalPrice != that.totalPrice) return false;
        if (contractDateFrom != null ? !contractDateFrom.equals(that.contractDateFrom) : that.contractDateFrom != null)
            return false;
        if (contractDateTo != null ? !contractDateTo.equals(that.contractDateTo) : that.contractDateTo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        long result = 56;
        result = 31 * result + car.getCarId();
        result = 31 * result + registerUser.getRegisterId();
     //   result = 31 * result + dealer.getDealerId();
        result = 31 * result + (contractDateFrom != null ? contractDateFrom.hashCode() : 0);
        result = 31 * result + (contractDateTo != null ? contractDateTo.hashCode() : 0);
        result = 31 * result + totalPrice.intValue();
        return (int) result;
    }

}