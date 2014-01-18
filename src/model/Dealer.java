package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the "dealer" database table.
 * 
 */
@Entity
@Table(name="dealer")
@NamedQuery(name="Dealer.findAll", query="SELECT d FROM Dealer d")
public class Dealer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DEALER_DEALER_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dealer_id", insertable=false, updatable=false, unique=true, nullable=false)
	private long dealerId;

	@Column(name="dealer_address", nullable=false, length=200)
	private String dealerAddress;

	@Column(name="dealer_name", nullable=false, length=50)
	private String dealerName;

	@Column(name="dealer_phone_number", nullable=false, length=50)
	private long dealerPhoneNumber;

	@Column(name="dealer_surname", nullable=false, length=50)
	private String dealerSurname;

	//bi-directional many-to-one association to Contract
	@OneToMany(mappedBy="dealer")
	private List<Contract> contracts;

	public Dealer() {
	}

	public Dealer(String dealerAddress, String dealerName,
			long dealerPhoneNumber, String dealerSurname) {
		super();
		this.dealerAddress = dealerAddress;
		this.dealerName = dealerName;
		this.dealerPhoneNumber = dealerPhoneNumber;
		this.dealerSurname = dealerSurname;
	}

	public long getDealerId() {
		return this.dealerId;
	}

	public void setDealerId(long dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerAddress() {
		return this.dealerAddress;
	}

	public void setDealerAddress(String dealerAddress) {
		this.dealerAddress = dealerAddress;
	}

	public String getDealerName() {
		return this.dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public long getDealerPhoneNumber() {
		return this.dealerPhoneNumber;
	}

	public void setDealerPhoneNumber(long dealerPhoneNumber) {
		this.dealerPhoneNumber = dealerPhoneNumber;
	}

	public String getDealerSurname() {
		return this.dealerSurname;
	}

	public void setDealerSurname(String dealerSurname) {
		this.dealerSurname = dealerSurname;
	}

	public List<Contract> getContracts() {
		return this.contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public Contract addContract(Contract contract) {
		getContracts().add(contract);
		contract.setDealer(this);

		return contract;
	}

	public Contract removeContract(Contract contract) {
		getContracts().remove(contract);
		contract.setDealer(null);

		return contract;
	}

	  @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        Dealer that = (Dealer) o;

	        if (dealerId != that.dealerId) return false;
	        if (dealerAddress != null ? !dealerAddress.equals(that.dealerAddress) : that.dealerAddress != null)
	            return false;
	        if (dealerName != null ? !dealerName.equals(that.dealerName) : that.dealerName != null) return false;
	        if (dealerSurname != null ? !dealerSurname.equals(that.dealerSurname) : that.dealerSurname != null)
	            return false;

	        return true;
	    }

	    @Override
	    public int hashCode() {
	        long result = dealerId;
	        result = 31 * result + (dealerName != null ? dealerName.hashCode() : 0);
	        result = 31 * result + (dealerSurname != null ? dealerSurname.hashCode() : 0);
	        result = 31 * result + (dealerAddress != null ? dealerAddress.hashCode() : 0);
	        return (int) result;
	    }
}