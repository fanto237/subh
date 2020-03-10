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
		user = dbReader.findSingleUserByName(userName);
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		if(user == null || !isCorrectPassword()){
			ctx.addMessage(null, new FacesMessage("Benutzer nicht gefunden oder Passwort falsch eingegeben !"));
            System.out.println("test");
            userName = "";
            userPassword = "";
            user = null;
            return null;
        }else{
            System.out.printf("user with id: %d and name: %s logged in", user.getId(), user.getUsername());
            ctx.getExternalContext().getSessionMap().put("user", user);
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

	public String forgotPasswordLink() {
		return "/forgotPassword.xhtml?faces-redirect=true";
	}

	public String registerLink() {
		return "/register.xhtml?faces-redirect=true";
	}


	public String loginLink() {
		return "/login.xhtml?faces-redirect=true";
	}
	
	
	// Setters and Getters

	public DBReader getDbReader() {
		return dbReader;
	}

	public void setDbReader(DBReader dbReader) {
		this.dbReader = dbReader;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
	

}
