package org.jboss.reddeer.workbench.editor;

/**
 * Interface with base operations which can be performed with editor.
 * 
 * @author rhopp
 * @deprecated use org.jboss.reddeer.workbench.api.Editor
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
	 * @param save
	 *            If true, content will be saved
	 */

	void close(boolean save);

	/**
	 * 
	 * @return whether is this editor currently active and has focus.
	 */

	boolean isActive();
}
