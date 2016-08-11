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
import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;

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
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
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
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "Editor title is "+title;
	}

}
