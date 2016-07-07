/**
 * HelloWorldSOAP12BindingQSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package MyTest;

public class HelloWorldSOAP12BindingQSServiceLocator extends org.apache.axis.client.Service implements MyTest.HelloWorldSOAP12BindingQSService {

    public HelloWorldSOAP12BindingQSServiceLocator() {
    }


    public HelloWorldSOAP12BindingQSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public HelloWorldSOAP12BindingQSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for HelloWorldSOAP12BindingQSPort
    private java.lang.String HelloWorldSOAP12BindingQSPort_address = "http://WCRBDKESP0203.crb.apmoller.net:7001/OSBProject1/ProxyService1";

    public java.lang.String getHelloWorldSOAP12BindingQSPortAddress() {
        return HelloWorldSOAP12BindingQSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String HelloWorldSOAP12BindingQSPortWSDDServiceName = "HelloWorldSOAP12BindingQSPort";

    public java.lang.String getHelloWorldSOAP12BindingQSPortWSDDServiceName() {
        return HelloWorldSOAP12BindingQSPortWSDDServiceName;
    }

    public void setHelloWorldSOAP12BindingQSPortWSDDServiceName(java.lang.String name) {
        HelloWorldSOAP12BindingQSPortWSDDServiceName = name;
    }

    public MyTest.HelloWorldPortType getHelloWorldSOAP12BindingQSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HelloWorldSOAP12BindingQSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHelloWorldSOAP12BindingQSPort(endpoint);
    }

    public MyTest.HelloWorldPortType getHelloWorldSOAP12BindingQSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            MyTest.HelloWorldSOAP12BindingStub _stub = new MyTest.HelloWorldSOAP12BindingStub(portAddress, this);
            _stub.setPortName(getHelloWorldSOAP12BindingQSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHelloWorldSOAP12BindingQSPortEndpointAddress(java.lang.String address) {
        HelloWorldSOAP12BindingQSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (MyTest.HelloWorldPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                MyTest.HelloWorldSOAP12BindingStub _stub = new MyTest.HelloWorldSOAP12BindingStub(new java.net.URL(HelloWorldSOAP12BindingQSPort_address), this);
                _stub.setPortName(getHelloWorldSOAP12BindingQSPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("HelloWorldSOAP12BindingQSPort".equals(inputPortName)) {
            return getHelloWorldSOAP12BindingQSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://MyTest", "HelloWorldSOAP12BindingQSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://MyTest", "HelloWorldSOAP12BindingQSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("HelloWorldSOAP12BindingQSPort".equals(portName)) {
            setHelloWorldSOAP12BindingQSPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
