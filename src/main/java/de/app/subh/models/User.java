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

	@Size(min = 3, max = 20, message = "Nachname benötigt mindestens 3 Zeichen")
	@Column(nullable = false, unique = true)
	private String nachname;

	@Size(min = 3, max = 20, message = "Vorname benötigt mindestens 3 Zeichen")
	@Column(nullable = false, unique = true)
	private String vorname;

	@Size(min = 3, max = 20, message = "Benutzer benötigt mindestens 3 Zeichen")
	@Column(nullable = false, unique = true)
	private String username;

	@Size(min = 3, max = 20, message = "Vorname benötigt mindestens 3 Zeichen")
	@Column(nullable = false, unique = true)
	private String adresse;

	@Email(message = "Bitte korrekte E-Mail angeben")
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false, columnDefinition = "varchar(255) default 'ACTIVED'")
	private String status;

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
		 * All users who register from the Frontend get as default role "NORMAL". The
		 * user role can be changed later by the Admin or by creating the user in the
		 * backend.
		 * 
		 */
		this.setRole(UserRole.NORMAL);
	}

	public User(String nachname, String vorname, String username, String adresse, String email, String password,
			UserRole role) {
		super();
		this.nachname = nachname;
		this.vorname = vorname;
		this.username = username;
		this.adresse = adresse;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public boolean isNormalUser() {
		return this.role == UserRole.ADMIN;
	}

	public boolean isStudentUser() {
		return this.role == UserRole.STUDENT;
	}

	public boolean isAdminUser() {
		return this.role == UserRole.ADMIN || this.role == UserRole.PROF;
	}

	
	// Setters and Getters
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
