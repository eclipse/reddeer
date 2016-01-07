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
package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.handler.ButtonHandler;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * RadioButton is button implementation that can be selected
 * @author jjankovi
 *
 */
public class RadioButton extends AbstractButton {
	
	private static final Logger log = Logger.getLogger(RadioButton.class);
	
	/**
	 * Creates Radio button.
	 */
	public RadioButton() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * Creates Radio button inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public RadioButton(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Creates Radio button with given text.
	 *
	 * @param text the text
	 */
	public RadioButton(String text) {
		this(null, text);
	}
	
	/**
	 * Creates Radio button with given text inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param text the text
	 */
	public RadioButton(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new WithMnemonicTextMatcher(text));
	}
	
	/**
	 * Radio button that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public RadioButton(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Radio button that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public RadioButton(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Creates Radio button with given index that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public RadioButton(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Creates Radio button with given index inside given composite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public RadioButton(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, SWT.RADIO, matchers);
	}
	
	/**
	 * Returns true when Radio Button is selected	.
	 *
	 * @return true, if is selected
	 */
	public boolean isSelected() {
		return ButtonHandler.getInstance().isSelected(swtWidget);
	}
	
	/**
	 * Sets Radio Button to state 'checked'.
	 *
	 * @param checked whether or not the button should be checked
	 */
	public void toggle(boolean checked){
		if (checked){
			if (isSelected()) {
				log.debug("Radio Button " + getDescriptiveText() + " already checked, no action performed");
				return;
			}else{
				log.info("Select radio button " + getDescriptiveText());
				click();
			}
		}else{
			if (isSelected()) {
				log.info("Unchecking Radio Button " + getDescriptiveText());
				click();
			}else{
				log.debug("Radio button " + getDescriptiveText() + " already unselected, no action performed");
				return;
			}
		}
	}
}