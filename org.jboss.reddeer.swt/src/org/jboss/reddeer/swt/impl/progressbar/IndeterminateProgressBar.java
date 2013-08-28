package org.jboss.reddeer.swt.impl.progressbar;

import org.eclipse.swt.SWT;

/**
 * This class represents progressbar with style SWT.INDETERMINATE (without possibility to read status).
 * This progressbar could be either SWT.HORIZONTAL or SWT.VERTICAL
 * 
 * @author rhopp
 *
 */

public class IndeterminateProgressBar extends AbstractProgressBar {

	/**
	 * Instantiates indeterminate progressbar
	 */
	
	public IndeterminateProgressBar(){
		super(0, SWT.INDETERMINATE);
	}
	
	/**
	 * Instantiates indeterminate progressbar with given index
	 * @param index
	 */
	
	public IndeterminateProgressBar(int index) {
		super(index, SWT.INDETERMINATE);
	}

}
