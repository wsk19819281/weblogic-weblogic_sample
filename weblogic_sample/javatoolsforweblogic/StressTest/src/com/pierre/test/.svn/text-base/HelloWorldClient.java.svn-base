package com.pierre.test;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import MyTest.HelloWorldPortType;
import MyTest.HelloWorldSOAP12BindingQSServiceLocator;

public class HelloWorldClient {
	
	public static void main(String[] args) throws ServiceException, RemoteException {
		HelloWorldClient client = new HelloWorldClient();
		client.runTest();
	}

	public void runTest() throws ServiceException, RemoteException {
		HelloWorldSOAP12BindingQSServiceLocator locator = new HelloWorldSOAP12BindingQSServiceLocator();
		HelloWorldPortType pt = locator.getHelloWorldSOAP12BindingQSPort();
		pt.helloWorld("vaffanculo");
	}
	
}
