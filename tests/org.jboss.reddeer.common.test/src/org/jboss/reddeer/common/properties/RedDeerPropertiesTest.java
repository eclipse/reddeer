package org.jboss.reddeer.common.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RedDeerPropertiesTest {

	private static String logDebugValue;

	private static boolean logDebugDefined = false;

	private static String relativeScreenshotDirectoryValue;

	private static boolean relativeScreenshotDirectoryDefined = false;

	@BeforeClass
	public static void setup(){
		if (System.getProperties().containsKey(RedDeerProperties.LOG_DEBUG.getName())){
			logDebugValue = RedDeerProperties.LOG_DEBUG.getSystemValue();			
			logDebugDefined = true;
		}

		if (System.getProperties().containsKey(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getName())){
			relativeScreenshotDirectoryValue = RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getSystemValue();			
			relativeScreenshotDirectoryDefined = true;
		}
	}

	@AfterClass
	public static void cleanup(){
		if (logDebugDefined){
			System.setProperty(RedDeerProperties.LOG_DEBUG.getName(), logDebugValue);
		}

		if (relativeScreenshotDirectoryDefined){
			System.setProperty(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getName(), relativeScreenshotDirectoryValue);
		}
	}

	@Test
	public void getByName() {
		assertEquals("logDebug", RedDeerProperties.getByName("logDebug").getName());
	}

	@Test(expected=RedDeerException.class)
	public void getByName_nonExisting() {
		RedDeerProperties.getByName("abc");
	}

	@Test
	public void getSystemValue() {
		System.setProperty(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getName(), "abc");

		assertEquals("abc", RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getSystemValue());
	}

	@Test
	public void getSystemValue_boolean() {
		System.setProperty(RedDeerProperties.LOG_DEBUG.getName(), "false");

		assertEquals("false", RedDeerProperties.LOG_DEBUG.getSystemValue());
	}

	@Test(expected=RedDeerException.class)
	public void getSystemValue_boolean_fail() {
		System.setProperty(RedDeerProperties.LOG_DEBUG.getName(), "abc");

		assertEquals("abc", RedDeerProperties.LOG_DEBUG.getSystemValue());
	}

	@Test
	public void getBooleanSystemValue() {
		System.setProperty(RedDeerProperties.LOG_DEBUG.getName(), "false");

		assertFalse(RedDeerProperties.LOG_DEBUG.getBooleanSystemValue());
	}

	@Test(expected=RedDeerException.class)
	public void getBooleanSystemValue_textProperty() {
		System.setProperty(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getName(), "abc");

		assertFalse(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getBooleanSystemValue());
	}
}
