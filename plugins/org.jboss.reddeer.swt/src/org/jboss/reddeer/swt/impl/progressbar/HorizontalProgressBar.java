package org.jboss.reddeer.swt.impl.progressbar;

import org.eclipse.swt.SWT;

/**
 * This class represents progress bar with style SWT.HORIZONTAL
 * 
 * @author rhopp
 *
 */

public class HorizontalProgressBar extends DeterminateProgressBar {

	/**
	 * Instantiates horizontal progressbar with given index.
	 *
	 * @param index the index
	 */
	public HorizontalProgressBar(int index) {
		super(index, SWT.HORIZONTAL);
	}
	
	/**
	 * Instantiates horizontal progressbar.
	 */
	public HorizontalProgressBar() {
		super(0, SWT.HORIZONTAL);
	}

}
