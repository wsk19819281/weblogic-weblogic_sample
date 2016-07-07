package com.acme.geoosb.mqfeed;

import org.junit.Test;

import com.acme.slt.geoosb.javaclient.Employee;
import com.acme.slt.geoosb.javaclient.XMLLoader;

public class XMLLoaderTest {
	@Test
	public void testname() throws Exception {
		XMLLoader<Employee> loader = new XMLLoader<Employee>();
		Employee emp = loader.getObjectFromXMLFile(Employee.class, "testResources/Employees.xml");
		System.out.println(emp.getName() + " " + emp.getAge());
	}
}
