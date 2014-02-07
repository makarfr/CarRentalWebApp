package dao.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.primefaces.model.SortOrder;

import model.Client;

@Local
public interface ClientDAOInterface<T> extends EntityDAOInterface<T> {
	
	public List<T> getByNameSurname(String name, String surname);

	public Client getByCardNumber(long cardNumber);

	/*public List<Client> findRange(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters);*/

	public int count(Map<String, String> filters);

	public List<Client> findAll(int i, int j);
	Client getByUser(long regId);
	
	  public List<Client> findAllFromDAO();

}
