/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
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
import org.jboss.reddeer.workbench.api.View;

/**
 * Wait condition for views checking whether specified view is open
 * 
 * @author rawagner@redhat.com
 */
public class ViewIsOpen extends AbstractWaitCondition{
	
	private View view;
	
	/**
	 * Checks whether specified view is open
	 * @param view view to check if it is open
	 */
	public ViewIsOpen(View view) {
		this.view = view;
	}

	@Override
	public boolean test() {
		return view.isOpened();
	}
	
	@Override
	public String description() {
		return "'"+view.getTitle()+"' view is open";
	}
	
	@Override
	public String errorMessageUntil() {
		return "'"+view.getTitle()+"' view is not open";
	}
	
	
	@Override
	public String errorMessageWhile() {
		return "'"+view.getTitle()+"' view is still open";
	}

}
