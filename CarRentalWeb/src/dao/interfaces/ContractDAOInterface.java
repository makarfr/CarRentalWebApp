package dao.interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import model.Contract;

import org.primefaces.model.SortOrder;

@Local
public interface ContractDAOInterface<T> extends EntityDAOInterface<T> {

	@Override
	List<T> findRange(int start, int count, String sortField,
             SortOrder sortOrder, Map<String, String> filters) ;
	int count(Map<String, String> filters) ;
		List<Contract> getClientContractsByRegisterId(Long registerId); 
}
