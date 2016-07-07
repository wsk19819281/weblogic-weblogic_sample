package com.acme.cache.persistence;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.acme.cache.domain.GeoBda;
import com.acme.cache.domain.GeoPostalArea;
import com.acme.cache.domain.GeoSite;
import com.acme.cache.domain.GeoSiteAddress;
import com.acme.cache.domain.GeographicArea;


public class GEOPersistenceHandler implements Serializable {

	public static final String ORA_00001_UNIQUE_CONSTRAINT = "ORA-00001";

	public static final String GEO_TIME_ZONE = "GeoTimeZone";
	public static final String GEO_SITE_PARENT = "GeoSiteParent";
	public static final String GEO_SITE_NAME_ALIA = "GeoSiteNameAlia";
	public static final String GEO_SITE_EXTENSION = "GeoSiteExtension";
	public static final String GEO_SITE_CODE_ALIA = "GeoSiteCodeAlia";
	public static final String GEO_SITE_ADDRESS = "GeoSiteAddress";
	public static final String GEO_SITE = "GeoSite";
	public static final String GEO_POSTAL_AREA = "GeoPostalArea";
	public static final String GEO_LATEST_TAG = "GeoLatestTag";
	public static final String GEOGRAPHIC_AREA = "GeographicArea";
	public static final String GEO_DAYLIGHT_SAVING_ZONE = "GeoDaylightSavingZone";
	public static final String GEO_DAYLIGHT_SAVING_DISPLACEME = "GeoDaylightSavingDisplaceme";
	public static final String GEO_BDA = "GeoBda";
	public static final String GEO_AREA_PARENTAGE = "GeoAreaParentage";
	public static final String GEO_AREA_NAME_ALIA = "GeoAreaNameAlia";
	public static final String GEO_AREA_CODE_ALIA = "GeoAreaCodeAlia";
	public static final String BDA_CONNECTOR_GEO_SITE = "BdaConnectorGeoSite";
	public static final String BDA_CONNECTOR_GEO_AREA = "BdaConnectorGeoArea";



	String[] tables = new String[] {BDA_CONNECTOR_GEO_AREA, BDA_CONNECTOR_GEO_SITE, GEO_AREA_CODE_ALIA,
			GEO_AREA_NAME_ALIA, GEO_AREA_PARENTAGE, GEO_BDA, GEO_DAYLIGHT_SAVING_DISPLACEME,
			GEO_DAYLIGHT_SAVING_ZONE, GEOGRAPHIC_AREA, GEO_LATEST_TAG, GEO_POSTAL_AREA,
			GEO_SITE, GEO_SITE_ADDRESS, GEO_SITE_CODE_ALIA, GEO_SITE_EXTENSION,
			GEO_SITE_NAME_ALIA, GEO_SITE_PARENT, GEO_TIME_ZONE};



	/** Default Serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** The Default Class LOGGER. */
	private static final Log LOGGER = LogFactory.getFactory().getInstance(
			GEOPersistenceHandler.class.getName());

	EntityManagerWrapper emw = new EntityManagerWrapper();





	/**
	 * Delete an existing instance of the Entity from the system.
	 */
	public void delete(Object object) {
		EntityManager theEM = emw.getEntityManager();
		theEM.getTransaction().begin();
		theEM.remove(object);
		theEM.getTransaction().commit();
		//em.close();
	}

	public void deleteAllTables() {
		EntityManager theEM = emw.getEntityManager();
		theEM.getTransaction().begin();
		for (String table : tables) {
			Query deleteAllQuery = theEM.createQuery("delete from " + table + " a");
			int count = deleteAllQuery.executeUpdate();
			LOGGER.info("deleted rows in " + table + " count = " + count);

		}
		theEM.getTransaction().commit();
	}

	public void deleteTable(String table) {
		EntityManager theEM = emw.getEntityManager();
		theEM.getTransaction().begin();
		Query deleteAllQuery = theEM.createQuery("delete from " + table + " a");
		int count = deleteAllQuery.executeUpdate();
		LOGGER.info("deleted rows in " + table + " count = " + count);
		theEM.getTransaction().commit();
	}


	public void countAllTables() {
		EntityManager theEM = emw.getEntityManager();
		for (String table : tables) {
			Query countAllQuery = theEM.createQuery("select count(a) from " + table + " a");
			Object count = countAllQuery.getSingleResult();
			LOGGER.info("counted rows in " + table + " count = " + count);

		}
	}


	// Everything below belongs in other Class(es), reproduced here for simplicity.


	/**
	 * Refresh the entity from database.
	 *
	 * @param TestEntity - abstract class (substituted at runtime) that is to be refreshed
	 */
	protected void refreshEntity(Object testEntity) {
		EntityManager theEM = emw.getEntityManager();
		try {
			theEM.refresh(testEntity);
		} catch(EntityNotFoundException ex){
			LOGGER.warn("Failed to refresh entity; this may not be a problem");
		}
	}

	/**
	 * Close the EntityManager instance.
	 */
	public void closeEM() {
		EntityManager theEM = emw.getEntityManager();
		theEM.close();
	}

	public static void main(String args[]) {

	}



	/**
	 * Updates and fail if not there
	 * @param country
	 * @throws Exception
	 */
	public void updateGeographicArea(GeographicArea geographicArea) throws Exception {
		EntityManager theEM = emw.getEntityManager();
		theEM.getTransaction().begin();
		GeographicArea previousBean = theEM.find(GeographicArea.class, geographicArea.getGeoid());
		if (previousBean == null) {
			throw new Exception("Unable to find geographicArea with geoId=" + geographicArea.getGeoid());
		}
		// do the actual update
		theEM.persist(geographicArea);
		theEM.getTransaction().commit();
		LOGGER.info("updated geographicArea with geoId=" + geographicArea.getGeoid());

	}

	/**
	 * Persist updated details of an existing instance of the Entity.
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public void updateEntity(Object object, String id) throws GEOPersistenceException, IllegalAccessException, InvocationTargetException {
		EntityManager theEM = emw.getEntityManager();
		theEM.getTransaction().begin();
		Object previousEntity = theEM.find(object.getClass(), id);
		if (previousEntity != null) {
			BeanUtils.copyProperties(previousEntity, object);
			theEM.merge(previousEntity);
		}
		else {
			throw new GEOPersistenceException("Unable to update " + object.getClass() + " with id " + id);
		}

		theEM.getTransaction().commit();
		/*
		// This next step should not be necessary; but sometimes update is not propagated !??
		theEM.getTransaction().begin();
		refreshEntity(object);
		theEM.getTransaction().commit();
		//em.close();
		 */
	}


	public void updateGeoPostalArea(GeoPostalArea gpa) throws Exception {
		EntityManager theEM = emw.getEntityManager();
		theEM.getTransaction().begin();
		GeoPostalArea previousBean = theEM.find(GeoPostalArea.class, gpa.getGeoid());
		if (previousBean == null) {
			throw new Exception("Unable to find geoPostalArea with geoId=" + gpa.getGeoid());
		}
		// do the actual update
		theEM.persist(gpa);
		theEM.getTransaction().commit();
		LOGGER.info("updated GeoPostalArea with geoId=" + gpa.getGeoid());

	}

	public void forceBDA(GeoBda bda) {
		persist(bda);
		LOGGER.info("forceCreated new GeoBda with SystemGeneratedId=" + bda.getSystemGeneratedI());
	}

	/**
	 * Force creation of any object in DB
	 * @param entity
	 * @param id
	 */
	public void forceEntity(Object entity, String id) {
		EntityManager theEM = emw.getEntityManager();
		theEM.getTransaction().begin();
		theEM.merge(entity);
		theEM.getTransaction().commit();
		LOGGER.info("forceCreated new " + entity.getClass().getName() + " with Id=" + id);
	}

	private void persist(Object object) {
		EntityManager theEM = emw.getEntityManager();
		theEM.getTransaction().begin();
		theEM.persist(object);
		theEM.getTransaction().commit();
	}

	public void updateBDA(GeoBda bda) throws Exception {
		EntityManager theEM = emw.getEntityManager();
		theEM.getTransaction().begin();
		GeoBda previousBean = theEM.find(GeoBda.class, bda.getSystemGeneratedI());
		if (previousBean == null) {
			throw new Exception("Unable to find GeoBda with geoId=" + bda.getSystemGeneratedI());
		}
		// do the actual update
		theEM.persist(bda);
		theEM.getTransaction().commit();
		LOGGER.info("updated GeoBda with geoId=" + bda.getSystemGeneratedI());

	}

	public void forceGeoSiteAddress(GeoSiteAddress gpa) {
		persist(gpa);
		LOGGER.info("forceCreated new GeoSiteAddress with Fkpoarid=" + gpa.getFkpoarid());
	}

	public void updateGeoSiteAddress(GeoSiteAddress gpa) throws Exception {
		EntityManager theEM = emw.getEntityManager();
		theEM.getTransaction().begin();
		GeoSiteAddress previousBean = theEM.find(GeoSiteAddress.class, gpa.getFkpoarid());
		if (previousBean == null) {
			throw new Exception("Unable to find GeoSiteAddress with Fkpoarid=" + gpa.getFkpoarid());
		}
		// do the actual update
		theEM.persist(gpa);
		theEM.getTransaction().commit();
		LOGGER.info("updated GeoPostalArea with Fkpoarid=" + gpa.getFkpoarid());

	}

	public void insertEntity(Object entity, String id) {
		EntityManager theEM = emw.getEntityManager();
		theEM.getTransaction().begin();
		theEM.persist(entity);
		theEM.getTransaction().commit();
		LOGGER.info("inserted " + entity.getClass().getName() + " with id=" + id);
	}

	public void deleteEntity(Object entity, String id) throws GEOPersistenceException {
		EntityManager theEM = emw.getEntityManager();
		theEM.getTransaction().begin();
		Class<? extends Object> class1 = entity.getClass();
		String className = class1.getName();
		Object myEntity = theEM.find(class1, id);
		if (myEntity != null) {
			theEM.remove(myEntity);
		}
		else {

			throw new GEOPersistenceException("unable to delete entity " + className + " with id " + id);
		}

		theEM.getTransaction().commit();
		LOGGER.info("deleted " + className + " with id=" + id);

	}

	/**
	 * Count the number of elements in a table
	 * @param string
	 * @return
	 */
	public int countTable(String table) {
		EntityManager theEM = emw.getEntityManager();
		Query countAllQuery = theEM.createQuery("select count(a) from " + table + " a");
		Object count = countAllQuery.getSingleResult();
		return Integer.parseInt(count.toString());
	}


	public Object findEntity(Object entity, String id) {
		EntityManager theEM = emw.getEntityManager();
		Object previousBean = theEM.find(entity.getClass(), id);
		return previousBean;
	}



}
