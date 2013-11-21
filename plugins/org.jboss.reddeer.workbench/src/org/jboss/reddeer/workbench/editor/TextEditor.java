package org.jboss.reddeer.workbench.editor;

import org.eclipse.ui.texteditor.ITextEditor;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.workbench.exception.EditorNotFoundException;

/**
 * Represents text editors (implementing interface ITextEditor)
 * 
 * @author rhopp
 *
 */

public class TextEditor extends DefaultEditor{

	/**
	 * Initialize currently focused TextEditor
	 * @throws EditorNotFoundException when currently active editor isn't instance of ITextEditor
	 */
	
	public TextEditor() {
		super();
		if (!(workbenchPart instanceof ITextEditor)){
			throw new EditorNotFoundException("Given editor is not instance of ITextEditor");
		}
	}
	
	/**
	 * Initialize editor with given title
	 * @param title title of desired editor
	 * @throws EditorNotFoundException when currently active editor isn't instance of ITextEditor 
	 */
	
	public TextEditor(final String title){
		super(title);
		if (!(workbenchPart instanceof ITextEditor)){
			throw new EditorNotFoundException("Given editor is not instance of ITextEditor");
		}
	}
	
	/**
	 * @return content of this editor
	 */

	public String getText(){
		return getTextEditorPart().getDocumentProvider().getDocument(getTextEditorPart().getEditorInput()).get();
	}
	
	/**
	 * Set text text into editor
	 * @param text given test that will be set as editor text
	 */
	public void setText(final String text) {
		
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				getTextEditorPart().getDocumentProvider().getDocument(getTextEditorPart().getEditorInput()).set(text);				
			}
			
		});
		
	}
	
	protected ITextEditor getTextEditorPart(){
		return (ITextEditor) getEditorPart();
	}
	
}
