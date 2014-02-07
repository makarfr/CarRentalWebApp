package beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name ="index")
@SessionScoped
public class IndexBean implements Serializable {

	public IndexBean() {
		
	}

	public String getMessage() {
		message = "Hello index page! This is talking Bean!!";
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String message;
	
	
}
