package validators;

import model.Car;
import model.Contract;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


@FacesValidator("dateGapValidator")
public class DateGapValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        UIInput fromTimeComponent = (UIInput) uiComponent.getAttributes().get("fromTimeComponent");
        Date dateFrom = (Date) fromTimeComponent.getValue();
        Date dateTo = (Date) o;

        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(dateFrom);

        Calendar calTo = Calendar.getInstance();
        calTo.setTime(dateTo);

        Car car = (Car) uiComponent.getAttributes().get("car");

        if (calTo.before(calFrom)) {
            makeErrorMessage("End date should be grater or equals to start date");
        } else if (!compareDatesTo30DaysGap(calFrom, calTo)) {
            makeErrorMessage("Rent period mast be less then 30 days");
        }
      
    }

    private static void makeErrorMessage(String message) {
        FacesMessage msg = new FacesMessage(null, message);
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(msg);
    }
    
    public  boolean compareDatesTo30DaysGap(Calendar from, Calendar to) {
        int days = Days.daysBetween(new DateTime(from), new DateTime(to)).getDays();
        return days < 30;
    }
}