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

import org.eclipse.swt.SWT;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.core.matcher.WithStyleMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Parent for all all prescribed button implementations like OK, Cancel, Next, etc.
 * @author Jiri Peterka
 *
 */
public abstract class PredefinedButton extends AbstractButton {

	protected PredefinedButton(ReferencedComposite refComposite, int index,
			String text, int style) {
		
		super(refComposite, index, SWT.PUSH,new WithMnemonicTextMatcher(text),new WithStyleMatcher(style));

	}

}
