package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;

/**
 * Cancel button implementation
 * @author Jiri Peterka
 *
 */
public class CancelButton extends PredefinedButton {
	
	/**
	 * CancelButton default constructor.
	 */
	public CancelButton() {		
		super(null, 0, "Cancel", SWT.PUSH);
		
	}

}
