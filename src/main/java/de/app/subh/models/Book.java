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
import lombok.Getter;
import lombok.Setter;

/**
 * Entity implementation class for Entity: Book
 *
 */
@Getter
@Setter
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
	}

}
