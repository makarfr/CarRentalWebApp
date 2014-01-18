package convertors;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.CarModel;
import model.Client;
import dao.implementations.CarModelDAO;
import dao.interfaces.CarModelDAOInterface;
import dao.interfaces.ClientDAOInterface;

@FacesConverter(value = "carModelConverter")
public class CarModelConverter implements Converter {

	private static List<CarModel> list;
	
	@Override
	public Object getAsObject(FacesContext facesContext,
			UIComponent uiComponent, String s) {
		if (s.isEmpty()){
			return s;
		}
		String[] mass = s.split(" ");
		System.out.println(s);
		String modelName = mass[0];
		String modelDescription = mass[1];

		 CarModel result = getCarModelDao().findByModelNameAndDesc(modelName, modelDescription);
		//CarModel result = list.get(0);
		return result;

	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		String val = null;
		if (obj != null && (obj instanceof CarModel)) {
			CarModel carModel = (CarModel) obj;
			val = carModel.getCarModelName() + " "
					+ carModel.getCarModelDescription();

		}

		return val;

	}

	public static void getList(List<CarModel> listModels) {
		list = listModels;
	}
	
	private CarModelDAOInterface<CarModel> getCarModelDao() {
		 try {
	            Context c = new InitialContext();
	            CarModelDAOInterface<CarModel> dao =(CarModelDAOInterface<CarModel>) c.lookup("java:module/CarModelDAO");
	            return dao;
	        } catch (NamingException e) {
	            e.printStackTrace();
	            return null;
	        }
	}
}
