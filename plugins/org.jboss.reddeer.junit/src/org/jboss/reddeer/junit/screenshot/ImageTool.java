package org.jboss.reddeer.junit.screenshot;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jboss.reddeer.common.exception.RedDeerException;

/**
 * Provides certain functionality to analyze screenshot images
 * 
 * @since 1.0
 * @author Jiri Peterka
 *
 */
public class ImageTool {

	private static ImageTool instance;

	private ImageTool() {
	}

	/**
	 * Gets the single instance of ImageTool.
	 *
	 * @return single instance of ImageTool
	 */
	public static ImageTool getInstance() {
		if (instance == null) {
			instance = new ImageTool();
		}
		return instance;
	}

	/**
	 * Checks if image is only one color (blank).
	 *
	 * @param filePath absolute path to the image
	 * @return true if image is one color only (blank) or false if not
	 */
	public boolean isImageBlank(String filePath) {

		BufferedImage img;
		try {
			img = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			throw new RedDeerException("Cannot read image file " + filePath, e);
		}
		int width = img.getWidth();
		int height = img.getHeight();
		Color first = new Color(img.getRGB(0, 0));
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = img.getRGB(i, j);
				Color c = new Color(rgb);
				if (!c.equals(first)) {
					return false;
				}

			}
		}
		return true;
	}

}
