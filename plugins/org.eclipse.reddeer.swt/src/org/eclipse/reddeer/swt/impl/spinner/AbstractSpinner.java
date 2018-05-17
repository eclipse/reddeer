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
package org.eclipse.reddeer.swt.impl.spinner;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.Spinner;
import org.eclipse.reddeer.core.handler.SpinnerHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

/**
 * Abstract class for all Spinner implementations
 * 
 * @author Andrej Podhradsky
 * 
 */
public abstract class AbstractSpinner extends AbstractControl<org.eclipse.swt.widgets.Spinner> implements Spinner {

	private static final Logger log = Logger.getLogger(AbstractSpinner.class);
	
	protected AbstractSpinner(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.Spinner.class, refComposite, index, matchers);
	}
	
	protected AbstractSpinner(org.eclipse.swt.widgets.Spinner widget) {
		super(widget);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Spinner#getValue()
	 */
	@Override
	public int getValue() {
		return SpinnerHandler.getInstance().getValue(getSWTWidget());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Spinner#setValue(int)
	 */
	@Override
	public void setValue(int value) {
		log.info("Set spinner value to " + value);
		SpinnerHandler.getInstance().setValue(getSWTWidget(), value);
	}
}
