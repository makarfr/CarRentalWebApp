package beans.dealer;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import model.Dealer;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

import dao.interfaces.DealerDAOInterface;

public class DealerLazyDataModel extends LazyDataModel<Dealer> implements
		SelectableDataModel<Dealer> {

	private static final long serialVersionUID = 1L;
	private List<Dealer> data;
	private int pageSize;
	private int rowIndex;
	private int rowCount;
	@EJB
	private DealerDAOInterface<Dealer> dealerDAO;
	private List<Dealer> datasource;

	public DealerLazyDataModel(List<Dealer> datasource,
			DealerDAOInterface<Dealer> dealerDAO) {
		this.datasource = datasource;
		this.dealerDAO = dealerDAO;
	}

	@Override
	public List<Dealer> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {

		data = dealerDAO.findRange(first, pageSize, sortField, sortOrder,
				filters);
		setRowCount(dealerDAO.count(filters));

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
	public Object getRowKey(Dealer dealer) {
		return String.valueOf(dealer.getDealerId());
	}

	@Override
	public Dealer getRowData() {
		if (data == null)
			return null;
		int index = rowIndex % pageSize;
		if (index > data.size()) {
			return null;
		}
		return data.get(index);
	}

	@Override
	public Dealer getRowData(String rowKey) {
		if (data == null)
			return null;
		for (Dealer dealer : data) {
			if (String.valueOf(dealer.getDealerId()).equals(rowKey))
				return dealer;
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
		this.data = (List<Dealer>) list;
	}

	@Override
	public Object getWrappedData() {
		return data;
	}
}
