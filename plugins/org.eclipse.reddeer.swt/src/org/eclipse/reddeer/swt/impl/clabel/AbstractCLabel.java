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
package org.eclipse.reddeer.swt.impl.clabel;

import org.eclipse.swt.graphics.Image;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.swt.api.CLabel;
import org.eclipse.reddeer.core.handler.CLabelHandler;
import org.eclipse.reddeer.core.handler.ControlHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;
/**
 * Abstract class for all CLabel implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractCLabel extends AbstractControl<org.eclipse.swt.custom.CLabel> implements CLabel {
	
	protected AbstractCLabel(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.custom.CLabel.class, refComposite, index, matchers);
	}
	
	protected AbstractCLabel(org.eclipse.swt.custom.CLabel widget){
		super(widget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.CLabel#getText()
	 */
	@Override
	public String getText() {
		return CLabelHandler.getInstance().getText(swtWidget);
	}
	
	@Override
	public int getAlignment() {
		return CLabelHandler.getInstance().getAlignment(swtWidget);
	}
	
	@Override
	public Image getImage() {
		return CLabelHandler.getInstance().getImage(swtWidget);
	}
	
	public String getToolTipText(){
		return ControlHandler.getInstance().getToolTipText(swtWidget);
	}
}
