package org.jboss.reddeer.workbench.condition;

import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.workbench.api.Editor;

/**
 * Checks if specified editor contains validation markers
 * @author rawagner
 *
 */
public class EditorHasValidationMarkers implements WaitCondition{
	
	private Editor editor;
	
	/**
	 * Default constructor
	 * @param editor which should be used to find validation markers
	 */
	public EditorHasValidationMarkers(Editor editor) {
		this.editor = editor;
	}

	@Override
	public boolean test() {
		editor.activate();
		return editor.getMarkers().size() > 0;
	}

	@Override
	public String description() {
		return "Editor "+editor.getTitle()+" has validation markers";
	}

}
