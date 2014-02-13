package validators;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import common.I18nHelper;


@FacesValidator("dateGapValidator")
public class DateGapValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        UIInput fromTimeComponent = (UIInput) uiComponent.getAttributes().get("fromTimeComponent");
    //    UIInput toTimeComponent = (UIInput) uiComponent.getAttributes().get("toTimeComponent");
        
   //     System.out.println("fromTimeComponent " + fromTimeComponent.getSubmittedValue().toString());
  //     System.out.println("toTimeComponent " + toTimeComponent.getSubmittedValue().toString());
        Date dateFrom = (Date) fromTimeComponent.getValue();
        Date dateTo = (Date) o;

        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(dateFrom);

        Calendar calTo = Calendar.getInstance();
        calTo.setTime(dateTo);

       Calendar calMaxToDate = Calendar.getInstance();
    		  calMaxToDate.setTime(dateFrom);
    		  calMaxToDate.add(Calendar.DATE, 30); 

        if (calTo.before(calFrom)) {
        	System.out.println("in Validator days gap " + calTo.before(calFrom));
            makeErrorMessage(I18nHelper.INSTANCE.getI18Message("day_gap_validate_end_date_greater_from_date"));
        } else if (calTo.after(calMaxToDate)) {
            makeErrorMessage(I18nHelper.INSTANCE.getI18Message("day_gap_validate_period_less_30_days"));
        }
        Calendar now = Calendar.getInstance();
        if (calFrom.before(now) || calTo.before(now)) {
            makeErrorMessage(I18nHelper.INSTANCE.getI18Message("day_gap_validate_date_greater_today"));
        }
      
    }

    private static void makeErrorMessage(String message) {
        FacesMessage msg = new FacesMessage(null, message);
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(msg);
    }
   
}