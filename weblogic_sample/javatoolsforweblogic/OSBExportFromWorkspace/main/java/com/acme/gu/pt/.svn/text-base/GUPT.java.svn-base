package com.ictra.gu.pt;

import org.junit.Test;

import utils.getProperty;
import static com.ictra.gu.conf.GUPropertyReader.*;

import com.ictra.gu.GUHelper;
import static com.ictra.gu.GUHelper.*;
import static org.junit.Assert.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class GUPT implements Runnable {
	public int TEST_DURATION = 60;
	public int AVERAGE_EXECUTION_TIME_GLOBAL = 1600;
	public int NUMBER_OF_SLOW_EXECUTIONS = 10;
	public int THRESHOLD_FOR_SLOW_EXECUTION = 2000;
	public int AVERAGE_EXECUTION_TIME_INDIVIDUAL = 1600;

	public int THINK_TIME = 100;
	public int EXTRA_THREAD_DELAY = 500;
	public int NUMBER_OF_ITERATIONS = 1;
	public int NUMBER_OF_THREADS = 1;
	public boolean SINGLESHOT = true; 

	public String FACADE_URL = "/InterfacesGU/GUAnyXMLFacade";
	public String HOST = "";
	
	private String threadName = "";
	
	public void setThreadName(String name) {
		this.threadName = name;
	}

	static GUHelper helper = new GUHelper();
	static final PerformanceMeasurement performanceMeasurement = new PerformanceMeasurement();

	
	public static final String[] testEvents = new String[] {testEventTrack, testEventETA, testEventRouteModified, testEventRouteCanceled, testEventRTI, testEventRTP};
	//public static final String[] testEvents = new String[] {testEventETA};

	public GUPT() throws Exception {
		THINK_TIME = getIntProperty("GUPT_THINK_TIME");
		EXTRA_THREAD_DELAY = getIntProperty("GUPT_EXTRA_THREAD_DELAY");
		NUMBER_OF_ITERATIONS = getIntProperty("GUPT_NUMBER_OF_ITERATIONS");
		NUMBER_OF_THREADS = getIntProperty("GUPT_NUMBER_OF_THREADS");
		SINGLESHOT = getBooleanProperty("GUPT_SINGLESHOT");
		AVERAGE_EXECUTION_TIME_GLOBAL = getIntProperty("GUPT_AVERAGE_EXECUTION_TIME_GLOBAL");
		NUMBER_OF_SLOW_EXECUTIONS = getIntProperty("GUPT_NUMBER_OF_SLOW_EXECUTIONS");
		THRESHOLD_FOR_SLOW_EXECUTION = getIntProperty("GUPT_THRESHOLD_FOR_SLOW_EXECUTION");
		AVERAGE_EXECUTION_TIME_INDIVIDUAL = getIntProperty("GUPT_AVERAGE_EXECUTION_TIME_INDIVIDUAL");
		TEST_DURATION = getIntProperty("GUPT_TEST_DURATION");
		
		FACADE_URL = getStringProperty("FACADE_URL");
		HOST = getStringProperty("HOST");
	}
	

	@Test
	public void hudsonTest() throws Exception {
		helper.setVERBOSE(false);
		runTestWithExecutor();
		log("performanceMeasurement.averageExecutionTime " + performanceMeasurement.averageExecutionTime());
		for (RecordByType recordByType : performanceMeasurement.getRecordByTypes()) {
			logf("recordByType.averageExecutionTime for type %s = %d \n", recordByType.getType(), recordByType.averageExecutionTime());
			logf("numberOfRecordsAboveThreshold  for type  %s = %d \n", recordByType.getType(), + recordByType.numberOfRecordsAboveThreshold(THRESHOLD_FOR_SLOW_EXECUTION));
		}
		assertTrue("global averageExecutionTime", performanceMeasurement.averageExecutionTime() < AVERAGE_EXECUTION_TIME_GLOBAL);
		for (RecordByType recordByType : performanceMeasurement.getRecordByTypes()) {
			assertTrue("individual averageExecutionTime for type "  + recordByType.getType(), recordByType.averageExecutionTime() < AVERAGE_EXECUTION_TIME_INDIVIDUAL);
			assertTrue("execution threshold  for type " + recordByType.getType(), recordByType.numberOfRecordsAboveThreshold(2000) < NUMBER_OF_SLOW_EXECUTIONS);
		}

	}

/**
 * New style test, using ExecutorService and thread pool
 * @throws Exception
 */
	public void runTestWithExecutor() throws Exception {
		// create ExecutorService to manage threads                        
		ExecutorService threadExecutor = Executors.newFixedThreadPool( NUMBER_OF_THREADS );

		// ramp up
		for (int i = 1 ; i <= NUMBER_OF_THREADS; i++) {
			GUPT gupt = new GUPT();
			gupt.setThreadName("GUPT_" + i);
			logf("starting thread number %d\n", i);
			threadExecutor.execute(gupt);
			Thread.sleep(EXTRA_THREAD_DELAY);
		}
		// measure only from steady state
		performanceMeasurement.reset();
		log("Steady state reached, waiting for test completion");
		boolean timedOut = threadExecutor.awaitTermination(TEST_DURATION, TimeUnit.SECONDS);
		log("test completed");
	}



	public static void main(String[] args) throws Exception {
		GUPT gupt = new GUPT();
		gupt.setThreadName("GUPT_MAIN");
		gupt.doTest();
	}

	/**
	 * Old style test, where individual threads are created 
	 * @throws Exception
	 */
	public void doTest() throws Exception {
		if (SINGLESHOT) {
			NUMBER_OF_ITERATIONS = 1;
			NUMBER_OF_THREADS = 1;
		}
		for (int i = 1 ; i <= NUMBER_OF_THREADS; i++) {
			GUPT gupt = new GUPT();
			gupt.setThreadName("GUPT_" + i);
			Thread thread = new Thread(gupt);
			thread.setName("GUPT-" + i);
			thread.start();
			Thread.sleep(EXTRA_THREAD_DELAY);
		}
	}



	@Override
	public void run() {
		try {
			for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
				for (String testEvent : testEvents) {
					long now = System.currentTimeMillis(); 
					helper.invokeGU(testEvent, "http://" + HOST + FACADE_URL);
					long elapsed = (System.currentTimeMillis() - now);
					performanceMeasurement.record(helper.getTypeFromNisNotificationEvent(testEvent), elapsed);
					Thread.sleep(THINK_TIME);
				}
			}	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void logf(String format, Object... args) {
		System.out.print(Thread.currentThread().getName() + " " + this.threadName + " " );
		System.out.printf(format, args);
	}

	public void log(String message) {
		System.out.println(Thread.currentThread().getName() + " " + this.threadName + " " + message);
	}
}
