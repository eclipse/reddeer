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
package org.eclipse.reddeer.eclipse.wst.xml.ui.tabletree;

import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;

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
