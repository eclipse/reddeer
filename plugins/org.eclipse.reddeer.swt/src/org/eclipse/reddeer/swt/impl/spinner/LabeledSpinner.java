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
package org.eclipse.reddeer.swt.impl.spinner;

import org.eclipse.reddeer.swt.api.Spinner;
import org.eclipse.reddeer.core.matcher.WithLabelMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Spinner with label implementation
 * 
 * @author Andrej Podhradsky
 * 
 */
public class LabeledSpinner extends AbstractSpinner implements Spinner {

	/**
	 * Default spinner with a label.
	 *
	 * @param label the label
	 */
	public LabeledSpinner(String label) {
		this(null, label);
	}

	/**
	 * Default spinner with a label inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param label the label
	 */
	public LabeledSpinner(ReferencedComposite referencedComposite, String label) {
		super(referencedComposite, 0, new WithLabelMatcher(label));
	}
}
