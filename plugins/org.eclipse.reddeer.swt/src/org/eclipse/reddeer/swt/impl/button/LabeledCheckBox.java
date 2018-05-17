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
package org.eclipse.reddeer.swt.impl.button;

import org.eclipse.reddeer.core.matcher.WithLabelMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Labeled Check Box implementation. Do not replace label with text.
 * 
 * @author apodhrad
 *
 */
public class LabeledCheckBox extends CheckBox {

	/**
	 * Finds a check box with a given label.
	 * 
	 * @param label the label
	 */
	public LabeledCheckBox(String label) {
		this(label, 0);
	}

	/**
	 * Finds a check box with a given label at the specified index.
	 * 
	 * @param label the label
	 * @param index the index
	 */
	public LabeledCheckBox(String label, int index) {
		this(null, label, index);
	}

	/**
	 * Finds a check box with a given label inside a given reference.
	 * 
	 * @param ref the reference
	 * @param label the label
	 */
	public LabeledCheckBox(ReferencedComposite ref, String label) {
		this(ref, label, 0);
	}

	/**
	 * Finds a check box with a given label inside a given reference at the specified index.
	 * 
	 * @param ref the reference
	 * @param label the label
	 * @param index the index
	 */
	public LabeledCheckBox(ReferencedComposite ref, String label, int index) {
		super(ref, index, new WithLabelMatcher(label));
	}
}
