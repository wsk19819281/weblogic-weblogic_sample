package com.pierre.socketpool;

import org.apache.commons.pool.impl.GenericObjectPool;

public class PVGenericObjectPool extends GenericObjectPool {
	
	public static void main(String[] args) throws Exception {
		PVPoolableObjectFactory factory = new PVPoolableObjectFactory();
		PVGenericObjectPool genericObjectPool  = new PVGenericObjectPool(factory);
		Object obj = genericObjectPool.borrowObject();
		System.out.println(obj);
	}
	
	
	public PVGenericObjectPool(PVPoolableObjectFactory factory ) {
		super(factory);
	}
	
	
}
