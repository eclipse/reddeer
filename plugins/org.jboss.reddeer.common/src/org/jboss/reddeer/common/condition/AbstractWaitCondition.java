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
package org.jboss.reddeer.common.condition;

/**
 * Abstract implementation of {@link WaitCondition} which provides initial
 * implementations of description() and erroMessage(). The method description()
 * returns a canonical name of the implementing class. The method errorMessage()
 * simply calls the method description().
 * 
 * @author Andrej Podhradsky
 *
 */
public abstract class AbstractWaitCondition implements WaitCondition {

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#description()
	 */
	@Override
	public String description() {
		return getClass().getCanonicalName();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#errorMessage()
	 */
	@Override
	public String errorMessage() {
		return description();
	}

}
