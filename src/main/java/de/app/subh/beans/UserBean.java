package de.app.subh.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import de.app.subh.dataAccessObjects.DBReader;
import de.app.subh.dataAccessObjects.DBWriter;
import de.app.subh.models.Book;
import de.app.subh.models.User;
import de.app.subh.utilities.EnryptionHelper;

@ManagedBean
@SessionScoped
public class UserBean {

	@EJB
	private DBWriter dbWriter;

	@EJB
	private DBReader dbReader;

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	private List<Book> userBooks;
	
	private DataModel<Book> borrowedBooks;

	private User currentUser;
	
	private String oldPassword;

	private String newPasswort;

	@PostConstruct
	public void init() {
		String tmp = loginBean.getUser().getUsername();
		userBooks = dbReader.findBookByUser(tmp);
		currentUser = loginBean.getUser();
		borrowedBooks.setWrappedData(userBooks);

	}

	public UserBean() {
		this.userBooks = new ArrayList<Book>();
		this.borrowedBooks = new ListDataModel<>();
		this.currentUser = new User();
	}

//	public List<Book> borrowedBook(){
//		return ;
//	}

	public String giveBack() {
		dbWriter.giveBackBook(getSelectedBook());
		userBooks = dbReader.findBookByUser(loginBean.getUser().getUsername());
		borrowedBooks.setWrappedData(userBooks);
		return "profile.xhtml?faces-redirect=true";
	}

	public Book getSelectedBook() {
		return borrowedBooks.getRowData();
	}

	public String profileLink() {
		userBooks = dbReader.findBookByUser(loginBean.getUser().getUsername());
		borrowedBooks.setWrappedData(userBooks);
		return "/profile.xhtml?faces-redirect=true";
	}

	public String modifyProfil() {
		return "/editProfil.xhtml?faces-redirect=true&id=" + loginBean.getUser().getId();
	}

	public String saveProfil() {

		if (!newPasswort.equals("")) {
			if (isCorrectPassword()) {
				currentUser.setSalt(EnryptionHelper.generateSalt());
				currentUser.setPassword(EnryptionHelper.hashPwAndSalt(newPasswort, currentUser.getSalt()));
				dbWriter.updateUser(currentUser);
				return logout();
			} else {
				FacesContext ctx = FacesContext.getCurrentInstance();
				ctx.addMessage(null, new FacesMessage("Falsches Passwort eingegen !"));
			}
		} else {
			System.out.println("es wurde keine passwort eingegen !");
		}
		
		dbWriter.updateUser(currentUser);

		return "/profile.xhtml?faces-redirect=true";
	}
	
	public String desactiveAccount() {
		
		if(userBooks.size() == 0) {
			currentUser.setStatus("DESACTIVED");
			dbWriter.updateUser(currentUser);
			return logout();
		}else {
			FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Sie müssen erst alle Bücher zurückgeben !"));
		}
		return profileLink();
	}

	private boolean isCorrectPassword() {
		return EnryptionHelper.hashPwAndSalt(oldPassword, currentUser.getSalt()).equals(currentUser.getPassword());
	}

	public String logout() {
		System.out.printf("user with id: %d and name: %s logged out", currentUser.getId(), currentUser.getUsername());
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		currentUser = null;
		return "/login.xhtml?faces-redirect=true";
	}

	// Setters and Getters

	public DBWriter getDbWriter() {
		return dbWriter;
	}

	public void setDbWriter(DBWriter dbWriter) {
		this.dbWriter = dbWriter;
	}

	public DBReader getDbReader() {
		return dbReader;
	}

	public void setDbReader(DBReader dbReader) {
		this.dbReader = dbReader;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public List<Book> getUserBooks() {
		return userBooks;
	}

	public void setUserBooks(List<Book> userBooks) {
		this.userBooks = userBooks;
	}

	public DataModel<Book> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setBorrowedBooks(DataModel<Book> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getUnhashedPassword() {
		return newPasswort;
	}

	public void setUnhashedPassword(String unhashedPassword) {
		this.newPasswort = unhashedPassword;
	}

	public String getNewPasswort() {
		return newPasswort;
	}

	public void setNewPasswort(String newPasswort) {
		this.newPasswort = newPasswort;
	}


}
