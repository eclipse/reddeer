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
package org.eclipse.reddeer.swt.impl.combo;

import org.eclipse.reddeer.swt.api.Combo;
import org.eclipse.reddeer.core.matcher.WithLabelMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Combo with label implementation
 * @author Vlado Pakan
 *
 */
public class LabeledCombo extends AbstractCombo implements Combo {
   
	/**
	 * Searches for combo with label .
	 *
	 * @param label the label
	 */
	public LabeledCombo(String label) {
		this(null, label);
	}
	
	/**
	 * Searches for combo with label inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param label the label
	 */
	public LabeledCombo(ReferencedComposite referencedComposite, String label) {
		super(referencedComposite, 0, new WithLabelMatcher(label));
	}
}
