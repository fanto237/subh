package de.app.subh.beans;

import java.util.ArrayList;
import java.util.Arrays;
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
import de.app.subh.models.enums.BookCategory;

@ManagedBean
@SessionScoped
public class BookListBean {

	@EJB
	private DBReader dbReader;

	@EJB
	private DBWriter dbWriter;

	private DataModel<Book> bookSearchResults;
	private static boolean test = false;
	private static BookCategory localCat;
	private String searchTerm;
	private Book choosedBook;

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	@PostConstruct
	public void init() {
		this.searchTerm = "";
		this.choosedBook = new Book();
		this.bookSearchResults = new ListDataModel<>(dbReader.findAllBook());
		this.bookSearchResults.setWrappedData(this.dbReader.findAllBook());
	}

	public BookListBean() {
	}

	public void search() {
		bookSearchResults = new ListDataModel<>();
		List<Book> results = new ArrayList<>();
		for (Book book : this.dbReader.findAllBook()) {
			if (book.getName().toLowerCase().contains(searchTerm.toLowerCase()))
				results.add(book);
		}
		bookSearchResults.setWrappedData(results);
	}

	public void filterCategory(BookCategory cat) {

		if (cat == null)
			bookSearchResults.setWrappedData(this.dbReader.findAllBook());
		else {
			bookSearchResults.setWrappedData(this.dbReader.findBookByCategory(cat));
			test = true;
			localCat = cat;
		}
	}

	public String borrow() {
		dbWriter.borrowBook(getSelectedBook(), loginBean.getUser());
		bookSearchResults.setWrappedData(dbReader.findAllBook());
		return "books.xhtml?faces-redirect=true";
	}
	
	public String borrowWithoutRow() {
		dbWriter.borrowBook(choosedBook, loginBean.getUser());
		bookSearchResults.setWrappedData(dbReader.findAllBook());
		return "books.xhtml?faces-redirect=true";
	}

	public String goTo() {
		choosedBook = getSelectedBook();
		System.out.println("Got to" + choosedBook.getId());
		return "bookDetails.xhtml?faces-redirect=true&id=" + choosedBook.getId();
	}
	
	public String comeBack() {
		bookSearchResults.setWrappedData(dbReader.findAllBook());
		return "/books.xhtml?faces-redirect=true";
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

	public static boolean isTest() {
		return test;
	}

	public static void setTest(boolean test) {
		BookListBean.test = test;
	}

	public static BookCategory getLocalCat() {
		return localCat;
	}

	public static void setLocalCat(BookCategory localCat) {
		BookListBean.localCat = localCat;
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
