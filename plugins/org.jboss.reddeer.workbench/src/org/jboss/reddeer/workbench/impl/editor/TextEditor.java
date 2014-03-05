package org.jboss.reddeer.workbench.impl.editor;

import org.eclipse.ui.texteditor.ITextEditor;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;
import org.jboss.reddeer.workbench.impl.editor.AbstractEditor;
import org.jboss.reddeer.workbench.api.Editor;

/**
 * Represents text editors (implementing interface ITextEditor)
 * 
 * @author rhopp
 * @author rawagner
 */
public class TextEditor extends AbstractEditor implements Editor {

	/**
	 * Initialize currently focused TextEditor
	 * 
	 * @throws EditorNotFoundException
	 *             when currently active editor isn't instance of ITextEditor
	 */
	public TextEditor() {
		super();
		if (!(editorPart instanceof ITextEditor)) {
			throw new WorkbenchPartNotFound(
					"Given editor is not instance of ITextEditor");
		}
	}

	/**
	 * Initialize editor with given title
	 * 
	 * @param title
	 *            title of desired editor
	 * @throws EditorNotFoundException
	 *             when currently active editor isn't instance of ITextEditor
	 */
	public TextEditor(final String title) {
		super(title);
		if (!(editorPart instanceof ITextEditor)) {
			throw new WorkbenchPartNotFound(
					"Given editor is not instance of ITextEditor");
		}
	}

	/**
	 * @return content of this editor
	 */
	public String getText() {
		return getTextEditorPart().getDocumentProvider()
				.getDocument(getTextEditorPart().getEditorInput()).get();
	}

	/**
	 * Set text text into editor
	 * 
	 * @param text
	 *            given test that will be set as editor text
	 */
	public void setText(final String text) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				getTextEditorPart().getDocumentProvider()
						.getDocument(getTextEditorPart().getEditorInput())
						.set(text);
			}
		});

	}

	protected ITextEditor getTextEditorPart() {
		return (ITextEditor) getEditorPart();
	}

}
