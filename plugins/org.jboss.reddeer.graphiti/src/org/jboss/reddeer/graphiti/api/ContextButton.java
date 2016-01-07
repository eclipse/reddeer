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
package org.jboss.reddeer.graphiti.api;

import java.util.List;

/**
 * API for context button.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * 
 */
public interface ContextButton {

	/**
	 * Click the context button.
	 */
	void click();

	/**
	 * Retuns a text of the context button.
	 * 
	 * @return text
	 */
	String getText();

	/**
	 * Returns a list of context buttons from sub menu.
	 * 
	 * @return list of context buttons from sub menu.
	 */
	List<ContextButton> getContextButtons();
}
