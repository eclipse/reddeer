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
package org.eclipse.reddeer.gef.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.gef.editor.GEFEditor;

/**
 * Returns true if a given GEF editor has more than the specified number of edit parts.
 * 
 * @author apodhrad
 *
 */
public class EditorHasEditParts extends AbstractWaitCondition {

	private GEFEditor gefEditor;
	private int numberOfEditParts;

	/**
	 * Contructs a condition which is fulfilled if a given GEF editor has at least 2 edit parts (one of them is root).
	 * 
	 * @param gefEditor
	 *            GED editor
	 */
	public EditorHasEditParts(GEFEditor gefEditor) {
		this(gefEditor, 1);
	}

	/**
	 * Contructs a condition which is fulfilled if a given GEF editor has more than the specified number edit parts.
	 * 
	 * @param gefEditor
	 *            GEF editor
	 * @param numberOfEditParts
	 *            Number of edit parts
	 */
	public EditorHasEditParts(GEFEditor gefEditor, int numberOfEditParts) {
		this.gefEditor = gefEditor;
		this.numberOfEditParts = numberOfEditParts;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		return gefEditor.getNumberOfEditParts() > numberOfEditParts;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "the number of edit parts is " + numberOfEditParts;
	}

}
