package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

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
		super(null, index,"",SWT.ARROW);
	}
	
	/**
	 * Arrow button with given index inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public ArrowButton(ReferencedComposite referencedComposite, int index) {
		super(referencedComposite, index,"",SWT.ARROW);
	}
	
}
