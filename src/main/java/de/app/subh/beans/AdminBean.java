package de.app.subh.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import de.app.subh.dataAccessObjects.DBReader;
import de.app.subh.dataAccessObjects.DBWriter;
import de.app.subh.models.Book;
import de.app.subh.models.User;
import de.app.subh.models.enums.BookCategory;
import de.app.subh.models.enums.UserRole;

@ManagedBean
@SessionScoped
public class AdminBean {

	@EJB
	private DBReader dbReader;

	@EJB
	private DBWriter dbWriter;

	private DataModel<Book> bookSearchResults;
	private Book choosedBook;

	private DataModel<User> userSearchResults;

	private String searchTerm;

	@PostConstruct
	public void init() {

		this.searchTerm = "";
		bookSearchResults.setWrappedData(dbReader.getAllBook());
		userSearchResults.setWrappedData(dbReader.findAllUser());

	}

	public AdminBean() {
		bookSearchResults = new ListDataModel<>();
		userSearchResults = new ListDataModel<>();
		this.choosedBook = new Book();
	}

//	public void clearAll() {
//		this.searchTerm = "";
//		this.bookSearchResults = null;
//		this.userSearchResults = null;
//		this.dbReader = null;
//		this.dbWriter = null;
//	}

	// Book administration

	/**
	 * look for a book inside the table
	 */
	public void searchBook() {
		bookSearchResults = new ListDataModel<>();
		List<Book> results = new ArrayList<>();
		for (Book book : this.dbReader.getAllBook()) {
			if (book.getName().toLowerCase().contains(searchTerm.toLowerCase()))
				results.add(book);
		}
		bookSearchResults.setWrappedData(results);
	}

	/**
	 * sort the books inside the table by it categories
	 * 
	 * @param cat
	 */
	public void filterCategory(BookCategory cat) {

		bookSearchResults = new ListDataModel<>();
		List<Book> results = new ArrayList<>();
		for (Book book : this.dbReader.getAllBook()) {
			if (book.getCategory().equals(cat))
				results.add(book);
		}
		bookSearchResults.setWrappedData(results);
	}

	/**
	 * insert a new book in the data base and in the table of book as well
	 * 
	 * @param book
	 * @return
	 */
	public String addBook(Book book) {

		bookSearchResults = new ListDataModel<>();
		if (book == null)
			System.out.println("es wurde kein Buch hinzugefügt !");
		else {
			this.dbWriter.addBook(book);
		}
		bookSearchResults.setWrappedData(dbReader.getAllBook());

		return "/adminpage.xhtml?faces-redirect=true";
	}

	/**
	 * modify a selected Book
	 * 
	 * @return
	 */
	public String modifyBook() {
		choosedBook = getSelectedBook();

		if (choosedBook.getStatus().equals("AVAILABLE")) {
			return "/editBook.xhtml?faces-redirect=true&id=" + choosedBook.getId();
		} else {
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(null,
					new FacesMessage("Das Book is momentan ausgelieht und kann nicht bearbeitet werden !"));
		}
		return "/adminpage.xhtml?faces-redirect=true";
	}

	/**
	 * save the modified book in the data base
	 * 
	 * @param book
	 * @return
	 */
	public String saveBook(Book book) {
		dbWriter.updateBook(book);
		bookSearchResults.setWrappedData(dbReader.getAllBook());
		return "/adminpage.xhtml?faces-redirect=true";
	}

	/**
	 * delete a selected book
	 * 
	 * @return
	 */
	public String deleteBook() {
		dbWriter.deleteBook(getSelectedBook());
		bookSearchResults.setWrappedData(dbReader.getAllBook());
		return "/adminpage.xhtml?faces-redirect=true";
	}

	public Book getSelectedBook() {
		return bookSearchResults.getRowData();
	}

	// user administration

	/**
	 * look for any user by his name
	 */
	public void searchUser() {
		userSearchResults = new ListDataModel<>();
		List<User> results = new ArrayList<>();
		for (User user : this.dbReader.findAllUser()) {
			if (user.getUsername().toLowerCase().contains(searchTerm.toLowerCase()))
				results.add(user);
		}
		userSearchResults.setWrappedData(results);
	}

	/**
	 * edit all users in the data base at once
	 * 
	 * @return
	 */
	public String saveUser() {
		dbWriter.updateUser(getSelectedUser());
		userSearchResults.setWrappedData(dbReader.findAllUser());
		return "/adminpage.xhtml?faces-redirect=true";
	}


	/**
	 * delete a selected user
	 * 
	 * @return
	 */
	public String deleteUser() {
		dbWriter.deleteUser(getSelectedUser());
		userSearchResults.setWrappedData(dbReader.findAllUser());
		return "/adminpage.xhtml?faces-redirect=true";
	}

	public int getAmountOfBook(User user) {
		return this.dbReader.findBookByUser(user.getUsername()).size();
	}

	public List<BookCategory> getCategoryEnumValues() {
		return new ArrayList<>(Arrays.asList(BookCategory.values()));
	}

	public List<UserRole> getRoleEnumValues() {
		return new ArrayList<>(Arrays.asList(UserRole.values()));
	}

	public List<String> getKontoStatus() {
		ArrayList<String> status = new ArrayList<>();
		status.add("ACTIVED");
		status.add("DESACTIVED");
		return status;
	}

	// Setters and Getters

	public User getSelectedUser() {
		return userSearchResults.getRowData();
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

	public Book getChoosedBook() {
		return choosedBook;
	}

	public void setChoosedBook(Book choosedBook) {
		this.choosedBook = choosedBook;
	}

	public DataModel<User> getUserSearchResults() {
		return userSearchResults;
	}

	public void setUserSearchResults(DataModel<User> userSearchResults) {
		this.userSearchResults = userSearchResults;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

}
