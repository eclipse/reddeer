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
package org.eclipse.reddeer.swt.impl.ccombo;

import org.eclipse.reddeer.core.matcher.WithLabelMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.CCombo;

/**
 * CCombo with label implementation
 * 
 * @author Andrej Podhradsky
 *
 */
public class LabeledCCombo extends AbstractCCombo implements CCombo {

	/**
	 * Searches for custom combo with label.
	 *
	 * @param label the label of the custom combo
	 */
	public LabeledCCombo(String label) {
		this(null, label);
	}

	/**
	 * Searches for custom combo with label inside given composite.
	 *
	 * @param referencedComposite composite inside which custom combo should be looked for
	 * @param label label of the custom combo
	 */
	public LabeledCCombo(ReferencedComposite referencedComposite, String label) {
		super(referencedComposite, 0, new WithLabelMatcher(label));
	}
}
