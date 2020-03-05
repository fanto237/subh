package de.app.subh.dataAccessObjects;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityTransaction;

import de.app.subh.exceptions.UserEmailException;
import de.app.subh.exceptions.UserPasswordException;
import de.app.subh.models.Book;
import de.app.subh.models.User;
import de.app.subh.utilities.EnryptionHelper;

/**
 * class used for writing ( adding and deleting ) entities in the data base
 * 
 * @author lucien 
 *
 */

@Named
@Stateless
public class DBWriter {

	private DBReader dbReader = new DBReader();
	private EntityTransaction transaction = dbReader.getEntityManager().getTransaction();;

	public DBWriter() {}

	/**
	 * add a new user in the data base
	 * 
	 * @param user
	 */
	public void addUser(User user) {

		if (Objects.nonNull(user)) {
			transaction.begin();
			dbReader.getEntityManager().persist(user);
			transaction.commit();
		} else
			System.out.println("the user is null");
	}

	/**
	 * delete a user in the data base
	 * 
	 * @param user
	 */
	public void deleteUser(User user) {

		transaction.begin();
		dbReader.getEntityManager().remove(user);
		transaction.commit();
	}
	
	/**
	 * Add a new book in the data base
	 * @param book
	 */
	public void addBook(Book book) {
		
		if(Objects.nonNull(book)) {
			transaction.begin();
			dbReader.getEntityManager().persist(book);
			transaction.commit();
		}
		else
			System.out.println("the book is null");
	}
	
	/**
	 * delete a book in the data base
	 * 
	 * @param user
	 */
	public void deleteBook(Book book) {

		transaction.begin();
		dbReader.getEntityManager().remove(book);
		transaction.commit();
	}
	
	/**
	 * create a new user and examine if his data are right
	 * @param toCreate
	 * @param unhashedPassword
	 * @throws UserEmailException
	 * @throws UserPasswordException
	 * @throws EntityExistsException
	 */
	
	public void createUser(User toCreate, String unhashedPassword) throws UserEmailException, UserPasswordException, EntityExistsException {
		String email = toCreate.getEmail();
		if (email == null || email.trim().length() == 0) {
			throw new UserEmailException("E-Mail muss angegeben werden!");
		}

		if (unhashedPassword == null || unhashedPassword.length() < 6) {
			throw new UserPasswordException("Passwort muss mindestens 6 Zeichen lang sein!");
		}
		if (dbReader.findUserByNameOrEmail(toCreate.getUsername(), email) != null) {
			throw new EntityExistsException("Benutzer existiert bereits!");
		}
		
		toCreate.setSalt(EnryptionHelper.generateSalt());
		toCreate.setPassword(EnryptionHelper.hashPwAndSalt(unhashedPassword, toCreate.getSalt()));

		//persist the new user in the data base
		addUser(toCreate);
	}
	
	/**
	 * methode used when a user borrows one book
	 */
	public void borrowBook(Book bookMark, User userMark) {
		
		bookMark.setStatus("Borrowed");
		bookMark.setBorrower(userMark.getUsername());
		updateBook(bookMark);
		
	}
	
	public void giveBackBook(Book bookMark) {
		
		bookMark.setStatus("Available");
		bookMark.setBorrower("");
		updateBook(bookMark);
		
	}
	
	/**
	 * update a a book given as parameter
	 * @param book
	 */
	public void updateBook(final Book book) {
		if(Objects.nonNull(book)) {
			transaction.begin();
			dbReader.getEntityManager().merge(book);
//			dbReader.getEntityManager().persist(book);
			transaction.commit();
		}else
			System.out.println("the book is null !");
	}
	
	/**
	 * update a user given as parameter
	 * @param user
	 */
	public void updateUser(final User user) {
		if(Objects.nonNull(user)) {
			transaction.begin();
			dbReader.getEntityManager().merge(user);
			transaction.commit();
		}else
			System.out.println("the user is null !");
	}
	
	
	// Setters and Getters

	public DBReader getDbReader() {
		return dbReader;
	}

	public void setDbReader(DBReader dbReader) {
		this.dbReader = dbReader;
	}

	public EntityTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(EntityTransaction transaction) {
		this.transaction = transaction;
	}

}
