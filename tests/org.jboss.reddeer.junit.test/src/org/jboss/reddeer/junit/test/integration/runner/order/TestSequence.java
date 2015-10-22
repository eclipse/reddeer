package org.jboss.reddeer.junit.test.integration.runner.order;

import java.util.ArrayList;
import java.util.List;

public class TestSequence {
	
	private static final String I_BEFORES = "I_BEFORES";
	
	private static final String I_BEFORES_CLASS = "I_BEFORES_CLASS";
	
	private static final String I_AFTERS = "I_AFTERS";
	
	private static final String I_AFTERS_CLASS = "I_AFTERS_CLASS";
	
	private static final String FULFILL = "FULFILL";
	
	private static final String CLEANUP = "CLEANUP";
	
	private static final String DECLARATION = "DECLARATION";
	
	private static final String BEFORE_CLASS = "BEFORE_CLASS";
	
	private static final String AFTER_CLASS = "AFTER_CLASS";
	
	private static final String BEFORE = "BEFORE";
	
	private static final String AFTER = "AFTER";
	
	private static final String TEST = "TEST";

	private static List<Object> realSequence = new ArrayList<Object>();
	
	public static List<?> getRealSequence(){
		return realSequence;
	}
	
	public static void add(Class<?> c){
		realSequence.add(c);
	}
	
	public static void addIBefores(Class<?> c){
		realSequence.add(createIBefore(c));
	}
	
	public static void addIBeforesClass(Class<?> c){
		realSequence.add(createIBeforeClass(c));
	}
	
	public static void addIAfters(Class<?> c){
		realSequence.add(createIAfter(c));
	}
	
	public static void addIAftersClass(Class<?> c){
		realSequence.add(createIAfterClass(c));
	}
	
	public static void addFulfill(Class<?> c){
		realSequence.add(createFulfill(c));
	}
	
	public static void addSetDeclaration(Class<?> c){
		realSequence.add(createSetDeclaration(c));
	}
	
	public static void addBeforeClass(Class<?> c){
		realSequence.add(createBeforeClass(c));
	}
	
	public static void addAfterClass(Class<?> c){
		realSequence.add(createAfterClass(c));
	}
	
	public static void addBefore(Class<?> c){
		realSequence.add(createBefore(c));
	}
	
	public static void addAfter(Class<?> c){
		realSequence.add(createAfter(c));
	}
	
	public static void addTest(Class<?> c){
		realSequence.add(createTest(c));
	}
	
	public static void addCleanup(Class<?> c){
		realSequence.add(createCleanup(c));
	}
	
	public static String createIBefore(Class<?> c){
		return c + " - " + I_BEFORES;
	}
	
	public static String createIBeforeClass(Class<?> c){
		return c + " - " + I_BEFORES_CLASS;
	}
	
	public static String createIAfterClass(Class<?> c){
		return c + " - " + I_AFTERS_CLASS;
	}
	
	public static String createIAfter(Class<?> c){
		return c + " - " + I_AFTERS;
	}
	
	public static String createFulfill(Class<?> c){
		return c + " - " + FULFILL;
	}
	
	public static String createSetDeclaration(Class<?> c){
		return c + " - " + DECLARATION;
	}
	
	public static String createBeforeClass(Class<?> c){
		return c + " - " + BEFORE_CLASS;
	}
	
	public static String createAfterClass(Class<?> c){
		return c + " - " + AFTER_CLASS;
	}
	
	public static String createBefore(Class<?> c){
		return c + " - " + BEFORE;
	}
	
	public static String createAfter(Class<?> c){
		return c + " - " + AFTER;
	}
	
	public static String createTest(Class<?> c){
		return c + " - " + TEST;
	}
	
	public static String createCleanup(Class<?> c){
		return c+ " - " + CLEANUP;
	}
}
