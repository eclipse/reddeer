package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for CLabel manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface CLabel extends Widget {
	
	/**
	 * Returns text of the CLabel.
	 * 
	 * @return text of the CLabel
	 */
	String getText();

	/**
	 * Returns ToolTip text on the CLabel.
	 * 
	 * @return ToolTip text
	 */
	String getTooltipText();

	/**
	 * Returns the horizontal alignment. The alignment style (SWT.LEFT,
	 * SWT.CENTER or SWT.RIGHT) is returned. Alignment styles are located in
	 * org.eclipse.swt.SWT.
	 * 
	 * @return alignment of CLabel
	 */
	int getAlignment();

	/**
	 * Returns whether CLabel contains image or not.
	 * 
	 * @return true if CLabel contains image, false otherwise
	 */
	boolean hasImage();

	org.eclipse.swt.custom.CLabel getSWTWidget();
}
