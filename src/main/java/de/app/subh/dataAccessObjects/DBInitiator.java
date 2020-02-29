package de.app.subh.dataAccessObjects;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lombok.Getter;

/**
 * create the connection to the data base using the persistence.xml file
 * 
 * @author lucien
 *
 */

@Getter
public enum DBInitiator {
	
	OBJECT;

	private EntityManagerFactory entityManagerFactory;
	
	/**
	 * constructor used for running the initEntityManager
	 */
	DBInitiator() {
		entityManagerFactory = Persistence.createEntityManagerFactory("subh-unit");
	}

	/**
	 * method used to created the entity manager 
	 */
	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
	
	/**
	 * method used for closing of the entity manager factory
	 */
	public void closeEntityFactory() {
		if(Objects.nonNull(entityManagerFactory))
			entityManagerFactory.close();
	}

}
