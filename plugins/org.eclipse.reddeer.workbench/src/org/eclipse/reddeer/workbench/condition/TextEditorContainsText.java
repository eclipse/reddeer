/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;

/**
 * Check if editor contains specified text
 * @author rawagner
 *
 */
public class TextEditorContainsText extends AbstractWaitCondition{
	
	private String text;
	private TextEditor editor;
	
	/**
	 * Default constructor
	 * @param editor to check
	 * @param text editor should contain
	 */
	public TextEditorContainsText(TextEditor editor, String text) {
		this.editor = editor;
		this.text = text;
	}

	@Override
	public boolean test() {
		return editor.getText().contains(text);
	}

	@Override
	public String description() {
		return "Editor '"+editor.getTitle()+"' contains text '"+text+"'";
	}

}
