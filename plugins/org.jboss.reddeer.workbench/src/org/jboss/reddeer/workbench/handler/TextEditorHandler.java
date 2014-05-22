/**
 * 
 */
package org.jboss.reddeer.workbench.handler;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IBlockTextSelection;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.texteditor.ITextEditor;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.workbench.exception.WorkbenchLayerException;

/**
 * TextEditor handler handles operations for TextEditor instances.
 * @author rhopp
 */
public class TextEditorHandler {

    private static TextEditorHandler instance;

    private TextEditorHandler() {

    }

    /**
     * 
     * @return
     */
    public static TextEditorHandler getInstance() {
        if (instance == null) {
            instance = new TextEditorHandler();
        }
        return instance;
    }

    /**
     * Sets text for given editor replacing its document content.
     * @param editor
     * @param text
     */
    public void setText(final ITextEditor editor, final String text) {
        Display.syncExec(new Runnable() {

            @Override
            public void run() {
                editor.getDocumentProvider()
                        .getDocument(editor.getEditorInput()).set(text);
            }
        });
    }

    /**
     * Returns text on given line of given editor. Note, that this method manipulates with editor's document (i.e. not
     * what is currently visible, but what is the actual content of this editor)
     * @param editor
     * @param line
     * @return
     */
    public String getTextAtLine(final ITextEditor editor, final int line) {
        return Display.syncExec(new ResultRunnable<String>() {
            @Override
            public String run() {
                try {
                    if (getDocument(editor).getLineDelimiter(line) != null) {
                        return getDocument(editor).get(
                                getDocument(editor).getLineOffset(line),
                                getDocument(editor).getLineLength(line))
                                .replace(
                                        getDocument(editor).getLineDelimiter(
                                                line), "");
                    } else {
                        return getDocument(editor).get(
                                getDocument(editor).getLineOffset(line),
                                getDocument(editor).getLineLength(line));
                    }

                } catch (BadLocationException e) {
                    throw new WorkbenchLayerException(
                            "Line provided is invalid for this editor", e);
                }
            }
        });
    }

    public int getNumberOfLines(final ITextEditor editor) {
        return Display.syncExec(new ResultRunnable<Integer>() {
            @Override
            public Integer run() {
                return getDocument(editor).getNumberOfLines();
            }
        });
    }

    /**
     * Inserts text to given line into editor. Note, that this method manipulates with editor's document (i.e. not what
     * is currently visible, but what is the actual content of this editor)
     * @param editor
     * @param line
     * @param offset
     * @param text
     */
    public void insertText(final ITextEditor editor, final int line,
            final int offset, final String text) {
        Display.syncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    getDocument(editor).replace(
                            getDocument(editor).getLineOffset(line) + offset,
                            0, text);
                } catch (BadLocationException e) {
                    throw new WorkbenchLayerException(
                            "Provided line or offset are invalid for this editor",
                            e);
                }
            }
        });
    }

    /**
     * Returns currently selected text in given editor
     * @param editor
     * @return
     */
    public String getSelectedText(final ITextEditor editor) {
        return Display.syncExec(new ResultRunnable<String>() {
            @Override
            public String run() {
                ISelection selection = editor.getSelectionProvider()
                        .getSelection();
                if (selection instanceof ITextSelection) {
                    return ((ITextSelection) selection).getText();
                } else if (selection instanceof IBlockTextSelection) {
                    return ((IBlockTextSelection) selection).getText();
                }
                throw new WorkbenchLayerException("Unsuported ISelection type.");
            }
        });
    }

    /**
     * Selects
     * @param editor
     * @param lineNumber
     */
    public void selectLine(final ITextEditor editor, final int lineNumber) {
        Display.syncExec(new Runnable() {

            @Override
            public void run() {
                int offset = 0;
                int length = 0;
                try {
                    offset = getDocument(editor).getLineOffset(lineNumber);
                    length = getDocument(editor).getLineLength(lineNumber);
                } catch (BadLocationException e) {
                    throw new WorkbenchLayerException("Unable to select line "
                            + lineNumber, e);
                }
                editor.selectAndReveal(offset, length);
            }
        });
    }

    /**
     * Selects text
     * @param editor
     * @param text to select
     * @param textIndex index of text
     */
    public void selectText(final ITextEditor editor, final String text,
            final int textIndex) {
        Display.syncExec(new Runnable() {

            @Override
            public void run() {
                boolean found = false;
                int iStartIndex = 0;
                int iRow = 0;
                String editorText = getDocument(editor).get();

                if (editorText != null && editorText.length() > 0
                        && editorText.contains(text)) {
                    int iOccurenceIndex = 0;
                    while (!found && iRow < getNumberOfLines(editor)) {
                        String lineText = getTextAtLine(editor, iRow);
                        System.out.println(lineText);
                        iStartIndex = 0;
                        while (!found && lineText.contains(text)) {
                            if (iOccurenceIndex == textIndex) {
                                found = true;
                                iStartIndex += lineText.indexOf(text);
                            } else {
                                iOccurenceIndex++;
                                int iNewStartIndex = lineText.indexOf(text)
                                        + text.length();
                                iStartIndex += iNewStartIndex;
                                lineText = lineText.substring(iNewStartIndex);
                            }
                        }
                        if (!found) {
                            iRow++;
                        }
                    }
                }

                if (found) {
                    int offset = 0;
                    try {
                        offset = getDocument(editor).getLineOffset(iRow)
                                + iStartIndex;
                    } catch (BadLocationException e) {
                        throw new WorkbenchLayerException("Unable to find "
                                + text + " in editor", e);
                    }
                    editor.selectAndReveal(offset, text.length());
                } else {
                    throw new WorkbenchLayerException("Unable to find " + text
                            + " in editor");
                }
            }
        });
    }

    /**
     * returns IDocument element for given ITextEditor
     * @param editor
     * @return
     */
    public IDocument getDocument(ITextEditor editor) {
        return editor.getDocumentProvider()
                .getDocument(editor.getEditorInput());
    }
}
