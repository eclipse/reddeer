package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;

/**
 * BackButton is simple button implementation for "Next" button
 * @author Jiri Peterka
 *
 */
public class NextButton extends PredefinedButton {

	
	/**
	 * BackButton default constructor.
	 */
	public NextButton() {		
		super(null, 0, "Next >", SWT.PUSH);
		
	}

}
