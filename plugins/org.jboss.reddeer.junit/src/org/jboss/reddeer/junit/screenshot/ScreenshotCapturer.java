/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.junit.screenshot;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.junit.AssumptionViolatedException;

/**
 * This class provides capabilities to capture screenshot of display. 
 * 
 * @author mlabuda@redhat.com
 * @since 0.5
 *
 */
public class ScreenshotCapturer {

	private static final Logger logger = new Logger(ScreenshotCapturer.class);
	public static final String SEPARATOR = System.getProperty("file.separator");
	
	private static ScreenshotCapturer instance;
	
	private ScreenshotCapturer() {}
	
	/**
	 * Gets the single instance of ScreenshotCapturer.
	 *
	 * @return single instance of ScreenshotCapturer
	 */
	public static ScreenshotCapturer getInstance() {
		if (instance == null) {
			instance = new ScreenshotCapturer();
		}
		return instance;
	}
	
	/**
	 * Provides capturing a screenshot. At first method try to create path to
	 * the specific directory (default or defined by property). If something
	 * goes wrong (e.g. cannot create directory structure), the process is
	 * stopped. This should not happen, only in unpredicted situations.
	 * Generally creation of directory structure pass and screenshot of the
	 * display(s) is taken.
	 * 
	 * User can override default image screenshot folder <i>./target/screenshots</i> 
	 * by definition of relative screenshot directory in property
	 * <i>relativeScreenshotDirectory</i>. Screenshots are captured in following
	 * cases:
	 * <ul>
	 * <li>BeforeClass method fails
	 * <li>Before method fails
	 * <li>Test method fails (exception is thrown, assertion failed, expected
	 * exception was not thrown etc.)
	 * <li>After method fails
	 * <li>AfterClass method fails
	 * </ul>
	 * 
	 * File name is altered in case of existence screenshot with the given file
	 * name. Alteration consists of number in braces in postfix.
	 * 
	 * @param config
	 *            configuration file under which the test is running
	 * @param name
	 *            file name of screenshot
	 * @throws CaptureScreenshotException
	 *             on occurrence of any exception
	 * @since 0.5
	 */
	public void captureScreenshotOnFailure(String config, String name) throws CaptureScreenshotException {
		if (RedDeerProperties.CAPTURE_SCREENSHOT.getBooleanValue()) {
			String path = getPath(config);
			
			createDirectories(path);
			
			captureScreenshot(path + name + ".png");
		} else {
			logger.warn("Screenshot has not been captured on failure, because RedDeer property whether screenshot "
					+ "should be captured or not is set to false.");
		}

	}
	
	/**
	 * Gets whole path to a directory where screenshots should be stored. Includes config directory.
	 * 
	 * @param config config of test suite.
	 * @return path to a directory supposed to contain screenshots
	 */
	private String getPath(String config) {
		String path;
		if (RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getValue() != null) {
			path = RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getValue();
		} else {
			path = "." + SEPARATOR + "target" + SEPARATOR + "screenshots";
		}
		if (!SEPARATOR.equals(path.charAt(path.length() - 1))) {
			path += SEPARATOR;
		}
		if (config != null) {
			path += config + SEPARATOR;
		}
		return path;
	}
	
	/**
	 * Capture screenshot with specified file name. PNG format is supported.
	 *
	 * @param screenshotFileName the screenshot file name
	 * @return absolute path to create screenshot filename
	 * @throws CaptureScreenshotException the capture screenshot exception
	 */
	public String captureScreenshot(final String screenshotFileName) throws CaptureScreenshotException {
		String alteredFileName = getAlteredScreenshotFileName(screenshotFileName);
		final String fileName = createMissingDirectories(alteredFileName);
		final Display display = Display.getDefault();
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				GC gc = new GC(display);
				Image image = null;

				try {
					logger.debug("Capturing Screenshot: " + fileName);
					image = new Image(display, display.getBounds().width, display.getBounds().height);
					gc.copyArea(image, display.getBounds().x, display.getBounds().y);

					ImageLoader imageLoader = new ImageLoader();
					imageLoader.data = new ImageData[] { image.getImageData() };
					imageLoader.save(fileName, SWT.IMAGE_PNG);

					logger.debug("Screenshot successfully captured. Saved in " + new File(fileName).getAbsolutePath());
				} catch (Exception ex) {
					logger.error("Capturing screenshot failed", ex);
					handleCorruptedScreenshot(fileName);
				} finally {
					gc.dispose();
					if (image != null) {
						image.dispose();
					}
				}
			}
		});
		return fileName;
	}

	/**
	 * Removes corrupted screenshot.
	 * 
	 * @param fileName file name of corrupted screenshot
	 */
	private void handleCorruptedScreenshot(final String fileName) {
		logger.debug("Screenshot capturing failed.");
		if (new File(fileName).exists()) {
			try {
				logger.debug("Corrupted image will be deleted on exit.");
				new File(fileName).deleteOnExit();
			} catch (Exception exception) {
			}
		}
	}
	
	/**
	 * Create missing directories from fileName.
	 * 
	 * @param fileName name of a screenshot
	 * @return full path with file name to a screenshot
	 * @throws CaptureScreenshotException on failure during the creation of directories
	 */
	private String createMissingDirectories(String fileName) throws CaptureScreenshotException {
		if (!fileName.contains(SEPARATOR)) {
			String path = "." + SEPARATOR + "target" + SEPARATOR + "custom-screenshots";
			createDirectories(path);
			return path + SEPARATOR + fileName;
		} else {
			createDirectories(fileName.substring(0, fileName.lastIndexOf(SEPARATOR)));
			return fileName;
		}
	}
	
	/**
	 * Gets formatted screenshot file name.
	 * 
	 * @param testClass
	 *            class where during the execution screenshot was captured on
	 *            failure
	 * @param testMethod
	 *            method where during the execution screenshot was captured on
	 *            failure
	 * @param detail
	 *            details about execution
	 * @return file name of a screenshot
	 */
	public static final String getScreenshotFileName(Class<?> testClass, String testMethod, String detail) {
		return testClass.getName() + (testMethod != null ? "." + testMethod : "")
				+ (detail != null ? "@" + detail : "");
	}
	
	/**
	 * Create directories for screenshot storage. If something goes wrong,
	 * CaptureScreenshotException is thrown.
	 *
	 * @param path the path
	 * @throws CaptureScreenshotException             on failure of creating directories
	 */
	public void createDirectories(String path) throws CaptureScreenshotException {
		try {
			String[] dirs;
			if (SEPARATOR.equals("\\")) {
				dirs = path.split("\\\\");
			} else {
				dirs = path.split(SEPARATOR);
			}

			int index = dirs.length;
			boolean checkpoint = false;
			String tmpPath = "";
			for (int i = 0; i < dirs.length; i++) {
				tmpPath += dirs[i] + SEPARATOR;
				if (!new File(tmpPath).exists()) {
					if (!checkpoint) {
						checkpoint = true;
						index = i;
					}
					if (new File(tmpPath).mkdir() == false) {
						for (int k = i - 1; k >= index; k--) {
							new File(tmpPath).delete();
						}
						throw new RuntimeException(
								"Directories for screenshot storage have not been created successfully."
										+ " Screenshot will not be captured and created directories were removed.");
					}
				}
			}
		} catch (Exception ex) {
			throw new CaptureScreenshotException(ex.getMessage(), ex.getCause());
		}
	}
	
	/**
	 * Tests if screenshot is required on throwable.
	 * 
	 * @param throwable the throwable
	 * @return true if required, false otherwise
	 */
	public static boolean shouldCaptureScreenshotOnException(Throwable throwable){
		return !(throwable instanceof AssumptionViolatedException);
	}
	
	private String getAlteredScreenshotFileName(String screenshotFileName) {
		String fileName = screenshotFileName;
		String fileExtension = ".png";
		if (!screenshotFileName.contains(fileExtension)) {
			fileName += fileExtension;
		}
		String partialFileName = fileName.substring(0, fileName.lastIndexOf("."));
		int counter = 2;
		if (new File(fileName).exists()) {
			while (new File(partialFileName + "(" + counter + ")" + fileExtension).exists()) {
				counter++;
			}
			return partialFileName + "(" + counter + ")" + fileExtension;
		} else {
			return partialFileName + fileExtension;
		}
	}
}
