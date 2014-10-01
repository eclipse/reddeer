package org.jboss.reddeer.workbench.api;

/**
 * Interface with base operations which can be performed with workbench part.
 * 
 * @author Vlado Pakan
 */
public interface WorkbenchPart {
	/**
	 * Activates workbench part
	 */
	void activate();

	/**
	 * Minimize workbench part
	 */
	void minimize();

	/**
	 * Maximize workbench part
	 */
	void maximize();

	/**
	 * Restores workbench part
	 */
	void restore();

	/**
	 * Close workbench part
	 */
	void close();
	/**
     * Returns Title of workbench part
     * @return
     */
    String getTitle();

}
