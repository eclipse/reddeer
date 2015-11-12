package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;

/**
 * FinishButton is simple button implementation for "Finish" button
 * @author Jiri Peterka
 *
 */
public class FinishButton extends PredefinedButton {

	
	/**
	 * FinishButton default constructor.
	 */
	public FinishButton() {		
		super(null, 0, "Finish", SWT.PUSH);
		
	}

}
