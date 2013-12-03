package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;

/**
 * NoButton is simple button implementation for "No" button
 * @author Jiri Peterka
 *
 */
public class NoButton extends PredefinedButton {

	
	/**
	 * NoButton default constructor
	 */
	public NoButton() {		
		super(null, 0, "No", SWT.PUSH);
		
	}

}
