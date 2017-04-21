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
package org.eclipse.reddeer.swt.impl.text;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.Text;
import org.eclipse.reddeer.core.handler.ControlHandler;
import org.eclipse.reddeer.core.handler.TextHandler;
import org.eclipse.reddeer.swt.keyboard.KeyboardFactory;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

/**
 * Abstract class for all Text implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractText extends AbstractControl<org.eclipse.swt.widgets.Text> implements Text {
	
	private static final Logger log = Logger.getLogger(AbstractText.class);

	protected AbstractText(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.Text.class, refComposite, index, matchers);
	}
	
	protected AbstractText(org.eclipse.swt.widgets.Text widget){
		super(widget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Text#setText(java.lang.String)
	 */
	@Override
	public void setText(String str) {
		log.info("Text set to: " + str);
		TextHandler.getInstance().setText(swtWidget, str);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Text#getText()
	 */
	@Override
	public String getText() {
		String text = TextHandler.getInstance().getText(swtWidget);
		return text;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Text#getMessage()
	 */
	@Override
	public String getMessage() {
		return TextHandler.getInstance().getMessage(swtWidget);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Text#setFocus()
	 */
	@Override
	public void setFocus() {
		log.debug("Set focus to Text");
		ControlHandler.getInstance().setFocus(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Text#typeText(java.lang.String)
	 */
	@Override
	public void typeText(String text) {
		log.info("Type text " + text);
		setText("");
		setFocus();
		forceFocus();
		KeyboardFactory.getKeyboard().type(text);		
	}
	
	private void forceFocus() {
		log.debug("Force focus to receive keyboard events");
		ControlHandler.getInstance().forceFocus(swtWidget);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Text#isReadOnly()
	 */
	@Override
	public boolean isReadOnly(){
		return TextHandler.getInstance().isReadOnly(swtWidget);
	}
}
