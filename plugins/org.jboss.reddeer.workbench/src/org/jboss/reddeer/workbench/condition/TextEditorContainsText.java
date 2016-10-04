/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.workbench.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;

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
