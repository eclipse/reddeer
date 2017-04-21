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
package org.eclipse.reddeer.jface.matcher;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.handler.CompositeHandler;
import org.eclipse.reddeer.core.handler.LabelHandler;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;

/**
 * Matcher which matches dialogs title with given title text.
 * @author rawagner
 *
 */
public class DialogWithTitle extends BaseMatcher<String>{
	
	private Matcher<String> matcher;
	
	/**
	 * 
	 * @param title Dialog's title
	 */
	public DialogWithTitle(String title) {
		this(new WithTextMatcher(title));
	}
	
	/**
	 * 
	 * @param matcher which matches dialog's title
	 */
	public DialogWithTitle(Matcher<String> matcher) {
		this.matcher = matcher;
	}

	@Override
	public boolean matches(Object item) {
		if(item instanceof Shell){
			Shell shell = (Shell)item;
			CompositeHandler compositeHandler = CompositeHandler.getInstance();
			LabelHandler lHandler = LabelHandler.getInstance();
			try{
				Composite shellComposite = (Composite)compositeHandler.getChildren(shell)[0];
				Control[] compositeChildren = compositeHandler.getChildren(shellComposite);
				Control labelControl = compositeChildren[2];
				if(labelControl instanceof Label){
					String labelText = lHandler.getText((Label)labelControl);
					return matcher.matches(labelText);
				}
			}catch (Exception e) {
				return false;
			}
			
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "Shell with title "+matcher;
	}

}
