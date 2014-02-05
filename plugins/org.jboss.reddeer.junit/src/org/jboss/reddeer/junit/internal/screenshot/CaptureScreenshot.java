package org.jboss.reddeer.junit.internal.screenshot;

import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.jboss.reddeer.junit.logging.Logger;

/**
 * Feature is enabled by definition of property <i>relativeScreenshotDirectory</i>
 * 
 * @author mlabuda@redhat.com
 *
 */
public class CaptureScreenshot {
	
	private static final Logger logger = new Logger(CaptureScreenshot.class);
	
	/**
	 * Provide capturing screenshot. At first try to create path to specified directory in 
	 * property <i>relativeScreenshotDirectory</i>. On fail delete created structure (only new
	 * folders). On success capture screenshot of display.
	 * 
	 * @param String name file name of screenshot 
	 */
	public static void captureScreenshot(String name) {
		String path = null;
		boolean pathCreatedSuccessfuly = false;
		try {
			if (!((path = System.getProperty("relativeScreenshotDirectory")) == null)) {
				pathCreatedSuccessfuly = true;
				String separator = System.getProperty("file.separator");
				if (!separator.equals(path.charAt(path.length() - 1))) {
					path += separator;
				}
				String[] dirs = path.split(separator);
				
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
			}
		} catch (SecurityException ex) { }
		
		// If path has been created successfully screenshots can be captured
		if (pathCreatedSuccessfuly) {
			final Display display = new Display();
			GC gc = new GC(display);
			Image image = null;
			String fileName = path + name + ".png";
			
			try {
				// Create image of screen
				logger.debug("*** Capturing Screenshot: ***" + fileName);
				image = new Image(display, display.getBounds().width, display.getBounds().height);
				gc.copyArea(image, display.getBounds().x, display.getBounds().y);
				
				// Store image
				ImageLoader imageLoader = new ImageLoader();				
				imageLoader.data = new ImageData[] { image.getImageData() };
				imageLoader.save(fileName, SWT.IMAGE_PNG);
				
			} catch (Exception ex) {
				logger.debug("*** Screenshot capturing failed ***");
				if (new File(fileName).exists()) {
					try {
						 logger.debug("*** Corrupted image will be deleted on exit ***");
						 new File(fileName).deleteOnExit();
					} catch (Exception exception) { }
				}
				
			} finally {
				gc.dispose();
				display.dispose();
				if (image != null) {
					image.dispose();
				}
			}
		}
	}	
}
