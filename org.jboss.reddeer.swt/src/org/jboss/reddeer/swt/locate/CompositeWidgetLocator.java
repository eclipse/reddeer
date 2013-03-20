package org.jboss.reddeer.swt.locate;

import org.eclipse.swt.widgets.Control;

/**
 * This static class is used when children widgets discovering is
 * performed. 
 *  
 * @author jjankovi
 *
 */
public class CompositeWidgetLocator {

	private static Control compositeWidget = null;
	
	private CompositeWidgetLocator() {
		// this is static class without possibility to be instantiated
	}

	/**
	 * Returns current composite widget. In case of not being set, returns 
	 * active shell 
	 * @return 
	 */
	public static Control getCompositeWidget() {
		return compositeWidget;
	}

	/**
	 * Set composite parent widget which will be used to locate its children
	 * widgets 
	 * @param compositeWidget
	 */
	public static void setCompositeWidget(Control compositeWidget) {
		CompositeWidgetLocator.compositeWidget = compositeWidget;
	}
	
}
