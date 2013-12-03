package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;

/**
 * BackButton is simple button implementation for "Yes" button
 * @author Jiri Peterka
 *
 */
public class YesButton extends PredefinedButton {

	
	/**
	 * YesButton default constructor
	 */
	public YesButton() {		
		super(null, 0, "Yes", SWT.PUSH);
		
	}

}
