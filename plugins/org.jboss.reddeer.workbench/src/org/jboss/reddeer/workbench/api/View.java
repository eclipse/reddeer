package org.jboss.reddeer.workbench.api;

/**
 * Interface with base operations which can be performed with view.
 * @author rawagner
 *
 */
public interface View {
	
	/**
	 * Open view
	 */
	void open();
	/**
	 * Close view
	 */
	void close();
	
	/**
	 * Maximize view
	 */
	void maximize();
	
	/**
	 * Minimize view
	 */
	void minimize();

}
