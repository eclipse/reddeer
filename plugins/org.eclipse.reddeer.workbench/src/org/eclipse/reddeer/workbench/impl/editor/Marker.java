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
package org.eclipse.reddeer.workbench.impl.editor;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Represents validation marker in editor.
 * 
 * @author rawagner
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 */
public class Marker {

	private Annotation annotation;
	private IEditorPart editor;

	/**
	 * Constructs a marker instance.
	 * 
	 * @param annotation
	 *            annotation
	 * @param editorPart
	 *            editor part
	 */
	public Marker(Annotation annotation, IEditorPart editorPart) {
		this.annotation = annotation;
		this.editor = editorPart;
	}

	/**
	 * Returns an annotation associated to the marker.
	 * 
	 * @return annotation associated to the marker
	 */
	protected Annotation getAnnotation() {
		return annotation;
	}

	/**
	 * Returns validation marker text.
	 * 
	 * @return validation marker text
	 */
	public String getText() {
		return annotation.getText();
	}

	/**
	 * Returns validation marker type.
	 * 
	 * @return validation marker type
	 */
	public String getType() {
		return annotation.getType();
	}

	/**
	 * Returns line number of validation marker.
	 * 
	 * @return line number of validation marker
	 */
	public int getLineNumber() {
		ITextEditor textEditor = (ITextEditor) editor.getAdapter(ITextEditor.class);
		if (textEditor == null) {
			return -1;
		}
		final IDocumentProvider documentProvider = textEditor.getDocumentProvider();
		if (documentProvider == null) {
			return -1;
		}
		IAnnotationModel model = documentProvider.getAnnotationModel(textEditor.getEditorInput());
		IDocument doc = documentProvider.getDocument(editor.getEditorInput());
		int offset = model.getPosition(annotation).getOffset();
		int line = -1;
		try {
			line = doc.getLineOfOffset(offset) + 1;
		} catch (BadLocationException e) {
			throw new WorkbenchLayerException("Unable to find line number for AYT marker", e);
		}
		return line;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return annotation.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Marker other = (Marker) obj;
		return other.annotation.equals(this.annotation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Marker [text=" + getText() + ", type=" + getType() + ", lineNumber=" + getLineNumber() + "]";
	}

}
