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
package org.jboss.reddeer.gef.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.gef.editor.GEFEditor;

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
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		return gefEditor.getNumberOfEditParts() > numberOfEditParts;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "the number of edit parts is " + numberOfEditParts;
	}

}
