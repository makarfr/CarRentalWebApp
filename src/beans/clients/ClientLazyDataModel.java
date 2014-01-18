package beans.clients;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import model.Client;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

import dao.implementations.ClientDAO;
import dao.interfaces.ClientDAOInterface;

public class ClientLazyDataModel extends LazyDataModel<Client> implements SelectableDataModel<Client> {

	/**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;
//	@EJB
	private ClientDAOInterface<Client> dao;
    private List<Client> datasource;
    

    public List<Client> getDatasource() {
        return datasource;
    }

    public ClientLazyDataModel(List<Client> datasource, ClientDAOInterface<Client> dao) {
        this.datasource = datasource;
        this.dao  = dao;
        setRowCount(this.datasource.size());
    }


    public void setDatasource(List<Client> datasource) {
        this.datasource = datasource;
    }

    @Override
    public Client getRowData(String rowKey){
        for(Client Client : datasource) {
            if(String.valueOf(Client.getClientId()).equals(rowKey))
                return Client;
        }
        return null;

    }

    @Override
    public Object getRowKey(Client Client) {
        return Client.getClientId();
    }

    @Override
    public void setRowIndex(int rowIndex) {
        
         * The following is in ancestor (LazyDataModel):
         * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
         
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        }
        else
            super.setRowIndex(rowIndex % getPageSize());
    }


    @Override
    public List<Client> load(int i, int i2, String s, SortOrder sortOrder, Map<String, String> stringStringMap)  {

     datasource = dao.findRange(i, i2, s, sortOrder, stringStringMap);     
   
        long count = dao.count();
        this.setRowCount((int) count);
        sortOrder = SortOrder.DESCENDING;
        return datasource;
    }*/
	
	
	  private List<Client> data;
	    private int pageSize;
	    private int rowIndex;
	    private int rowCount;
	  @EJB
	    private ClientDAOInterface<Client> clientDao;
		private List<Client> datasource;

	    public ClientLazyDataModel(List<Client> datasource, ClientDAOInterface<Client> clientDao2) {
	        this.datasource = datasource;
	        this.clientDao = clientDao2;
	    }

	    @Override
	    public List<Client> load(int first, int pageSize, String sortField,
	                          SortOrder sortOrder, Map<String, String> filters) {

	        data = clientDao.findRange(first, pageSize, sortField, sortOrder, filters);
	        setRowCount(clientDao.count(filters));

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
	    public Object getRowKey(Client client) {
	        return String.valueOf(client.getClientId());
	    }

	    @Override
	    public Client getRowData() {
	        if (data == null)
	            return null;
	        int index = rowIndex % pageSize;
	        if (index > data.size()) {
	            return null;
	        }
	        return data.get(index);
	    }

	    @Override
	    public Client getRowData(String rowKey) {
	        if (data == null)
	            return null;
	        for (Client client : data) {
	            if (String.valueOf(client.getClientId()).equals(rowKey))
	                return client;
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
	        this.data = (List<Client>) list;
	    }

	    @Override
	    public Object getWrappedData() {
	        return data;
	    }
	}

