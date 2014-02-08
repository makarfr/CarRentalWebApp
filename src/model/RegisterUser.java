package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;


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
	private Long registerId;

	@Email(message = "Invalid email")
	@Column(name="register_login", nullable=false, length=200, unique = true)
	private String registerLogin;

	@Length(min=3, max=25, message = "Your password must be at least 3 symbols") 
	@Column(name="register_password", nullable=false, length=200)
	private String registerPassword;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	
	public RegisterUser() {
	}

	public RegisterUser(String registerLogin, String registerPassword,
			Client client) {
		super();
		this.registerLogin = registerLogin;
		this.registerPassword = registerPassword;
		
	
	}

	public Long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
	}

	public String getRegisterLogin() {
		return registerLogin;
	}

	public void setRegisterLogin(String registerLogin) {
		this.registerLogin = registerLogin.toLowerCase();
	}

	public String getRegisterPassword() {
		return registerPassword;
	}

	public void setRegisterPassword(String registerPassword) {
		this.registerPassword = registerPassword;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        RegisterUser that = (RegisterUser) o;

	         if (registerId != that.registerId) return false;
	        if (registerLogin != null ? !registerLogin.equals(that.registerLogin) : that.registerLogin != null)
	            return false;
	        if (registerPassword != null ? !registerPassword.equals(that.registerPassword) : that.registerPassword != null)
	            return false;

	        return true;
	    }

	    @Override
	    public int hashCode() {
	        long result = registerId == null ? 0L : registerId;
	        result = 31 * result + (registerLogin != null ? registerLogin.hashCode() : 0);
	        result = 31 * result + (registerPassword != null ? registerPassword.hashCode() : 0);
	        return (int) result;
	    }

		@Override
		public String toString() {
			return "RegisterUser [registerId=" + registerId
					+ ", registerLogin=" + registerLogin
					+ ", registerPassword=" + registerPassword + ", role="
					+ role + "]";
		}

		
}