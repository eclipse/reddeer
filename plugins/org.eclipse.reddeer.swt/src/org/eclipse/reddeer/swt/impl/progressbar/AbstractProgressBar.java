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
package org.eclipse.reddeer.swt.impl.progressbar;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.swt.api.ProgressBar;
import org.eclipse.reddeer.core.handler.ProgressBarHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

/**
 * Abstract class for ProgressBar
 * 
 * @author rhopp
 *
 */

public abstract class AbstractProgressBar extends AbstractControl<org.eclipse.swt.widgets.ProgressBar> implements ProgressBar {
	
	protected AbstractProgressBar(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.ProgressBar.class, null, index, matchers);
	}
	
	protected AbstractProgressBar(org.eclipse.swt.widgets.ProgressBar widget) {
		super(widget);
	}
	
	/**
	 * Returns state of this progressbar. One of SWT.NORMAL, SWT.ERROR, SWT.PAUSED. 
	 * Note: This operation is a hint and is not supported on platforms that do not have this concept.
	 *
	 * @return the state
	 */
	@Override
	public int getState() {
		return ProgressBarHandler.getInstance().getState(swtWidget);
	}
}
