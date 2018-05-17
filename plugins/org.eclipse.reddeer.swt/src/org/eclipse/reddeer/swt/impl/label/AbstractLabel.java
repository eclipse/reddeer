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
package org.eclipse.reddeer.swt.impl.label;

import org.eclipse.swt.graphics.Image;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.swt.api.Label;
import org.eclipse.reddeer.core.handler.LabelHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

public abstract class AbstractLabel extends AbstractControl<org.eclipse.swt.widgets.Label> implements Label {

	protected AbstractLabel(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.Label.class, refComposite, index, matchers);
	}
	
	protected AbstractLabel(org.eclipse.swt.widgets.Label widget){
		super(widget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Label#getText()
	 */
	@Override
	public String getText() {
		String text = LabelHandler.getInstance().getText(swtWidget);
		return text;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Label#getImage()
	 */
	@Override
	public Image getImage(){
		return LabelHandler.getInstance().getImage(swtWidget);
	}
}
