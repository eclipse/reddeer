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
package org.eclipse.reddeer.common.condition;

/**
 * Abstract implementation of {@link WaitCondition} which provides initial
 * implementations of description() and erroMessage(). The method description()
 * returns a canonical name of the implementing class. The method errorMessage()
 * simply calls the method description().
 * 
 * @author Andrej Podhradsky
 * @author jkopriva@redhat.com
 *
 */
public abstract class AbstractWaitCondition implements WaitCondition {

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#description()
	 */
	@Override
	public String description() {
		return getClass().getCanonicalName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#errorMessageWhile()
	 */
	@Override
	public String errorMessageWhile() {
		return description();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#errorMessageUntil()
	 */
	@Override
	public String errorMessageUntil() {
		return description();
	}

}
