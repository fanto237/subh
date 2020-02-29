package de.app.subh.models;

import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import de.app.subh.models.enums.UserRole;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "User")
public class User implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private Integer id;

	@Size(min = 3, max=20, message = "Nachname benötigt mindestens 3 Zeichen")
    @Column(nullable = false, unique = true)
	private String nachname;
	
	@Size(min = 3, max=20, message = "Vorname benötigt mindestens 3 Zeichen")
    @Column(nullable = false, unique = true)
	private String vorname;
	
	@Size(min = 3, max=20, message = "Benutzer benötigt mindestens 3 Zeichen")
    @Column(nullable = false, unique = true)
	private String username;
	
	@Size(min = 3, max=20, message = "Vorname benötigt mindestens 3 Zeichen")
    @Column(nullable = false, unique = true)
	private String adresse;

	@Email(message = "Bitte korrekte E-Mail angeben")
	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;

	@Column(nullable = false)
	private String salt;
	
//	@Column(nullable = false, columnDefinition = "integer default 0")
//	private Integer numberOfBook;


	public User() {
		/*
		 * Alle Users sind mit der stantard Role "Normal-user" erstellt. Nur den Admin
		 * kann andere Role zuweisen.
		 */
		this.setRole(UserRole.NORMAL);
	}

	public User(String nachname, String vorname, String username, String adresse, String email, String password, UserRole role) {
		super();
		this.nachname = nachname;
		this.vorname = vorname;
		this.username = username;
		this.adresse = adresse;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	

	public boolean isSuperUser() {
		return this.role == UserRole.ADMIN || this.role == UserRole.STUDENT || this.role == UserRole.PROF;
	}

	public boolean isAdminUser() {
		return this.role == UserRole.ADMIN || this.role == UserRole.PROF;
	}

}
