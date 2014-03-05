package org.jboss.reddeer.workbench.api;

/**
 * Interface with base operations which can be performed with editor.
 * 
 * @author rhopp
 * @author rawagner
 */
public interface Editor {

	/**
	 * 
	 * @return editor title
	 */
	String getTitle();
	
	/**
	 * 
	 * @return editor title tooltip
	 */
	String getTitleToolTip();

	/**
	 * Checks if editor is dirty
	 * @return true if editor is dirty
	 */
	boolean isDirty();

	/**
	 * Tries to perform save on this editor.
	 */
	void save();

	/**
	 * Closes this editor
	 * 
	 * @param save If true, content will be saved
	 */
	void close(boolean save);

	/**
	 * Checks if editor is active
	 * @return whether is this editor currently active and has focus.
	 */
	boolean isActive();
	
	/**
	 * Closes editor
	 */
	void close();
	
	/**
	 * Maximize editor
	 */
	void maximize();
	
	/**
	 * Minimize editor
	 */
	void minimize();
	
	
}
