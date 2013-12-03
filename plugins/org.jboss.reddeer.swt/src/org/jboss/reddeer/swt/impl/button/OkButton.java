package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;

/**
 * OkButton is simple button implementation for OK button
 * @author Jiri Peterka
 *
 */
public class OkButton extends PredefinedButton {

	
	/**
	 * OkButton default constructor
	 */
	public OkButton() {		
		super(null, 0, "OK", SWT.PUSH);
		
	}

}
