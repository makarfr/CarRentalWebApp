package model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import common.UserRole;


/**
 * The persistent class for the "client" database table.
 * 
 */
@Entity
@Table(name="Client")
@NamedQueries({
@NamedQuery(name="Client.findAll", query="FROM Client"),
@NamedQuery(name="Client.findAllLazy",query="FROM Client") ,
@NamedQuery(name="Client.count",query="SELECT count(*) from Client") ,
@NamedQuery(name="Client.getByNameSurname", query="FROM Client WHERE client_name=:name AND client_surname=:surname") ,
@NamedQuery(name="Client.getByCardNumber", query="FROM Client WHERE client_card_number=:cardNumber"),
@NamedQuery(name="Client.getByUser", query="select c FROM Client c join c.registerUser r where r.registerId =:regId")
})
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CLIENT_CLIENT_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="client_id", insertable=false, updatable=false, unique=true, nullable=false)
	private Long clientId;

	@Column(name="client_address", nullable=false, length=200)
	private String clientAddress;

	@SequenceGenerator(name="CLIENT_CLIENT_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="client_card_number", nullable=false)
	private Long clientCardNumber;

	@Column(name="client_discount", nullable=false)
	private BigDecimal clientDiscount;

	@Column(name="client_driver_license_number", nullable=false, length=50)
	private String clientDriverLicenseNumber;

	@Column(name="client_middle_name", length=50)
	private String clientMiddleName;

	@Column(name="client_name", nullable=false, length=50)
	private String clientName;

	@Column(name="client_phone_number", nullable=false, length=50)
	private String clientPhoneNumber;

	@Column(name="client_surname", nullable=false, length=50)
	private String clientSurname;

	@OneToOne(cascade= CascadeType.ALL, optional = false)
	@JoinColumn(name = "register_id")
	private RegisterUser registerUser;

	public Client() {
		registerUser = new RegisterUser();
		registerUser.setRole(new Role(UserRole.CLIENT.getCode()));
	}

	public Client(String clientAddress, Long clientCardNumber,
			BigDecimal clientDiscount, String clientDriverLicenseNumber,
			String clientMiddleName, String clientName,
			String clientPhoneNumber, String clientSurname,
			RegisterUser registerUser) {
		super();
		this.clientAddress = clientAddress;
		this.clientCardNumber = clientCardNumber;
		this.clientDiscount = clientDiscount;
		this.clientDriverLicenseNumber = clientDriverLicenseNumber;
		this.clientMiddleName = clientMiddleName;
		this.clientName = clientName;
		this.clientPhoneNumber = clientPhoneNumber;
		this.clientSurname = clientSurname;
		this.registerUser = registerUser;
	}

	public Long getClientId() {
		return this.clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getClientAddress() {
		return this.clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public Long getClientCardNumber() {
		return this.clientCardNumber;
	}

	public void setClientCardNumber(Long clientCardNumber) {
		this.clientCardNumber = clientCardNumber;
	}

	public BigDecimal getClientDiscount() {
		return this.clientDiscount;
	}

	public void setClientDiscount(BigDecimal clientDiscount) {
		this.clientDiscount = clientDiscount;
	}

	public String getClientDriverLicenseNumber() {
		return this.clientDriverLicenseNumber;
	}

	public void setClientDriverLicenseNumber(String clientDriverLicenseNumber) {
		this.clientDriverLicenseNumber = clientDriverLicenseNumber;
	}

	public String getClientMiddleName() {
		return this.clientMiddleName;
	}

	public void setClientMiddleName(String clientMiddleName) {
		this.clientMiddleName = clientMiddleName;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientPhoneNumber() {
		return this.clientPhoneNumber;
	}

	public void setClientPhoneNumber(String clientPhoneNumber) {
		this.clientPhoneNumber = clientPhoneNumber;
	}

	public String getClientSurname() {
		return this.clientSurname;
	}

	public void setClientSurname(String clientSurname) {
		this.clientSurname = clientSurname;
	}

	public RegisterUser getRegisterUser() {
		return this.registerUser;
	}

	public void setRegisterUser(RegisterUser registerUser) {
		this.registerUser = registerUser;
	}

	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        Client that = (Client) o;

	        if (clientCardNumber != that.clientCardNumber) return false;
	        if (clientDiscount != that.clientDiscount) return false;
	        if (clientId != that.clientId) return false;
	        if (clientAddress != null ? !clientAddress.equals(that.clientAddress) : that.clientAddress != null)
	            return false;
	        if (clientDriverLicenseNumber != null ? !clientDriverLicenseNumber.equals(that.clientDriverLicenseNumber) : that.clientDriverLicenseNumber != null)
	            return false;
	        if (clientMiddleName != null ? !clientMiddleName.equals(that.clientMiddleName) : that.clientMiddleName != null)
	            return false;
	        if (clientName != null ? !clientName.equals(that.clientName) : that.clientName != null) return false;
	        if (clientPhoneNumber != null ? !clientPhoneNumber.equals(that.clientPhoneNumber) : that.clientPhoneNumber != null)
	            return false;
	        if (clientSurname != null ? !clientSurname.equals(that.clientSurname) : that.clientSurname != null)
	            return false;

	        return true;
	    }

	    @Override
	    public int hashCode() {
	        long result = clientId;
	        result = 31 * result + clientCardNumber;
	        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
	        result = 31 * result + (clientMiddleName != null ? clientMiddleName.hashCode() : 0);
	        result = 31 * result + (clientSurname != null ? clientSurname.hashCode() : 0);
	        result = 31 * result + (clientAddress != null ? clientAddress.hashCode() : 0);
	        result = 31 * result + (clientPhoneNumber != null ? clientPhoneNumber.hashCode() : 0);
	        result = 31 * result + (clientDriverLicenseNumber != null ? clientDriverLicenseNumber.hashCode() : 0);
	        result = 31 * result + clientDiscount.longValue();
	        return (int) result;
	    }

		@Override
		public String toString() {
			return "Client [clientId=" + clientId + ", clientAddress="
					+ clientAddress + ", clientCardNumber=" + clientCardNumber
					+ ", clientDiscount=" + clientDiscount
					+ ", clientDriverLicenseNumber="
					+ clientDriverLicenseNumber + ", clientMiddleName="
					+ clientMiddleName + ", clientName=" + clientName
					+ ", clientPhoneNumber=" + clientPhoneNumber
					+ ", clientSurname=" + clientSurname + ", registerUser="
					+ registerUser + "]";
		}


}