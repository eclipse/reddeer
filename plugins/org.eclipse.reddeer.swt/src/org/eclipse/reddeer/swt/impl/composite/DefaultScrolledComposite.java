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
import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Default implementation of scrolled composite
 * 
 * @author rawagner
 *
 */
public class DefaultScrolledComposite extends AbstractScrolledComposite {

	public DefaultScrolledComposite(ScrolledComposite widget) {
		super(widget);
	}

	public DefaultScrolledComposite() {
		this(0);
	}

	public DefaultScrolledComposite(int index) {
		this(null, index);
	}

	public DefaultScrolledComposite(Matcher<?>... matchers) {
		this(null, matchers);
	}

	public DefaultScrolledComposite(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}

	public DefaultScrolledComposite(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}

	public DefaultScrolledComposite(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}

	public DefaultScrolledComposite(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}

}
