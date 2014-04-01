package org.jboss.reddeer.workbench.impl.editor;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.texteditor.ITextEditor;
import org.jboss.reddeer.workbench.api.Editor;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;
import org.jboss.reddeer.workbench.handler.TextEditorHandler;

/**
 * Represents text editors (implementing interface ITextEditor)
 * This implementation manipulates with IDocument of given editor. For keyboard-like 
 * typing use @link{org.jboss.reddeer.swt.Keyboard}.
 * 
 * @author rhopp
 * @author rawagner
 *
 */

public class TextEditor extends AbstractEditor implements Editor{

	/**
	 * Initialize currently focused TextEditor
	 * @throws EditorNotFoundException when currently active editor isn't instance of ITextEditor
	 */
	
	public TextEditor() {
		super();
		if (!(editorPart instanceof ITextEditor)){
			throw new WorkbenchPartNotFound("Given editor is not instance of ITextEditor");
		}
	}
	
	/**
	 * Initialize editor with given title
	 * @param title title of desired editor
	 * @throws EditorNotFoundException when currently active editor isn't instance of ITextEditor 
	 */
	
	public TextEditor(final String title){
		super(title);
		if (!(editorPart instanceof ITextEditor)){
			throw new WorkbenchPartNotFound("Given editor is not instance of ITextEditor");
		}
	}
	
	/**
	 * @return content of this editor
	 */

	public String getText(){
		return TextEditorHandler.getInstance().getDocument(getTextEditorPart()).get();
	}
	
	/**
	 * Set text into editor (and replaces everything already in there)
	 * This implementation is manipulating with IDocument of this TextEditor. For keyboard-like typing see @link{#typeText(int, int, String) typeText()}
	 * @param text given test that will be set as editor text
	 */
	public void setText(final String text) {
		TextEditorHandler.getInstance().setText(getTextEditorPart(), text);
	}
	
	/**
	 * Returns nth line from this editor. It could be even currently collapsed text. Lines are counted from 0.
	 * @param line
	 * @return
	 */
	
	public String getTextAtLine(final int line) {
		return TextEditorHandler.getInstance().getTextAtLine(getTextEditorPart(), line);
	}
	
	/**
	 * Inserts text on defined line and offset. Note, that offset doesn't mean column to which insert will be performed, but it means nth character from start of the line. 
	 * Thus inserting after first tab character means to insert with offset 1, not with offset 4 (eclipses default tab width)
	 * This implementation is manipulating with IDocument of this TextEditor. For keyboard-like typing see @link{#typeText(int, int, String) typeText()}
	 * @param line Lines are counted from 0
	 * @param offset Text will be inserted right after nth character on specified line
	 */
	
	public void insertText(final int line, final int offset, final String text){
		TextEditorHandler.getInstance().insertText(getTextEditorPart(), line, offset, text);
	}
	
	/**
	 * Insert line before line specified by line parameter. 
	 * This implementation is manipulating with IDocument of this TextEditor. For keyboard-like typing see @link{#typeText(int, int, String) typeText()}
	 * @param line Lines are counted from 0.
	 * @param text
	 */
	public void insertLine(final int line, final String text){
		try {
			insertText(line, 0, text+TextEditorHandler.getInstance().getDocument(getTextEditorPart()).getLineDelimiter(line));
		} catch (BadLocationException e) {
			throw new RuntimeException("Line provided is invalid for this editor", e);
		}
	}
	
	
	/**
	 * Returns string of currently selected text.
	 * 
	 * @return string of selected text
	 */
	
	public String getSelectedText(){
		return TextEditorHandler.getInstance().getSelectedText(getTextEditorPart());
	}
	
	
	/**
	 * Selects whole line #lineNumber.
	 * @param lineNumber Lines are counted from 0.
	 */
	
	public void selectLine(final int lineNumber){
		TextEditorHandler.getInstance().selectLine(getTextEditorPart(), lineNumber);
	}
	
	protected int getCursorOffset(){
		ITextSelection textSelection = (ITextSelection) getEditorPart().getSite().getSelectionProvider().getSelection();
		return textSelection.getOffset();
	}
	
	protected ITextEditor getTextEditorPart(){
		return (ITextEditor) getEditorPart();
	}
	
}
