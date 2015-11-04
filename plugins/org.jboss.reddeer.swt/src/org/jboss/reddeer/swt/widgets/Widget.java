package org.jboss.reddeer.swt.widgets;

public interface Widget {

	/**
	 * Returns SWT Widget enclosed by this widget
	 * 
	 * @return
	 */
	org.eclipse.swt.widgets.Widget getSWTWidget();

	/**
	 * Returns true when widget is enabled, false otherwise
	 * 
	 * @return
	 */
	boolean isEnabled();
	
	/**
	 * Returns true when widget is disposed, false otherwise
	 * 
	 * @return
	 */
	boolean isDisposed();

}
