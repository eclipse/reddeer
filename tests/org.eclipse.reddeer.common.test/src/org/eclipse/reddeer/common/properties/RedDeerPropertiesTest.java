/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.common.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RedDeerPropertiesTest {

	private static String relativeScreenshotDirectoryValue;

	private static boolean relativeScreenshotDirectoryDefined = false;
	
	private static String timePeriodFactor;

	private static boolean timePeriodFactorDefined = false;

	@BeforeClass
	public static void setup(){

		if (System.getProperties().containsKey(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getName())){
			relativeScreenshotDirectoryValue = RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getValue();			
			relativeScreenshotDirectoryDefined = true;
		}
		
		if (System.getProperties().containsKey(RedDeerProperties.TIME_PERIOD_FACTOR.getName())){
			timePeriodFactor = RedDeerProperties.TIME_PERIOD_FACTOR.getValue();			
			timePeriodFactorDefined = true;
		}
	}

	@AfterClass
	public static void cleanup(){
		if (relativeScreenshotDirectoryDefined){
			System.setProperty(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getName(), relativeScreenshotDirectoryValue);
		} else {
			System.clearProperty(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getName());
		}
		
		if (timePeriodFactorDefined){
			System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), timePeriodFactor);
		} else {
			System.clearProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName());
		}
	}

	@Test(expected=RedDeerException.class)
	public void getByName_nonExisting() {
		RedDeerProperties.getByName("abc");
	}

	@Test
	public void getSystemValue() {
		System.setProperty(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getName(), "abc");

		assertEquals("abc", RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getValue());
	}
	
	@Test
	public void getSystemValue_float() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "0.5");

		assertEquals("0.5", RedDeerProperties.TIME_PERIOD_FACTOR.getValue());
	}

	@Test(expected=RedDeerException.class)
	public void getSystemValue_float_fail() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "abc");

		RedDeerProperties.TIME_PERIOD_FACTOR.getValue();
	}

	@Test(expected=RedDeerException.class)
	public void getBooleanSystemValue_textProperty() {
		System.setProperty(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getName(), "abc");

		assertFalse(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getBooleanValue());
	}
	
	@Test
	public void getFloatSystemValue() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "0.5");

		assertThat(RedDeerProperties.TIME_PERIOD_FACTOR.getFloatValue(), is(0.5f));
	}

	@Test(expected=RedDeerException.class)
	public void getFloatSystemValue_textProperty() {
		System.setProperty(RedDeerProperties.TIME_PERIOD_FACTOR.getName(), "abc");

		RedDeerProperties.TIME_PERIOD_FACTOR.getFloatValue();
	}
}
