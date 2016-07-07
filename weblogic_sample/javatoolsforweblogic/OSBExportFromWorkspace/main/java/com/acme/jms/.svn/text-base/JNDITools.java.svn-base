package com.ictra.jms;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDITools {
	public static Context createInitialContext() throws NamingException {
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		ht.put(Context.PROVIDER_URL, "t3://iinfblockbcl003.msnet.railb.be:7009");    	
		Context ctx = new InitialContext(ht);
		return ctx;
	}

}
