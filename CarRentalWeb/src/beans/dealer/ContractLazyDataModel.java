package beans.dealer;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import model.Contract;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

import dao.interfaces.ContractDAOInterface;

public class ContractLazyDataModel extends LazyDataModel<Contract> implements
		SelectableDataModel<Contract> {

	private static final long serialVersionUID = 1L;
	private List<Contract> data;
	private int pageSize;
	private int rowIndex;
	private int rowCount;
	@EJB
	private ContractDAOInterface<Contract> contractDAO;
	private List<Contract> datasource;

	public ContractLazyDataModel(List<Contract> datasource,
			ContractDAOInterface<Contract> contractDAO) {
		this.datasource = datasource;
		this.contractDAO = contractDAO;
	}

	@Override
	public List<Contract> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {

		data = contractDAO.findRange(first, pageSize, sortField, sortOrder,
				filters);
		setRowCount(contractDAO.count(filters));

		return data;
	}

	@Override
	public boolean isRowAvailable() {
		if (data == null)
			return false;
		int index = rowIndex % pageSize;
		return index >= 0 && index < data.size();
	}

	@Override
	public Object getRowKey(Contract contract) {
		return String.valueOf(contract.getContractId());
	}

	@Override
	public Contract getRowData() {
		if (data == null)
			return null;
		int index = rowIndex % pageSize;
		if (index > data.size()) {
			return null;
		}
		return data.get(index);
	}

	@Override
	public Contract getRowData(String rowKey) {
		if (data == null)
			return null;
		for (Contract contract : data) {
			if (String.valueOf(contract.getContractId()).equals(rowKey))
				return contract;
		}
		return null;
	}

	@Override
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getRowIndex() {
		return this.rowIndex;
	}

	@Override
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	@Override
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	@Override
	public int getRowCount() {
		return this.rowCount;
	}

	@Override
	public void setWrappedData(Object list) {
		this.data = (List<Contract>) list;
	}

	@Override
	public Object getWrappedData() {
		return data;
	}
}
