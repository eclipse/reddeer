package org.jboss.reddeer.junit.internal.screenshot;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.properties.RedDeerProperties;

/**
 * This class provide capabilities to capture screenshot of display. User can override default 
 * image screenshot folder <i>./target/screenshots</i> by definition of relative screenshot 
 * directory in property <i>relativeScreenshotDirectory</i>. Screenshots are captured in 
 * following cases:<ul>
 * <li>BeforeClass method fails
 * <li>Before method fails
 * <li>Test method fails (exception is thrown, assertion failed, expected exception was not thrown etc.)
 * <li>After method fails
 * <li>AfterClass method fails
 * </ul>
 * 
 * @author mlabuda@redhat.com
 * @since 0.5
 *
 */
public class CaptureScreenshot {
	
	private static final Logger logger = new Logger(CaptureScreenshot.class);
	private String fileName;
	
	/**
	 * Provides capturing a screenshot. At first method try to create path to the specific directory 
	 * (default or defined by property). If something goes wrong (e.g. cannot create directory structure),
	 * the process is stopped. This should not happen, only in unpredicted situations.
	 * Generally creation of directory structure pass and screenshot of the display(s) is taken.
	 * 
	 * File name is altered in case of existence screenshot with the given file name. Alteration 
	 * consists of number in braces in postfix.
	 * 
	 * @param name file name of screenshot
	 * @param config configuration file under which the test is running
	 * @throws CaptureScreenshotException on occurrence of any exception
	 * @since 0.5 
	 */
	public void captureScreenshot(String config, String name) throws CaptureScreenshotException {
		if (RedDeerProperties.CAPTURE_SCREENSHOT.getBooleanValue()) {
			try {
				String separator = System.getProperty("file.separator");
				String path = "." + separator + "target" + separator + "screenshots";
				boolean pathCreatedSuccessfuly = true;
				try {
					if (RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getValue() != null) {
						path = RedDeerProperties.RELATIVE_SCREENSHOT_DIRECTORY.getValue();
					}
					if (!separator.equals(path.charAt(path.length() - 1))) {
						path += separator;
					}
					String[] dirs;
					if (separator.equals("\\")) {
						dirs = path.split("\\\\");
					} else {
						dirs = path.split(separator);
					}
					
					// Create missing folders in path
					int index = dirs.length;
					boolean checkpoint = false;
					String tmpPath = "";
					for (int i=0; i < dirs.length; i++) {
						tmpPath += dirs[i] + separator;
						if (!new File(tmpPath).exists()) {
							// Important due to rollbacks - if something goes wrong 
							// clean up process start and remove all created directory
							if (!checkpoint) {
								checkpoint = true;
								index = i;
							}
							if (new File(tmpPath).mkdir() == false) {
								// If was not created properly than clean up
								pathCreatedSuccessfuly = false;
								for (int k=i-1; k >= index; k--) {
									new File(tmpPath).delete();
								}
							}
						}
					}
					
					// Create a directory for screenshots, if configuration is set
					if (config != null) {
						path += separator + config + separator;
						if (!new File(path).exists()) {
							if (!new File(path).mkdir()) {
								pathCreatedSuccessfuly = false;
							}
						}
					}
				} catch (SecurityException ex) { 
					pathCreatedSuccessfuly = false;
				}
				
				// If path has been created successfully screenshots can be captured
				if (pathCreatedSuccessfuly) {
					final Display display = Display.getDefault();
					
					// Set file name or alter it in case of existence of such file
					int counter = 1;
					String partiallyFileName = path + name;
					String fileNameExtension = ".png";
					if (new File(partiallyFileName + fileNameExtension).exists()) {
						while (new File(partiallyFileName + "(" + counter + ")" + fileNameExtension).exists()) {
							counter++;
						}
						fileName = partiallyFileName + "(" + counter + ")" + fileNameExtension;
					} else {
						fileName = partiallyFileName + fileNameExtension;
					}
					
					display.syncExec(new Runnable() {
						@Override
						public void run() {
							GC gc = new GC(display);
							Image image = null;
								
							try {
								// Create image of screen
								logger.debug("Capturing Screenshot: " + fileName);
								image = new Image(display, display.getBounds().width, display.getBounds().height);
								gc.copyArea(image, display.getBounds().x, display.getBounds().y);
								
								// Store image
								ImageLoader imageLoader = new ImageLoader();				
								imageLoader.data = new ImageData[] { image.getImageData() };
								imageLoader.save(fileName, SWT.IMAGE_PNG);
								
								logger.debug("Screenshot successfully captured. Saved in " + new File(fileName).getAbsolutePath());
							} catch (Exception ex) {
								logger.debug("Screenshot capturing failed.");
								if (new File(fileName).exists()) {
									try {
										 logger.debug("Corrupted image will be deleted on exit.");
										 new File(fileName).deleteOnExit();
									} catch (Exception exception) { }
								}
							} finally {
								gc.dispose();
								if (image != null) {
									image.dispose();
								}
							}
							
						}
					});
				} else {
					throw new CaptureScreenshotException("Screenshot could not be created, " 
							+ "because required folders where to store screenshot has not been created");
				}
			} catch (Exception ex) {
				throw new CaptureScreenshotException(ex.getMessage(), ex.getCause());
			}
		}
	}
	/**
	 * Returns formatted screenshot file name 
	 * @param testClass
	 * @param testMethod
	 * @param detail
	 * @return
	 */
	public static final String getScreenshotFileName (Class<?> testClass, String testMethod, String detail){
		return testClass.getName() + "." 
			+ (testMethod != null ? testMethod : "nomethod")
			+ (detail != null ? "@" + detail : "");
	}
}
