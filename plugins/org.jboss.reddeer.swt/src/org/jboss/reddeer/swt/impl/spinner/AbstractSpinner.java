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
package org.jboss.reddeer.swt.impl.spinner;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Spinner;
import org.jboss.reddeer.core.handler.SpinnerHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all Spinner implementations
 * 
 * @author Andrej Podhradsky
 * 
 */
public abstract class AbstractSpinner extends AbstractWidget<org.eclipse.swt.widgets.Spinner> implements Spinner {

	private static final Logger log = Logger.getLogger(AbstractSpinner.class);
	
	protected AbstractSpinner(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.Spinner.class, refComposite, index, matchers);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Spinner#getValue()
	 */
	@Override
	public int getValue() {
		return SpinnerHandler.getInstance().getValue(getSWTWidget());
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Spinner#setValue(int)
	 */
	@Override
	public void setValue(int value) {
		log.info("Set spinner value to " + value);
		SpinnerHandler.getInstance().setValue(getSWTWidget(), value);
	}
}
