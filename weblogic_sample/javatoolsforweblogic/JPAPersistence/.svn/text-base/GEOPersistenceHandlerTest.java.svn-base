package com.acme.cache.persistence;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.acme.cache.domain.GeographicArea;

import static com.acme.cache.persistence.GEOPersistenceHandler.*;

/**
 * tester class for GEOPersistenceHandler
 * @author PVE033
 *
 */
public class GEOPersistenceHandlerTest {

	DummyDataGenerator dummyDataGenerator = new DummyDataGenerator();


	@Test
	public void testUpdateEntity() throws GEOPersistenceException, IllegalAccessException, InvocationTargetException {
		GEOPersistenceHandler testEntityHandler = new GEOPersistenceHandler();
		testEntityHandler.deleteTable(GEOGRAPHIC_AREA);

		GeographicArea geographicArea = dummyDataGenerator.getDummyGeographicArea();
		// test one: entity not yet in DB
		String geoid = geographicArea.getGeoid();
		boolean exc = false;
		try {
			testEntityHandler.updateEntity(geographicArea, geoid);
		}
		catch (Exception e) {
			exc = true;
		}
		assertTrue(exc);

		// now we insert it, and we update it
		testEntityHandler.insertEntity(geographicArea, geoid);

		geographicArea.setCityname("MARANELLO");
		testEntityHandler.updateEntity(geographicArea, geoid);
		GeographicArea maranello = (GeographicArea)testEntityHandler.findEntity(geographicArea, geoid);
		assertNotNull(maranello);
		assertEquals("MARANELLO", maranello.getCityname());

	}

	@Test
	public void testForceEntity() {
		GEOPersistenceHandler testEntityHandler = new GEOPersistenceHandler();
		testEntityHandler.deleteTable(GEOGRAPHIC_AREA);

		GeographicArea geographicArea = dummyDataGenerator.getDummyGeographicArea();
		// test one: entity not yet in DB
		String geoid = geographicArea.getGeoid();
		testEntityHandler.forceEntity(geographicArea, geoid);
		Object entity = testEntityHandler.findEntity(geographicArea, geoid);
		assertNotNull(entity);

		// now we change the entity and merge again.... it should be updated!
		geographicArea.setCityname("MARANELLO");
		testEntityHandler.forceEntity(geographicArea, geoid);
		GeographicArea maranello = (GeographicArea)testEntityHandler.findEntity(geographicArea, geoid);
		assertNotNull(maranello);
		assertEquals("MARANELLO", maranello.getCityname());


	}

	@Test
	public void testDeleteEntity() throws GEOPersistenceException {
		GEOPersistenceHandler testEntityHandler = new GEOPersistenceHandler();
		testEntityHandler.deleteTable(GEOGRAPHIC_AREA);

		// insert dummy data
		GeographicArea geographicArea = dummyDataGenerator.getDummyGeographicArea();
		testEntityHandler.insertEntity(geographicArea, geographicArea.getGeoid());
		// verify it's there
		Object bean = testEntityHandler.findEntity(geographicArea, geographicArea.getGeoid());
		assertNotNull(bean);

		testEntityHandler.deleteEntity(geographicArea, geographicArea.getGeoid());
		// verify it's not there
		bean = testEntityHandler.findEntity(geographicArea, geographicArea.getGeoid());
		assertNull(bean);

	}

	@Test
	public void testInsertEntity() {
		GEOPersistenceHandler testEntityHandler = new GEOPersistenceHandler();
		testEntityHandler.deleteTable(GEOGRAPHIC_AREA);

		GeographicArea geographicArea = dummyDataGenerator.getDummyGeographicArea();
		testEntityHandler.insertEntity(geographicArea, geographicArea.getGeoid());
		int count = testEntityHandler.countTable(GEOGRAPHIC_AREA);
		assertEquals(1, count);

		// subsequent insert of same entity should fail
		boolean exc = false;
		try {
			testEntityHandler.insertEntity(geographicArea, geographicArea.getGeoid());
		}
		catch (Exception e) {
			// unique constraint
			if (e.toString().contains(ORA_00001_UNIQUE_CONSTRAINT)) {
				exc = true;
			}
		}
		assertEquals(true, exc);

	}


}
