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
package org.jboss.reddeer.swt.impl.progressbar;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.ProgressBar;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for ProgressBar
 * 
 * @author rhopp
 *
 */

public abstract class AbstractProgressBar extends AbstractWidget<org.eclipse.swt.widgets.ProgressBar> implements ProgressBar {
	
	protected AbstractProgressBar(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.ProgressBar.class, null, index, matchers);
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
