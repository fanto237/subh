package de.app.subh.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;
import javax.validation.constraints.Size;

import de.app.subh.dataAccessObjects.DBWriter;
import de.app.subh.exceptions.UserEmailException;
import de.app.subh.exceptions.UserPasswordException;
import de.app.subh.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ManagedBean(name = "register")
@RequestScoped
public class RegisterBean {

	@EJB
    private DBWriter dbWriter;

    @Size(min = 6, message = "Passwort muss mindestens 6 Zeichen lang sein!")
    private String unhashedPassword;
    private String userConfirmPassword ="";
    private User dummyUser;

    @PostConstruct
    public void init() {}

    public RegisterBean() {
        dummyUser = new User();
    }

    public String register() {
        String redirectTo = "";
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (validateData()) {
            try {
  				dbWriter.createUser(dummyUser, unhashedPassword);
                redirectTo = "/login.xhtml?faces-redirect=true";
            } catch (EntityExistsException | UserPasswordException | UserEmailException e) {
                ctx.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                e.getMessage(), null));
            }
        }
        return redirectTo;
    }

    private boolean validateData() {
        boolean isValid = true;
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        // check passwordConfirm is the same as password
        if (!userConfirmPassword.equals(unhashedPassword)) {
            ctx.addMessage(null, new FacesMessage("Passwörter stimmen nicht überein"));
            isValid = false;
        }
        return isValid;
    }

}
