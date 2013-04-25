package org.jboss.reddeer.workbench.editor;

/**
 * Interface with base operations which can be performed with editor.
 * 
 * @author rhopp
 * 
 */

public interface Editor {

	String getTitle();

	/**
	 * 
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

}
