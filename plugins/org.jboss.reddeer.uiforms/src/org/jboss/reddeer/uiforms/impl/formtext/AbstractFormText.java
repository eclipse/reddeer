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
package org.jboss.reddeer.uiforms.impl.formtext;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;
import org.jboss.reddeer.uiforms.api.FormText;
import org.jboss.reddeer.core.handler.FormTextHandler;

public abstract class AbstractFormText extends AbstractWidget<org.eclipse.ui.forms.widgets.FormText> implements FormText {

	protected AbstractFormText(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.ui.forms.widgets.FormText.class, refComposite, index, matchers);
		setFocus();
	}

	public String getSelectionText() {
		return null;
	}

	public void click() {
		FormTextHandler.getInstance().click(swtWidget);
	}

	public String getText() {
		return FormTextHandler.getInstance().getText(swtWidget);
	}

	public void setFocus() {
		WidgetHandler.getInstance().setFocus(swtWidget);
	}

	public boolean hasFocus() {
		return FormTextHandler.getInstance().hasFocus(swtWidget);
	}

	public String getTooltipText() {
		return FormTextHandler.getInstance().getTooltipText(swtWidget);
	}
	
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtWidget);
	}
}
