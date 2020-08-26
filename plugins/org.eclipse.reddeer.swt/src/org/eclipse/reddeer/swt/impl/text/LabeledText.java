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
package org.eclipse.reddeer.swt.impl.text;

import org.eclipse.reddeer.swt.api.Text;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.matcher.WithLabelMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Text with label implementation
 * @author Jiri Peterka
 *
 */
public class LabeledText extends AbstractText implements Text {

	/**
	 * Default text with a label.
	 *
	 * @param label the label
	 */
	public LabeledText(String label) {
		this(null, label);
	}
	
	/**
	 * Default text with a label inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param label the label
	 */
	public LabeledText(ReferencedComposite referencedComposite, String label) {
		super(referencedComposite, 0, new WithLabelMatcher(label));
	}
	
	/**
	 * Default text with a label matcher inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matcher matcher for the label matching
	 */
	public LabeledText(ReferencedComposite referencedComposite, Matcher<?>... matcher) {
		super(referencedComposite, 0, matcher);
	}
}
