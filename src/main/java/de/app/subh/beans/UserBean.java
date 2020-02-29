package de.app.subh.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import de.app.subh.dataAccessObjects.DBReader;
import de.app.subh.dataAccessObjects.DBWriter;
import de.app.subh.models.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    
	
	@PostConstruct
	public void init() {
		String tmp = loginBean.getUser().getUsername();
		userBooks = dbReader.findBookByUser(tmp);
		borrowedBooks.setWrappedData(userBooks);
		
	}
	
	public UserBean() {
		this.userBooks = new ArrayList<Book>();
		this.borrowedBooks = new ListDataModel<>();
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
}
