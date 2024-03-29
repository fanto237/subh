package de.app.subh.beans;

import java.util.ArrayList;
import java.util.Arrays;
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
import de.app.subh.models.enums.BookCategory;

@ManagedBean
@SessionScoped
public class BookListBean {

	@EJB
	private DBReader dbReader;

	@EJB
	private DBWriter dbWriter;

	private DataModel<Book> bookSearchResults;
	private String searchTerm;
	private Book choosedBook;

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	@PostConstruct
	public void init() {
		this.searchTerm = "";
		this.choosedBook = new Book();
		this.bookSearchResults = new ListDataModel<>(dbReader.findAllFreeBook());
		this.bookSearchResults.setWrappedData(this.dbReader.findAllFreeBook());
	}

	public BookListBean() {
	}

	public void search() {
		bookSearchResults = new ListDataModel<>();
		List<Book> results = new ArrayList<>();
		for (Book book : this.dbReader.findAllFreeBook()) {
			if (book.getName().toLowerCase().contains(searchTerm.toLowerCase()))
				results.add(book);
		}
		bookSearchResults.setWrappedData(results);
	}

	public void filterCategory(BookCategory cat) {

		if (cat == null)
			bookSearchResults.setWrappedData(this.dbReader.findAllFreeBook());
		else {
			bookSearchResults.setWrappedData(this.dbReader.findBookByCategory(cat));
		}
	}

	public String borrow() {
		if (loginBean.getUser().isNormalUser() && getAmountBook() == 3) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Sie können nicht mehr als 3 Bücher ausleihen !"));
			return "books.xhtml?faces-redirect=true";
		} else if (loginBean.getUser().isStudentUser() && getAmountBook() == 5) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Sie können nicht mehr als 5 Bücher ausleihen !"));
			return "books.xhtml?faces-redirect=true";
		} else {
			dbWriter.borrowBook(getSelectedBook(), loginBean.getUser());
			bookSearchResults.setWrappedData(dbReader.findAllFreeBook());
			return "books.xhtml?faces-redirect=true";
		}
	}

	public String borrowWithoutRow() {
		if (loginBean.getUser().isNormalUser() && getAmountBook() == 3) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Sie können nicht mehr als 3 Bücher ausleihen !"));
			return "books.xhtml?faces-redirect=true";
		} else if (loginBean.getUser().isStudentUser() && getAmountBook() == 5) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Sie können nicht mehr als 5 Bücher ausleihen !"));
			return "books.xhtml?faces-redirect=true";
		} else {
			dbWriter.borrowBook(choosedBook, loginBean.getUser());
			bookSearchResults.setWrappedData(dbReader.findAllFreeBook());
			return "books.xhtml?faces-redirect=true";
		}
	}

	public String goTo() {
		choosedBook = getSelectedBook();
		System.out.println("Got to" + choosedBook.getId());
		return "bookDetails.xhtml?faces-redirect=true&id=" + choosedBook.getId();
	}

	public String comeBack() {
		bookSearchResults.setWrappedData(dbReader.findAllFreeBook());
		return "/books.xhtml?faces-redirect=true";
	}

	private int getAmountBook() {
		return dbReader.findBookByUser(loginBean.getUser().getUsername()).size();
	}

	// Setters and Getters

	public Book getSelectedBook() {
		return bookSearchResults.getRowData();
	}

	public List<BookCategory> getCategoryEnumValues() {
		return new ArrayList<>(Arrays.asList(BookCategory.values()));
	}

	public DBReader getDbReader() {
		return dbReader;
	}

	public void setDbReader(DBReader dbReader) {
		this.dbReader = dbReader;
	}

	public DBWriter getDbWriter() {
		return dbWriter;
	}

	public void setDbWriter(DBWriter dbWriter) {
		this.dbWriter = dbWriter;
	}

	public DataModel<Book> getBookSearchResults() {
		return bookSearchResults;
	}

	public void setBookSearchResults(DataModel<Book> bookSearchResults) {
		this.bookSearchResults = bookSearchResults;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public Book getChoosedBook() {
		return choosedBook;
	}

	public void setChoosedBook(Book choosedBook) {
		this.choosedBook = choosedBook;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

}
