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
package org.eclipse.reddeer.uiforms.impl.formtext;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;
import org.eclipse.reddeer.uiforms.api.FormText;
import org.eclipse.reddeer.uiforms.handler.FormTextHandler;

public abstract class AbstractFormText extends AbstractControl<org.eclipse.ui.forms.widgets.FormText> implements FormText {

	protected AbstractFormText(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.ui.forms.widgets.FormText.class, refComposite, index, matchers);
		setFocus();
	}
	
	protected AbstractFormText(org.eclipse.ui.forms.widgets.FormText widget){
		super(widget);
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
}
