package org.jboss.reddeer.swt.api;

/**
 * API for CLabel manipulation
 * @author Jiri Peterka
 *
 */
public interface CLabel {
	/**
	 * Returns text
	 * @return
	 */
	String getText();
	/**
	 * Returns tooltip text
	 * @return
	 */
	String getTooltipText();
	/**
	 * Returns alignment
	 * @return
	 */
	int getAlignment();
	/**
	 * Returns true when CLabel is displaying image 
	 * @return
	 */
	boolean hasImage();
	/**
	 * Returns underlying CLabel SWT widget
	 * @return
	 */
	org.eclipse.swt.custom.CLabel getSWTWidget();
}
