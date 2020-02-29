package de.app.subh.utilities;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class EmailValidator implements Validator {

    public void validate(FacesContext context, UIComponent
            component, Object toValidate) {
        boolean isValid = true;
        String value = null;

        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
        if (!(component instanceof UIInput)) {
            return;
        }
        if (null == toValidate) {
            return;
        }
        value = toValidate.toString();
        int atIndex = value.indexOf('@');
        if (atIndex < 0) {
            isValid = false;
        }
        else if (value.lastIndexOf('.') < atIndex) {
            isValid = false;
        }
        if ( !isValid ) {
            throw new ValidatorException(new FacesMessage("Invalid E-Mail format"));
        }
    }
}