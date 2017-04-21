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
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.workbench.impl.editor.DefaultEditor;

/**
 * Check whether editor is active
 * @author rawagner
 *
 */
public class EditorWithTitleIsActive extends AbstractWaitCondition {
	
	private String title;
	
	/**
	 * Default constructor.
	 *
	 * @param title of editor
	 */
	public EditorWithTitleIsActive(String title){
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		try{
			return new DefaultEditor().getTitle().equals(title);
		} catch (RedDeerException e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "Editor title is "+title;
	}

}
