package org.jboss.reddeer.swt.reference;

import org.eclipse.swt.widgets.Control;

/**
 * This static class is used when children widgets discovering is
 * performed. 
 *  
 * @author jjankovi
 * @author Jiri Peterka
 *
 */
public class ReferenceComposite {

	private static Control compositeWidget = null;
	
	private ReferenceComposite() {
		// this is static class without possibility to be instantiated
	}

	/**
	 * Returns current composite widget. In case of not being set, returns 
	 * active shell 
	 * @return 
	 */
	public static Control getComposite() {
		return compositeWidget;
	}

	/**
	 * Set composite parent widget which will be used to locate its children
	 * widgets 
	 * @param compositeWidget
	 */
	public static void setComposite(Control compositeWidget) {
		ReferenceComposite.compositeWidget = compositeWidget;
	}
	
}
