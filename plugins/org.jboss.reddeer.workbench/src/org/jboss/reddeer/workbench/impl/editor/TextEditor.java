package org.jboss.reddeer.workbench.impl.editor;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.texteditor.ITextEditor;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.handler.TextEditorHandler;
import org.jboss.reddeer.core.lookup.EditorPartLookup;
import org.jboss.reddeer.core.matcher.EditorPartClassMatcher;
import org.jboss.reddeer.core.matcher.EditorPartTitleMatcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.workbench.api.Editor;
import org.jboss.reddeer.workbench.exception.WorkbenchLayerException;

/**
 * Represents text editors (implementing interface ITextEditor)
 * This implementation manipulates with IDocument of given editor. For keyboard-like 
 * typing use @link{org.jboss.reddeer.swt.Keyboard}.
 * 
 * @author rhopp
 * @author rawagner
 *
 */

public class TextEditor extends AbstractEditor implements Editor {

	private static final Logger log = Logger.getLogger(TextEditor.class);
	
	/**
	 * Initialize currently focused TextEditor.
	 * @throws WorkbenchPartNotFound when currently active editor isn't instance of ITextEditor
	 */
	public TextEditor() {
		super(EditorPartLookup.getInstance().getEditor());
		if (!(editorPart instanceof ITextEditor)){
			throw new WorkbenchLayerException("The active editor is not a text editor, but " + editorPart.getClass());
		}
	}
	
	/**
	 * Initialize editor with given title.
	 * @param title title of desired editor
	 * @throws WorkbenchPartNotFound when currently active editor isn't instance of ITextEditor 
	 */
	@SuppressWarnings("unchecked")
	public TextEditor(final String title) {
		super(EditorPartLookup.getInstance().getEditor(
				new EditorPartClassMatcher(ITextEditor.class), 
				new EditorPartTitleMatcher(new WithTextMatcher(title))));
	}
	
	/**
	 * Initialize editor with given title matcher
	 * @param title title of desired editor
	 * @throws WorkbenchPartNotFound when currently active editor isn't instance of ITextEditor 
	 */
	@SuppressWarnings("unchecked")
	public TextEditor(Matcher<String> title) {
		super(EditorPartLookup.getInstance().getEditor(
				new EditorPartClassMatcher(ITextEditor.class), 
				new EditorPartTitleMatcher(title)));
	}
	
	/**
	 * Create reddeer text editor from given eclipse text editor. 
	 * @param editor
	 */
	protected TextEditor(ITextEditor editor) {
		super(editor);
	}
	
	/**
	 * Returns the contents of the editor
	 * @return content of this editor
	 */
	public String getText() {
		return TextEditorHandler.getInstance().getDocument(getTextEditorPart()).get();
	}
	
	/**
	 * Set text into editor (and replaces everything already in there).
	 * This implementation is manipulating with IDocument of this TextEditor. 
	 * For keyboard-like typing see @link{#typeText(int, int, String) typeText()}.
	 * @param text given test that will be set as editor text
	 */
	public void setText(final String text) {
		log.info("Set text to editor");
		TextEditorHandler.getInstance().setText(getTextEditorPart(), text);
	}
	
	/**
	 * Returns nth line from this editor. It could be even currently collapsed text. Lines are counted from 0.
	 * @param line of editor
	 * @return text at given line
	 */
	public String getTextAtLine(final int line) {
		return TextEditorHandler.getInstance().getTextAtLine(getTextEditorPart(), line);
	}
	
	/**
	 * Returns number of lines in editor.
	 * @return number of lines
	 */
	public int getNumberOfLines() {
		return TextEditorHandler.getInstance().getNumberOfLines(getTextEditorPart());
	}
	
	/**
	 * Inserts text on defined line and offset. Note, that offset doesn't mean column to which insert will be performed,
	 * but it means nth character from start of the line. 
	 * Thus inserting after first tab character means to insert with offset 1, 
	 * not with offset 4 (eclipses default tab width).
	 * This implementation is manipulating with IDocument of this TextEditor. 
	 * For keyboard-like typing see @link{#typeText(int, int, String) typeText()}.
	 * @param line Lines are counted from 0
	 * @param offset Text will be inserted right after nth character on specified line
	 * @param text text to insert
	 */
	public void insertText(final int line, final int offset, final String text) {
		log.info("Insert text to editor at line " + line + ", offser " + offset);
		TextEditorHandler.getInstance().insertText(getTextEditorPart(), line, offset, text);
	}
	
	/**
	 * Inserts text on defined offset. Note, that offset doesn't mean column to which insert will be performed,
	 * but it means nth character from start of the line. 
	 * Thus inserting after first tab character means to insert with offset 1,
	 * not with offset 4 (eclipses default tab width).
	 * This implementation is manipulating with IDocument of this TextEditor. 
	 * For keyboard-like typing see @link{#typeText(int, int, String) typeText()}.
	 * @param offset where to insert text
	 * @param text to insert
	 */
	public void insertText(final int offset, final String text) {
		log.info("Insert text to editor at offser " + offset);
		TextEditorHandler.getInstance().insertText(getTextEditorPart(), offset, text);
	}
	
	/**
	 * Insert line before line specified by line parameter. 
	 * This implementation is manipulating with IDocument of this TextEditor. 
	 * For keyboard-like typing see @link{#typeText(int, int, String) typeText()}.
	 * @param line Lines are counted from 0.
	 * @param text to insert
	 */
	public void insertLine(final int line, final String text) {
		log.info("Insert text to editor at line " + line);
		try {
			insertText(line, 0, text + TextEditorHandler.getInstance()
					.getDocument(getTextEditorPart()).getLineDelimiter(line));
		} catch (BadLocationException e) {
			throw new WorkbenchLayerException("Line provided is invalid for this editor", e);
		}
	}
	
	
	/**
	 * Returns string of currently selected text.
	 * 
	 * @return string of selected text
	 */
	public String getSelectedText() {
		return TextEditorHandler.getInstance().getSelectedText(getTextEditorPart());
	}
	
	
	/**
	 * Selects whole line #lineNumber.
	 * @param lineNumber Lines are counted from 0.
	 */
	public void selectLine(final int lineNumber) {
		log.info("Select line " + lineNumber + " in editor");
		TextEditorHandler.getInstance().selectLine(getTextEditorPart(), lineNumber);
	}
	
	/**
	 * Selects text. 
	 * 
	 * @param text to select
	 */
	public void selectText(String text) {
		log.info("Select text in editor");
		TextEditorHandler.getInstance().selectText(getTextEditorPart(), text, 0);
	}
	
	/**
	 * Selects text with given index from possible multiple occurrences.
	 * 
	 * @param text to select
	 * @param index of text (if more occurrences)
	 */
	public void selectText(String text, int index) {
		log.info("Select text in editor with index " + index);
		TextEditorHandler.getInstance().selectText(getTextEditorPart(), text, index);
	}
	
	/**
	 * Gets position of first character of specified text in specified editor.
	 * 
	 * @param text to get position of
	 * @return offset of text, -1 if text was not found
	 */
	public int getPositionOfText(String text) {
		return getPositionOfText(text, 0);
	}

	/**
	 * Sets position of the cursor to the specified <var>line</var> and
	 * <var>column</var>.
	 * 
	 * @param line line, in which the cursor will be located
	 * @param column column in the line (greater than or equal to 0)
	 */
	public void setCursorPosition(int line, int column) {
		log.info("Set cursor position at ["+ line + ", " + column + "]");
		TextEditorHandler.getInstance().setCursorPosition(getTextEditorPart(),
				line, column);
	}

	/**
	 * Gets position of first character of specified text in specified editor.
	 * 
	 * @param text to get position of
	 * @param index of text
	 * @return offset of text, -1 if text was not found
	 */
	public int getPositionOfText(String text, int index) {
		return TextEditorHandler.getInstance().getPositionOfText(getTextEditorPart(), text, index);
	}
	
	/**
	 * Returns cursor offset.
	 * @return cursor offset
	 */
	protected int getCursorOffset() {
		ITextSelection textSelection = (ITextSelection) getEditorPart().getSite().getSelectionProvider().getSelection();
		return textSelection.getOffset();
	}
	
	/**
	 * Returns text editor widget.
	 * @return text editor widget
	 */
	protected ITextEditor getTextEditorPart() {
		return (ITextEditor) getEditorPart();
	}
}
