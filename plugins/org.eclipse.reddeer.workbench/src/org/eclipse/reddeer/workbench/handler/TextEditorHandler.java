/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.handler;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IBlockTextSelection;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;
import org.eclipse.swt.graphics.Point;

/**
 * TextEditor handler handles operations for TextEditor instances.
 * @author rhopp
 */
public class TextEditorHandler extends EditorHandler{
	
	private static TextEditorHandler instance;
	
	/**
	 * Gets instance of TextEditorHandler.
	 * 
	 * @return instance of TextEditorHandler
	 */
	public static TextEditorHandler getInstance(){
		if(instance == null){
			instance = new TextEditorHandler();
		}
		return instance;
	}

    /**
     * Sets specified text to specified {@link ITextEditor} editor. 
     * Current content of text editor is replaced.
     * 
     * @param editor editor to set text
     * @param text text to set
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
     * Gets text on specified line of specified text editor. Notice, that this method manipulates with 
     * document of specified editor - that mean this works with current content of editor, not with visible content.
     * 
     * @param editor editor to handle
     * @param line line with desired text
     * @return text on specified line of specified text editor
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

    /**
     * Gets number of lines of specified text editor.
     * 
     * @param editor editor to get lines of
     * @return number of lines of specified text editor
     */
    public int getNumberOfLines(final ITextEditor editor) {
        return Display.syncExec(new ResultRunnable<Integer>() {
            @Override
            public Integer run() {
                return getDocument(editor).getNumberOfLines();
            }
        });
    }

    /**
     * Inserts text to specified line and specified offset of specified text editor. Notice, that this method manipulates with 
     * document of specified editor - that mean this works with current content of editor, not with visible content.
     * 
     * @param editor editor to insert text into
     * @param line line to insert text 
     * @param offset offset of text
     * @param text text to insert
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
     * Inserts text to specified offset of text editor. Notice, that this method manipulates with 
     * document of specified editor - that mean this works with current content of editor, not with visible content.
     * 
     * @param editor editor to handle
     * @param offset offset to insert text into 
     * @param text text to insert
     */
    public void insertText(final ITextEditor editor, final int offset, final String text) {
        Display.syncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    getDocument(editor).replace(offset,0, text);
                } catch (BadLocationException e) {
                    throw new WorkbenchLayerException("Provided offset is invalid for this editor",e);
                }
            }
        });
    }

    /**
     * Gets currently selected text in specified editor.
     * 
     * @param editor editor to handle
     * @return currently selected text of specified text editor
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
     * Returns line number of i-th occurrence of given text.
     *  
     * @param editor editor to handle
     * @param text text to find
     * @param textIndex index of text occurrence in editor
     * @return line number of text found, -1 otherwise
     */    
    public int getLineOfText(final ITextEditor editor, final String text, final int textIndex) {
    	return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				int textOffset = getPositionOfText(editor, text, textIndex);
				int line = -1;
				try {
					line = getDocument(editor).getLineOfOffset(textOffset);
				} catch (BadLocationException e) {
					line = -1;
				}
				return line;
			}
    	});
    }

    /**
     * Selects line with specified number in specified text editor.
     * 
     * @param editor editor to handle
     * @param lineNumber line to select
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
     * Selects specified text on specified index in specified text editor.
     *  
     * @param editor editor to handle
     * @param text text to select
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
	 * Gets position of first character of specified text in specified text editor.
	 * 
	 * @param editor to handle
	 * @param text text to get position
	 * @return position of first character of specified text if exists, -1 otherwise 
	 */
	public int getPositionOfText(final ITextEditor editor, final String text) {
		return getPositionOfText(editor, text, 0);
	}
	
	/**
	 * Gets position of first character of specified text on specified index in specified text editor.
	 * 
	 * @param editor editor to handle
	 * @param text text to get position
	 * @param index index of text
	 * @return position of first character of specified text if exists, -1 otherwise 
	 */
	public int getPositionOfText(final ITextEditor editor, final String text, final int index) {
		return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				if(index < 0){
					return -1;
				}
				String doc = getDocument(editor).get();
				int textIndex = 0;
				int i = 0;
				while (i <= index){
					i++;
					textIndex = doc.indexOf(text,textIndex);
					if(textIndex == -1){
						return -1;
					}
					textIndex += text.length();
				}
				return textIndex - text.length();
			}
		});
	}

	/**
	 * Sets cursor position to specified line and specified column in specified text editor.
	 * 
	 * @param editor editor to handle
	 * @param line line to set cursor to
	 * @param column column to set cursor to
	 */
	public void setCursorPosition(final ITextEditor editor, final int line,
			final int column) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				int lineOffset = 0;
				try {
					lineOffset = getDocument(editor).getLineOffset(line);
				} catch (BadLocationException e) {
					throw new WorkbenchLayerException("Unable to select line"
							+ line + " and column " + column);
				}
				editor.selectAndReveal(lineOffset + column, 0);
			}

		});
	}
	
	/**
	 * Gets the current position of the cursor.
	 *
	 * @param editor
	 *            editor to handle
	 * @return zero based position of the cursor in the editor.
	 */
	public Point getCursorPosition(final ITextEditor editor) {
		Integer offset = Display.syncExec(new ResultRunnable<Integer>() {
			public Integer run() {
				ISelection selection = editor.getSelectionProvider().getSelection();
				if (selection instanceof ITextSelection) {
					return ((ITextSelection) selection).getOffset();
				} else {
					throw new WorkbenchLayerException("Unsuported ISelection type.");
				}
			}
		});
		int line = getLineOfOffest(editor, offset);
		int column = offset - getLineOffset(editor, line);
		return new Point(line, column);
	}
	
	/**
	 * Sets cursor position to specified offset in specified text editor.
	 * 
	 * @param editor editor to handle
	 * @param offset where cursor should be set 
	 */
	public void setCursorPosition(final ITextEditor editor, final int offset) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				editor.selectAndReveal(offset, 0);
			}

		});
	}
	
	/**
	 * Returns offset of the line within specified text editor.
	 *
	 * @param editor editor to handle
	 * @param line line to get offset of
	 * @return the line offset
	 */
	public int getLineOffset(final ITextEditor editor, final int line) {
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				try {
					return getDocument(editor).getLineOffset(line);
				} catch (BadLocationException e) {
					throw new WorkbenchLayerException("Unable to get offset of line");
				}
			}

		});
	}
	
	/**
	 * Returns line of offset within specified text editor.
	 *
	 * @param editor editor to handle
	 * @param offset offset to line of
	 * @return the line
	 */
	public int getLineOfOffest(final ITextEditor editor, final int offset){
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				try {
					return getDocument(editor).getLineOfOffset(offset);
				} catch (BadLocationException e) {
					throw new WorkbenchLayerException("Unable to get line of offset");
				}
			}

		});
	}

    /**
     * Gets {@link IDocument} element of specified text editor.
     * 
     * @param editor editor to handle
     * @return document of specified text editor
     */
    public IDocument getDocument(ITextEditor editor) {
        return editor.getDocumentProvider()
                .getDocument(editor.getEditorInput());
    }
}

