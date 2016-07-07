package com.testws.ws;

import javax.jws.*;
import com.testws.data.Employee;


@WebService
public class MyTestWS {

	@WebMethod(action="helloAction")
	public void hello(Employee emp) {
		System.out.println(emp.getAge() + " " + emp.getName());
	}
	
	@WebMethod(action="generateFault1Action")
	public void generateFault1() throws Exception {
		System.out.println("generating fault1");
		throw new Exception("ciao1");
	}
	
	@WebMethod(action="generateFault2Action")
	public void generateFault2() throws Exception {
		System.out.println("generating fault2");
		throw new Exception("ciao2");
	}

}