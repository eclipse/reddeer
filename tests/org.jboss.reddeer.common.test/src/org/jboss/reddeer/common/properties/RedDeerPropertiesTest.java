package org.jboss.reddeer.common.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RedDeerPropertiesTest {

	private static String recordScreencastValue;

	private static boolean recordScreencastDefined = false;

	private static String relativeScreenshotDirectoryValue;

	private static boolean relativeScreenshotDirectoryDefined = false;

	@BeforeClass
	public static void setup(){
		if (System.getProperties().containsKey(RedDeerProperties.RECORD_SCREENCAST.getName())){
			recordScreencastValue = RedDeerProperties.RECORD_SCREENCAST.getSystemValue();			
			recordScreencastDefined = true;
		}

		if (System.getProperties().containsKey(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getName())){
			relativeScreenshotDirectoryValue = RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getSystemValue();			
			relativeScreenshotDirectoryDefined = true;
		}
	}

	@AfterClass
	public static void cleanup(){
		if (recordScreencastDefined){
			System.setProperty(RedDeerProperties.RECORD_SCREENCAST.getName(), recordScreencastValue);
		}

		if (relativeScreenshotDirectoryDefined){
			System.setProperty(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getName(), relativeScreenshotDirectoryValue);
		}
	}

	@Test
	public void getByName() {
		assertEquals("recordScreenCast", RedDeerProperties.getByName("recordScreenCast").getName());
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
		System.setProperty(RedDeerProperties.RECORD_SCREENCAST.getName(), "false");

		assertEquals("false", RedDeerProperties.RECORD_SCREENCAST.getSystemValue());
	}

	@Test(expected=RedDeerException.class)
	public void getSystemValue_boolean_fail() {
		System.setProperty(RedDeerProperties.RECORD_SCREENCAST.getName(), "abc");

		assertEquals("abc", RedDeerProperties.RECORD_SCREENCAST.getSystemValue());
	}

	@Test
	public void getBooleanSystemValue() {
		System.setProperty(RedDeerProperties.RECORD_SCREENCAST.getName(), "false");

		assertFalse(RedDeerProperties.RECORD_SCREENCAST.getBooleanSystemValue());
	}

	@Test(expected=RedDeerException.class)
	public void getBooleanSystemValue_textProperty() {
		System.setProperty(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getName(), "abc");

		assertFalse(RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getBooleanSystemValue());
	}
}
