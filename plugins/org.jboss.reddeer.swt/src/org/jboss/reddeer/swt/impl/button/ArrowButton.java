package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.swt.api.Button;

/**
 * Arrow Button is button implementation with arrow symbol as label
 * @author Vlado Pakan
 *
 */
public class ArrowButton extends AbstractButton implements Button {

	/**
	 * Arrow button with given index
	 * @param index
	 */
	public ArrowButton(int index) {
		super(index,"",SWT.ARROW);
	}
	
}
