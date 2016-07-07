package com.acme.jmxtest;

import javax.management.*;
import java.lang.management.*;

/**
 * Startup and activates MBeanAgent
 * Run with:
 * -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1617 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
 * @author PVE033
 *
 */

public class SimpleAgent {
	   private MBeanServer mbs = null;

	   public SimpleAgent() {

	      // Get the platform MBeanServer
	       mbs = ManagementFactory.getPlatformMBeanServer();

	      // Unique identification of MBeans
	      Hello helloBean = new Hello();
	      ObjectName helloName = null;

	      try {
	         // Uniquely identify the MBeans and register them with the platform MBeanServer 
	         helloName = new ObjectName("FOO:name=HelloBean");
	         mbs.registerMBean(helloBean, helloName);
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	   }

	   // Utility method: so that the application continues to run
	   private static void waitForEnterPressed() {
	      try {
	         System.out.println("Press  to continue...");
	         System.in.read();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	    }

	   public static void main(String argv[]) {
	      SimpleAgent agent = new SimpleAgent();
	      System.out.println("SimpleAgent is running...");
	      SimpleAgent.waitForEnterPressed();
	   }

}
