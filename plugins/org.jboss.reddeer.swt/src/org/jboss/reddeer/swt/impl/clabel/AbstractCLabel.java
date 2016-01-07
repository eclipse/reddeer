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

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.CLabel;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.core.util.ObjectUtil;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all CLabel implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractCLabel extends AbstractWidget<org.eclipse.swt.custom.CLabel> implements CLabel {
	
	protected AbstractCLabel(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.custom.CLabel.class, refComposite, index, matchers);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.CLabel#getText()
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}
	
	/**
	 * See {@link CLabel}.
	 *
	 * @return the tooltip text
	 */
	@Override
	public String getTooltipText() {
		return WidgetHandler.getInstance().getToolTipText(swtWidget);
	}
	
	/**
	 * See {@link CLabel}.
	 *
	 * @return the alignment
	 */
	@Override
	public int getAlignment() {
		return (Integer)ObjectUtil.invokeMethod(swtWidget, "getAlignment");
	}
	
	/**
	 * See {@link CLabel}.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean hasImage() {
		return ObjectUtil.invokeMethod(swtWidget, "getImage") != null;
	}
}
