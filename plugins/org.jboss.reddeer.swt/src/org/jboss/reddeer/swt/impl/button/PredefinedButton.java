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
package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.core.matcher.WithStyleMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

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
