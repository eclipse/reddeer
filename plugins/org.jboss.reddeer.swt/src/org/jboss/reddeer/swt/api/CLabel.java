package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for CLabel manipulation
 * @author Jiri Peterka
 *
 */
public interface CLabel extends Widget{
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
	
	org.eclipse.swt.custom.CLabel getSWTWidget();
}
