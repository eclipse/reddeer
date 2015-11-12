package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;

/**
 * BackButton is a simple button implementation for "Back" button
 * @author Jiri Peterka
 *
 */
public class BackButton extends PredefinedButton {

	
	/**
	 * BackButton default constructor.
	 */
	public BackButton() {		
		super(null, 0, "< Back", SWT.PUSH);
		
	}

}
