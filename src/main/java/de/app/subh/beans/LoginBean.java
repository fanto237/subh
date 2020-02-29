package de.app.subh.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;

import de.app.subh.dataAccessObjects.DBReader;
import de.app.subh.models.User;
import de.app.subh.utilities.EnryptionHelper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6121419509835829647L;

	@EJB
	private DBReader dbReader;

	@Size(min = 1, message = "Benutzername eingeben!")
	private String userName;
	@Size(min = 1, message = "Passwort eingeben!")
	private String userPassword;

	private User user;

	@PostConstruct
	public void init() {
	}

	public LoginBean() {
	}

	public String login() {
		System.out.println("login");
		FacesContext context = FacesContext.getCurrentInstance();
		user = dbReader.findSingleUserByName(userName);

		if(user == null || !isCorrectPassword()){
            context.addMessage(null, new FacesMessage("Keinen Benutzer gefunden, versuche es erneut!"));
            userName = "";
            userPassword = "";
            user = null;
            return null;
        }else{
            System.out.printf("user with id: %d and name: %s logged in", user.getId(), user.getUsername());
            context.getExternalContext().getSessionMap().put("user", user);
            return "/home.xhtml?faces-redirect=true";
        }
	}

	public String logout() {
		System.out.printf("user with id: %d and name: %s logged out", user.getId(), user.getUsername());
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		user = null;
		return "/login.xhtml?faces-redirect=true";
	}

//	public void logout() {
//		FacesContext context = FacesContext.getCurrentInstance();
//		context.getExternalContext().invalidateSession();
//		try {
//			context.getExternalContext().redirect("login.xhtml");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	private boolean isCorrectPassword() {
		return EnryptionHelper.hashPwAndSalt(userPassword, user.getSalt()).equals(user.getPassword());
	}

	public String info() {
		return "Sie haben bisher 0 Bücher in unseren Bibliothek ausgeliehen.";
	}

	public String forgotPasswordLink() {
		return "/forgotPassword.xhtml?faces-redirect=true";
	}

	public String registerLink() {
		return "/register.xhtml?faces-redirect=true";
	}


	public String loginLink() {
		return "/login.xhtml?faces-redirect=true";
	}

}
