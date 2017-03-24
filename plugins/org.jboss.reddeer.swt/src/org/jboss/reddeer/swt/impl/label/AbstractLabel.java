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
package org.jboss.reddeer.swt.impl.label;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Label;
import org.jboss.reddeer.core.handler.LabelHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractControl;

public abstract class AbstractLabel extends AbstractControl<org.eclipse.swt.widgets.Label> implements Label {

	protected AbstractLabel(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.Label.class, refComposite, index, matchers);
	}
	
	protected AbstractLabel(org.eclipse.swt.widgets.Label widget){
		super(widget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Label#getText()
	 */
	@Override
	public String getText() {
		String text = LabelHandler.getInstance().getText(swtWidget);
		return text;
	}
	
}
