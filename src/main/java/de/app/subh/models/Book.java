package de.app.subh.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import de.app.subh.models.enums.BookCategory;

/**
 * Entity implementation class for Entity: Book
 *
 */

@Entity
@Table(name = "Book")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String shortDescription;

	@Column(nullable = false)
	private String author;

	@Column(nullable = false)
	private String publisher;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BookCategory category;

	@Column(nullable = false)
	private String publishDate;

	@Column(nullable = false, columnDefinition = "TEXT", length = 1000000)
	private String description;

	@Column(nullable = false, columnDefinition = "varchar(255) default 'Available'")
	private String status;
	
	@Column(nullable = true, columnDefinition = "varchar(255) default ''")
	String borrower;

	public Book() {
		super();
		this.setStatus("Available");
	}
	
	// Setters and Getters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public BookCategory getCategory() {
		return category;
	}

	public void setCategory(BookCategory category) {
		this.category = category;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

}
