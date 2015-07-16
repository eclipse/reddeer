package org.jboss.reddeer.workbench.condition;

import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;

/**
 * Check whether editor is active
 * @author rawagner
 *
 */
public class EditorWithTitleIsActive implements WaitCondition {
	
	private String title;
	
	/**
	 * Default constructor
	 * @param title of editor
	 */
	public EditorWithTitleIsActive(String title){
		this.title = title;
	}

	@Override
	public boolean test() {
		return new DefaultEditor().getTitle().equals(title);
	}

	@Override
	public String description() {
		return "Editor title is "+title;
	}

}
