package org.jboss.reddeer.swt.impl.progressbar;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.core.matcher.WithStyleMatcher;

/**
 * This class represents progressbar with style SWT.INDETERMINATE (without possibility to read status).
 * This progressbar could be either SWT.HORIZONTAL or SWT.VERTICAL
 * 
 * @author rhopp
 *
 */

public class IndeterminateProgressBar extends DefaultProgressBar {

	/**
	 * Instantiates indeterminate progressbar.
	 */
	public IndeterminateProgressBar(){
		this(0);
	}
	
	/**
	 * Instantiates indeterminate progressbar with given index.
	 *
	 * @param index the index
	 */
	public IndeterminateProgressBar(int index) {
		super(index, new WithStyleMatcher(SWT.INDETERMINATE));
	}

}
