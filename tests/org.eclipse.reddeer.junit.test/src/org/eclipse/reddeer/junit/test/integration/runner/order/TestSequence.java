/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.test.integration.runner.order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestSequence {
	
	private static final String I_BEFORES = "I_BEFORES";
	
	private static final String I_BEFORES_CLASS = "I_BEFORES_CLASS";
	
	private static final String I_AFTERS = "I_AFTERS";
	
	private static final String I_AFTERS_CLASS = "I_AFTERS_CLASS";
	
	private static final String FULFILL = "FULFILL";
	
	private static final String CLEANUP = "CLEANUP";
	
	private static final String DECLARATION = "DECLARATION";
	
	private static final String REQ_BEFORE_CLASS = "REQ_BEFORE_CLASS";
	
	private static final String REQ_AFTER_CLASS = "REQ_AFTER_CLASS";
	
	private static final String REQ_BEFORE = "REQ_BEFORE";
	
	private static final String REQ_AFTER = "REQ_AFTER";
	
	private static final String BEFORE_CLASS = "BEFORE_CLASS";
	
	private static final String AFTER_CLASS = "AFTER_CLASS";
	
	private static final String BEFORE = "BEFORE";
	
	private static final String AFTER = "AFTER";
	
	private static final String TEST = "TEST";
	
	private static final String TEST_WITH_PARAMETER = "TEST_WITH_PARAMETER";
	
	private static final String CONSTRUCT_TEST = "CONSTRUCT_TEST";
	
	private static final String CONSTRUCT_TEST_WITH_PARAMETER = "CONSTRUCT_TEST_WITH_PARAMETER";

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
	
	public static void addReqBeforeClass(Class<?> c){
		realSequence.add(createReqBeforeClass(c));
	}
	
	public static void addReqAfterClass(Class<?> c){
		realSequence.add(createReqAfterClass(c));
	}
	
	public static void addReqBefore(Class<?> c){
		realSequence.add(createReqBefore(c));
	}
	
	public static void addReqAfter(Class<?> c){
		realSequence.add(createReqAfter(c));
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
	
	public static void addTestWithParam(Class<?> c , Object param){
		realSequence.add(createTestWithParam(c, param));
	}
	
	public static void addCleanup(Class<?> c){
		realSequence.add(createCleanup(c));
	}
	
	public static void addConstructTest(Class<?> c){
		realSequence.add(constructTest(c));
	}
	
	public static void addConstructTestWithParam(Class<?> c, Object param){
		realSequence.add(constructTestWithParam(c, param));
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
	
	public static String createReqBeforeClass(Class<?> c){
		return c + " - " + REQ_BEFORE_CLASS;
	}
	
	public static String createReqAfterClass(Class<?> c){
		return c + " - " + REQ_AFTER_CLASS;
	}
	
	public static String createReqBefore(Class<?> c){
		return c + " - " + REQ_BEFORE;
	}
	
	public static String createReqAfter(Class<?> c){
		return c + " - " + REQ_AFTER;
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
	
	public static String createTestWithParam(Class<?> c , Object parameter){
		return c + " - " + parameter + " - " + TEST_WITH_PARAMETER;
	}
	
	public static String createCleanup(Class<?> c){
		return c+ " - " + CLEANUP;
	}
	
	public static String constructTest(Class<?> c){
		return c+ " - " + CONSTRUCT_TEST;
	}
	
	public static String constructTestWithParam(Class<?> c , Object parameter){
		return c + " - " + parameter + " - "  + CONSTRUCT_TEST_WITH_PARAMETER;
	}
	
	public static String diffRealSequence (List<?> compareSequence){
		String result = null;
		if (realSequence != null && compareSequence != null){
			StringBuilder sbDiff = new StringBuilder("Real sequence vs. compared sequence:\n");
			Iterator<?> itRealSequence = realSequence.iterator();
			Iterator<?> itCompareSequence = compareSequence.iterator();
			int step = 0;
			boolean equals = true;
			while (itRealSequence.hasNext() && itCompareSequence.hasNext()){
				Object realItem = itRealSequence.next();
				Object comparedItem = itCompareSequence.next();
				sbDiff.append(step);
				sbDiff.append(". ");
				sbDiff.append(realItem.toString());
				if (realItem.equals(comparedItem)){
					sbDiff.append(" == ");
				}
				else{
					sbDiff.append(" != ");
					if (equals){
						equals = false;
					}
				}
				sbDiff.append(comparedItem.toString());
				sbDiff.append("\n");
				step++;
			}
			while (itRealSequence.hasNext()) {
				Object realItem = itRealSequence.next();
				sbDiff.append(step);
				sbDiff.append(". ");
				sbDiff.append(realItem.toString());
				sbDiff.append(" != ");
				if (equals){
					equals = false;
				}
				sbDiff.append("<null>");
				sbDiff.append("\n");
				step++;
			}
			while (itCompareSequence.hasNext()) {
				Object compareItem = itCompareSequence.next();
				sbDiff.append(step);
				sbDiff.append(". ");
				sbDiff.append("<null>");
				sbDiff.append(" != ");
				if (equals){
					equals = false;
				}
				sbDiff.append(compareItem.toString());
				sbDiff.append("\n");
				step++;
			}
			if (!equals){
				result = sbDiff.toString();
			}
		}
		else{
			if (realSequence != null){
				result = "Compared sequence is null but real sequence is not";
			}
			else{
				result = "Real sequence is null but compared sequence is not";
			}
		}
		return result;
	}
}
