package de.app.subh.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import de.app.subh.dataAccessObjects.DBReader;
import de.app.subh.dataAccessObjects.DBWriter;
import de.app.subh.models.Book;
import de.app.subh.models.enums.BookCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ManagedBean
@RequestScoped
public class BookListBean {

	@EJB
	private DBReader dbReader;

	@EJB
	private DBWriter dbWriter;

	private List<Book> allBooks;
	private DataModel<Book> bookSearchResults;
	private static boolean test = false;
	private static BookCategory localCat;
	private String searchTerm;
	private Book book;

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	@PostConstruct
	public void init() {
		this.searchTerm = "";
		this.book = new Book();
		this.allBooks = dbReader.findAllBook();
		this.bookSearchResults = new ListDataModel<>();
		this.bookSearchResults.setWrappedData(this.allBooks);
	}

	public BookListBean() {
	}

	public List<BookCategory> getCategoryEnumValues() {
		return new ArrayList<>(Arrays.asList(BookCategory.values()));
	}

	public void search() {
		bookSearchResults = new ListDataModel<>();
		List<Book> results = new ArrayList<>();
		for (Book book : allBooks) {
			if (book.getName().toLowerCase().contains(searchTerm.toLowerCase()))
				results.add(book);
		}
		bookSearchResults.setWrappedData(results);
	}

	public void filterCategory(BookCategory cat) {

		if (cat == null)
			bookSearchResults.setWrappedData(allBooks);
		else {
			bookSearchResults = new ListDataModel<>();
//			List<Book> results = new ArrayList<>();
//			for (Book book : dbReader.findAllBook()) {
//				if (book.getCategory().equals(cat))
//					results.add(book);
//			}
			bookSearchResults.setWrappedData(dbReader.findBookByCategory(cat));
			test = true;
			localCat = cat;
		}
	}

	public String borrow(Book tmp) {

//		if (test) {
//			bookSearchResults.setWrappedData(dbReader.findBookByCategory(localCat));
//			tmp = bookSearchResults.getRowData();
//		} else
//			tmp = bookSearchResults.getRowData();
		dbWriter.borrowBook(tmp, loginBean.getUser());
		bookSearchResults.setWrappedData(dbReader.findAllBook());
		return "books.xhtml?faces-redirect=true";
	}

	public String goTo(Book book) {
		this.book = book;
		return "/books.xhtml?faces-redirect=true&id=" + book.getId();
	}

	public Book getSelectedBook() {
		return bookSearchResults.getRowData();
	}
}
