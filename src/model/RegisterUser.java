package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the "register_user" database table.
 * 
 */
@Entity
@Table(name="register_user")
@NamedQueries({
@NamedQuery(name="RegisterUser.findAll", query="SELECT r FROM RegisterUser r"),
@NamedQuery(name="RegisterUser.findByLogin", query="SELECT r FROM RegisterUser r WHERE registerLogin=:login")})
public class RegisterUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="REGISTER_USER_REGISTER_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="register_id", insertable=false, updatable=false, unique=true, nullable=false)
	private long registerId;

	@Column(name="client_id", nullable=false, insertable=false, updatable=false)
	private long clientId;

	@Column(name="register_login", nullable=false, length=50)
	private String registerLogin;

	@Column(name="register_password", nullable=false, length=50)
	private String registerPassword;
	
	@Column(name="role", nullable=false, length=20)
	private String role;

	//bi-directional many-to-one association to Contract
	/* @OneToMany(targetEntity = model.Contract.class, mappedBy = "register.user")
	private List<Contract> contracts;*/

	//bi-directional one-to-one association to Client
	@OneToOne
	@JoinColumn(name ="client_id")
	private Client client;

	public RegisterUser() {
	}

	public RegisterUser(String registerLogin, String registerPassword,
			Client client) {
		super();
		this.registerLogin = registerLogin;
		this.registerPassword = registerPassword;
		this.client = client;
		this.clientId = client.getClientId();
	}

	public long getRegisterId() {
		return this.registerId;
	}

	public void setRegisterId(long registerId) {
		this.registerId = registerId;
	}

	public long getClientId() {
		return this.clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getRegisterLogin() {
		return this.registerLogin;
	}

	public void setRegisterLogin(String registerLogin) {
		this.registerLogin = registerLogin;
	}

	public String getRegisterPassword() {
		return this.registerPassword;
	}

	public void setRegisterPassword(String registerPassword) {
		this.registerPassword = registerPassword;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

/*	public List<Contract> getContracts() {
		return this.contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public Contract addContract(Contract contract) {
		getContracts().add(contract);
		contract.setRegisterUser(this);

		return contract;
	}

	public Contract removeContract(Contract contract) {
		getContracts().remove(contract);
		contract.setRegisterUser(null);

		return contract;
	}*/

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        RegisterUser that = (RegisterUser) o;

	        if (clientId != that.clientId) return false;
	        if (registerId != that.registerId) return false;
	        if (registerLogin != null ? !registerLogin.equals(that.registerLogin) : that.registerLogin != null)
	            return false;
	        if (registerPassword != null ? !registerPassword.equals(that.registerPassword) : that.registerPassword != null)
	            return false;

	        return true;
	    }

	    @Override
	    public int hashCode() {
	        long result = registerId;
	        result = 31 * result + clientId;
	        result = 31 * result + (registerLogin != null ? registerLogin.hashCode() : 0);
	        result = 31 * result + (registerPassword != null ? registerPassword.hashCode() : 0);
	        return (int) result;
	    }

		
}