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
package org.jboss.reddeer.swt.impl.ccombo;

import org.jboss.reddeer.core.matcher.WithLabelMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.api.CCombo;

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
