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
package org.jboss.reddeer.eclipse.wst.xml.ui.tabletree;

import org.eclipse.ui.texteditor.ITextEditor;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;

/**
 * Represents the Source page of {@link XMLMultiPageEditorPart}.
 * 
 * @author Lucia Jelinkova
 *
 */
public class XMLSourcePage extends TextEditor {

	/**
	 * Creates instance from existing text editor reference.
	 *
	 * @param editor
	 *            the editor
	 */
	protected XMLSourcePage(ITextEditor editor) {
		super(editor);
	}

	@Override
	public XMLEditorFile getAssociatedFile() {
		return new XMLEditorFile(super.getAssociatedFile());
	}

}
