package org.jboss.reddeer.core.reference;

import org.eclipse.swt.widgets.Control;

/**
 * Default referenced composite.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class DefaultReferencedComposite implements ReferencedComposite {

	private Control control;
	
	/**
	 * Creates default ReferencedComposite from control.
	 * 
	 * @param control control
	 */
	public DefaultReferencedComposite(Control control) {
		this.control = control;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.core.reference.ReferencedComposite#getControl()
	 */
	@Override
	public Control getControl() {
		return control;
	}

}
