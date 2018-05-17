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
package org.eclipse.reddeer.swt.impl.composite;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.handler.ScrolledCompositeHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

/**
 * Abstract class for all ScrolledComposite implementations
 * 
 * @author rawagner
 *
 */
public abstract class AbstractScrolledComposite extends AbstractControl<ScrolledComposite>
		implements org.eclipse.reddeer.swt.api.ScrolledComposite {

	public AbstractScrolledComposite(ScrolledComposite widget) {
		super(widget);
	}

	public AbstractScrolledComposite(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(ScrolledComposite.class, referencedComposite, index, matchers);
	}

	@Override
	public Control getControl() {
		return ScrolledCompositeHandler.getInstance().getContent(swtWidget);
	}

}
