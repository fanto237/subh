package de.app.subh.dataAccessObjects;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import de.app.subh.models.Book;
import de.app.subh.models.User;
import de.app.subh.models.enums.BookCategory;
import de.app.subh.models.enums.UserRole;

/**
 * to get Entities from the database
 * 
 * @author lucien and @author francine
 *
 */

@Named
@Stateless
public class DBReader {

	private EntityManager entityManager = DBInitiator.OBJECT.getEntityManager();

	public DBReader() {
	}

	/**
	 * return a list of all the user
	 * 
	 * @return list of user
	 */
	public List<User> findAllUser() {

		TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u WHERE u.role = :role1 OR u.role = :role2", User.class);
		q.setParameter("role1", UserRole.NORMAL);
		q.setParameter("role2", UserRole.STUDENT);
		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		} finally {
			// entityManager.close();
		}
	}

	/**
	 * find a user by his name
	 * 
	 * @param name of user
	 * @return selected user
	 */
	public User findSingleUserByName(final String name) {

		TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :name AND u.status = :status", User.class);
		q.setParameter("name", name);
		q.setParameter("status", "ACTIVED");
		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			// entityManager.close();
		}
	}

	/**
	 * find a use by his name or his email
	 * 
	 * @param name of user
	 * @param email of user
	 * @return selected user
	 */
	public User findUserByNameOrEmail(final String name, final String email) {

		TypedQuery<User> q = entityManager
				.createQuery("SELECT u FROM User u WHERE u.username = :name OR u.email = :email", User.class);
		q.setParameter("email", email);
		q.setParameter("name", name);
		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			// entityManager.close();
		}
	}
	
	
	//**************************************************************************************//

	/**
	 * return all books from the data base, which are available
	 *
	 * @return list of book
	 */
	public List<Book> findAllFreeBook() {
		String status = "Available";
		TypedQuery<Book> q = entityManager.createQuery("SELECT b FROM Book b WHERE b.status = :status", Book.class);
		q.setParameter("status", status);
		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		} finally {
			// entityManager.close();
		}
	}
	
	/**
	 * return all books, which are borrowed or available
	 * @return list of book
	 */
	
	public List<Book> findAllBook() {
		TypedQuery<Book> q = entityManager.createQuery("SELECT b FROM Book b WHERE b.status = :status1 OR b.status = :status2", Book.class);
		q.setParameter("status1", "Borrowed");
		q.setParameter("status2", "Available");
		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		}finally {
			// entityManager.close();
		}
	}
	
	
	/**
	 * return the books borrowed by an user on the basis of his name
	 * @param username of book
	 * @return list of book
	 */
	public List<Book> findBookByUser(String username){
		TypedQuery<Book> q = entityManager.createQuery("SELECT b FROM Book b WHERE b.borrower = :userName", Book.class);
		q.setParameter("userName", username);
		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		}finally {
		//	entityManager.close();
		}
	}
	
	
	/**
	 * return all books of a determinate category
	 * @param category of book
	 * @return list of book
	 */
	public List<Book> findBookByCategory(BookCategory category){
		TypedQuery<Book> q = entityManager.createQuery("SELECT b FROM Book b WHERE b.category = :category AND b.status = :status", Book.class);
		q.setParameter("category", category);
		q.setParameter("status", "Available");
		try {
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		}finally {
		//	entityManager.close();
		}
	}
	
	// Setters and Getters

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
}
