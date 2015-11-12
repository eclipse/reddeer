package org.jboss.reddeer.swt.impl.progressbar;

import org.eclipse.swt.SWT;

/**
 * This class represents progress bar with style SWT.VERTICAL
 * 
 * @author rhopp
 *
 */
public class VerticalProgressBar extends DeterminateProgressBar {

	/**
	 * Instantiates vertical progressbar with given index.
	 *
	 * @param index the index
	 */
	public VerticalProgressBar(int index) {
		super(index, SWT.VERTICAL);
	}
	
	/**
	 * Instantiates vertical progressbar.
	 */
	public VerticalProgressBar() {
		super(0, SWT.VERTICAL);
	}

}
