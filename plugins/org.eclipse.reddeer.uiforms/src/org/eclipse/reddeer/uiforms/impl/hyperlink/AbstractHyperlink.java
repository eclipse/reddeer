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
package org.eclipse.reddeer.uiforms.impl.hyperlink;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;
import org.eclipse.reddeer.uiforms.api.Hyperlink;
import org.eclipse.reddeer.uiforms.handler.HyperLinkHandler;

/**
 * Common ancestor for all {@link Hyperlink} implementations
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractHyperlink extends AbstractControl<org.eclipse.ui.forms.widgets.Hyperlink> implements Hyperlink {

	protected AbstractHyperlink(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.ui.forms.widgets.Hyperlink.class, refComposite, index, matchers);
		setFocus();
	}
	
	protected AbstractHyperlink(org.eclipse.ui.forms.widgets.Hyperlink widget){
		super(widget);
	}
	
	public String getText() {
		return HyperLinkHandler.getInstance().getText(swtWidget);
	}
	
	public void activate() {
		HyperLinkHandler.getInstance().activate(swtWidget);
	}
}
