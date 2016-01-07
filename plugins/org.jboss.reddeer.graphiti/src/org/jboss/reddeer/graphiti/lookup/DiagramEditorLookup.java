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
package org.jboss.reddeer.graphiti.lookup;

import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.ui.IEditorPart;
import org.jboss.reddeer.gef.GEFLayerException;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;

/**
 * Lookup for {@link org.eclipse.graphiti.ui.editor.DiagramEditor}
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class DiagramEditorLookup {

	protected final Logger log = Logger.getLogger(this.getClass());

	private static DiagramEditorLookup instance;

	private DiagramEditorLookup() {

	}

	/**
	 * Gets the single instance of DiagramEditorLookup.
	 *
	 * @return single instance of DiagramEditorLookup
	 */
	public static DiagramEditorLookup getInstance() {
		if (instance == null) {
			instance = new DiagramEditorLookup();
		}
		return instance;
	}

	/**
	 * Finds a diagram editor in an active editor.
	 *
	 * @return Diagram editor
	 */
	public DiagramEditor findDiagramEditor() {
		return findDiagramEditor(new ActiveEditor().getIEditorPart());
	}

	/**
	 * Finds a diagram editor in a given editor part.
	 * 
	 * @param editorPart
	 *            Editor part
	 * @return Diagram editor
	 */
	public DiagramEditor findDiagramEditor(final IEditorPart editorPart) {
		DiagramEditor diagramEditor = Display.syncExec(new ResultRunnable<DiagramEditor>() {
			@Override
			public DiagramEditor run() {
				return (DiagramEditor) editorPart.getAdapter(DiagramEditor.class);
			}
		});
		if (diagramEditor == null) {
			throw new GEFLayerException("Cannot find diagram editor in a given editor part");
		}
		return diagramEditor;
	}

	/**
	 * Helper class for achieving active {@link org.eclipse.ui.IEditorPart}. This class can be removed when
	 * {@link org.jboss.reddeer.workbench.lookup} is exported.
	 * 
	 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
	 *
	 */
	private class ActiveEditor extends DefaultEditor {

		public ActiveEditor() {
			super();
		}

		public IEditorPart getIEditorPart() {
			return getEditorPart();
		}
	}
}
