package de.app.subh.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import de.app.subh.dataAccessObjects.DBReader;
import de.app.subh.models.Book;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
@Getter
@Setter
public class BookBean {

	@EJB
	private DBReader dbReader;
	
	private Book book;
	private int bookID;
	
	@ManagedProperty("#{loginBean}")
    private LoginBean loginBean;
	
	@PostConstruct
	public void init() {
		this.book = new Book();
	}
	
	public BookBean() {
	}
}
