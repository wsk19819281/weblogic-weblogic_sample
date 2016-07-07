package com.pierre.socketpool;

import org.apache.commons.pool.PoolableObjectFactory;

public class PVPoolableObjectFactory implements PoolableObjectFactory {

	@Override
	public void activateObject(Object arg0) throws Exception {
		System.out.println("activateObject");
		
	}

	@Override
	public void destroyObject(Object arg0) throws Exception {
		System.out.println("destroyObject");
		
	}

	@Override
	public Object makeObject() throws Exception {
		System.out.println("makeObject");
		return null;
	}

	@Override
	public void passivateObject(Object arg0) throws Exception {
		System.out.println("passivateObject");
		
	}

	@Override
	public boolean validateObject(Object arg0) {
		System.out.println("validateObject");
		return false;
	}

}
