package org.jboss.reddeer.logparser.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.editors.text.TextEditor;

public class ReadOnlyTextEditor extends TextEditor {
	@Override
	public void doSave(IProgressMonitor monitor) {
		// do nothing it's read-only
	}

	@Override
	public void doSaveAs() {
		// do nothing it's read-only
	}
	
	@Override
	public boolean isDirty() {
		return false;
	}
	
	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
}