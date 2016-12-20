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
 * Toggle Button implementation
 * @author Vlado Pakan
 *
 */
public class ToggleButton extends AbstractButton {

	private static final Logger log = Logger.getLogger(ToggleButton.class);
		
	/**
	 * Toggle button with index 0.
	 */
	public ToggleButton() {
		this((ReferencedComposite) null);
	}
	
	public ToggleButton(org.eclipse.swt.widgets.Button widget){
		super(widget);
	}
	
	/**
	 * Creates Toggle button inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public ToggleButton(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Creates Toggle button with given text.
	 *
	 * @param text the text
	 */
	public ToggleButton(String text) {
		this(null, text);
	}
	
	/**
	 * Creates Toggle button with given text inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param text the text
	 */
	public ToggleButton(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new WithMnemonicTextMatcher(text));
	}
	
	/**
	 * Toggle button that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public ToggleButton(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Toggle button that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public ToggleButton(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Creates Toggle button with given index.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public ToggleButton(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Creates Toggle button with given index inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public ToggleButton(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, SWT.TOGGLE, matchers);
	}
	
    /**
     * Returns true when Toggle Button is selected.
     *
     * @return true, if is selected
     */
	public boolean isSelected() {
		return ButtonHandler.getInstance().isSelected(swtWidget);
	}
	
	/**
	 * Sets Toggle Button to state 'checked'.
	 *
	 * @param checked whether or not the button is checked
	 */
	public void toggle(boolean checked){
		if (checked){
			if (isSelected()) {
				log.debug("Toggle Button " + getDescriptiveText() + " already checked, no action performed");
				return;
			}else{
				log.info("Checking Toggle Button " + getDescriptiveText());
				click();
			}
		}else{
			if (isSelected()) {
				log.info("Unchecking Toggle Button " + getDescriptiveText());
				click();
			}else{
				log.debug("Toggle Button " + getDescriptiveText() + " already unchecked, no action performed");
				return;
			}
		}
	}
}