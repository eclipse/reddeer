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
package org.jboss.reddeer.uiforms.impl.hyperlink;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;
import org.jboss.reddeer.uiforms.api.Hyperlink;
import org.jboss.reddeer.core.handler.HyperLinkHandler;

/**
 * Common ancestor for all {@link Hyperlink} implementations
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractHyperlink extends AbstractWidget<org.eclipse.ui.forms.widgets.Hyperlink> implements Hyperlink {

	protected AbstractHyperlink(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.ui.forms.widgets.Hyperlink.class, refComposite, index, matchers);
		setFocus();
	}
	
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}
	
	public void activate() {
		HyperLinkHandler.getInstance().activate(swtWidget);
	}
	
	protected void setFocus() {
		WidgetHandler.getInstance().setFocus(swtWidget);
	}
}
