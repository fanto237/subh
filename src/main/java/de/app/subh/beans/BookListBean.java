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
	private String searchTerm;
	private BookCategory category;
	
	@ManagedProperty("#{loginBean}")
    private LoginBean loginBean;
	
	
	@PostConstruct
	public void init() {
		this.searchTerm = "";
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
		for(Book book : allBooks) {
			if(book.getName().toLowerCase().contains(searchTerm.toLowerCase()))
				results.add(book);
		}
		bookSearchResults.setWrappedData(results);
	}
	
	public void filterCategory(BookCategory cat) {
		if(cat == null)
			bookSearchResults.setWrappedData(allBooks);
		else {
			bookSearchResults = new ListDataModel<>();
			List<Book> results = new ArrayList<>();
			for(Book book : allBooks) {
				if(book.getCategory().equals(cat))
					results.add(book);
			}
			bookSearchResults.setWrappedData(results);
		}
	}
	
	public String borrow() {
		dbWriter.borrowBook(getSelectedBook(), loginBean.getUser());
		allBooks = dbReader.findAllBook();
		bookSearchResults.setWrappedData(allBooks);
		return "books.xhtml?faces-redirect=true";
	}
	
	
	
	public String goTo(Book book) {
        return "/books.xhtml?faces-redirect=true&id=" + book.getId();
    }
	
	public Book getSelectedBook() {
		return bookSearchResults.getRowData();
	}
}
