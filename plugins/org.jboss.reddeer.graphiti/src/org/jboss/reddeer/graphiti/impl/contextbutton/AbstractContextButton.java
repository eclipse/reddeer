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
package org.jboss.reddeer.graphiti.impl.contextbutton;

import java.util.List;

import org.eclipse.graphiti.tb.IContextButtonEntry;
import org.jboss.reddeer.graphiti.api.ContextButton;
import org.jboss.reddeer.graphiti.handler.ContextButtonHandler;

/**
 * Abstract class for ContextButton implementation.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public abstract class AbstractContextButton implements ContextButton {

	protected IContextButtonEntry contextButtonEntry;

	public AbstractContextButton(IContextButtonEntry contextButtonEntry) {
		this.contextButtonEntry = contextButtonEntry;
	}

	@Override
	public void click() {
		ContextButtonHandler.getInstance().click(contextButtonEntry);
	}

	@Override
	public String getText() {
		return ContextButtonHandler.getInstance().getText(contextButtonEntry);
	}
	
	@Override
	public List<ContextButton> getContextButtons() {
		return ContextButtonHandler.getInstance().getContextButtons(contextButtonEntry);
	}

}
