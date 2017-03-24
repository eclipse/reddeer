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
package org.jboss.reddeer.swt.impl.clabel;

import org.eclipse.swt.graphics.Image;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.CLabel;
import org.jboss.reddeer.core.handler.CLabelHandler;
import org.jboss.reddeer.core.handler.ControlHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractControl;
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
	 * @see org.jboss.reddeer.swt.api.CLabel#getText()
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
