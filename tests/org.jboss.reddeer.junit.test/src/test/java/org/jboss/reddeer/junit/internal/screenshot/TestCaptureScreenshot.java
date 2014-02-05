package org.jboss.reddeer.junit.internal.screenshot;

import static org.junit.Assert.*;

import java.io.File;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(RedDeerSuite.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCaptureScreenshot {
	
	private static String pathWithoutSeparator = "images";
	private String pathWithSeparator = "images" + System.getProperty("file.separator");
	
	@Test(expected=AssertionError.class)
	public void test1aCaptureScreenshotInDirWithoutSeparator() {
		System.setProperty("relativeScreenshotDirectory", pathWithoutSeparator);
		fail();
	}
	
	@Test
	public void test1bverifyTest1a() {
		fileExists("test1aCaptureScreenshotInDirWithoutSeparator", true);
	}
	
	@Test(expected=AssertionError.class)
	public void test2aCaptureScreenshotInDirWithSeparator() {
		System.setProperty("relativeScreenshotDirectory", pathWithSeparator);
		fail();
	}
	
	@Test
	public void test2bverifyTest2a() {
		fileExists("test2aCaptureScreenshotInDirWithSeparator", true);
	}
	
	@Test
	public void test3aPass() {
	}
	
	public void test3bVerifyTest3a() {
		fileExists("test3aPass", false);
	}

	@AfterClass
	public static void cleanUp() {
		for (File file: new File(pathWithoutSeparator).listFiles()) {
			file.delete();	
		}
		new File(pathWithoutSeparator).delete();
	}
	
	private void fileExists(String fileName, boolean shouldExists) {
		boolean fileExist = false; 
		for (File file: new File(pathWithoutSeparator).listFiles()) {
			if (file.getName().split(" ")[1].equals(fileName + ".png")) {
				fileExist = true;
			}
		}
		assertEquals(shouldExists, fileExist);
	}
}
