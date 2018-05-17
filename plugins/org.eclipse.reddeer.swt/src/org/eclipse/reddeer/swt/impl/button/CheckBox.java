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
package org.eclipse.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.handler.ButtonHandler;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
/**
 * Class represents Button with type Toggle (Checkbox)
 * 
 * @author rhopp
 * 
 */

public class CheckBox extends AbstractButton {

	private static final Logger log = Logger.getLogger(CheckBox.class);
	
	/**
	 * Checkbox with index 0.
	 */
	public CheckBox() {
		this((ReferencedComposite) null);
	}
	
	public CheckBox(org.eclipse.swt.widgets.Button widget){
		super(widget);
	}
	
	/**
	 * Checkbox inside given referencedComposite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public CheckBox(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Checkbox with given text.
	 *
	 * @param text the text
	 */
	public CheckBox(String text) {
		this(null, text);
	}
	
	/**
	 * Checkbox that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public CheckBox(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Checkbox that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public CheckBox(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Checkbox with given text inside given referencedComposite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param text the text
	 */
	public CheckBox(ReferencedComposite referencedComposite,String text) {
		this(referencedComposite, 0, new WithMnemonicTextMatcher(text));
	}
	
	/**
	 * Checkbox with given index that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public CheckBox(int index, Matcher<?>... matchers){
		this(null, index, matchers);
	}
	
	/**
	 * Checkbox with given index inside given referencedComposite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public CheckBox(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers){
		super(referencedComposite, index, SWT.CHECK, matchers);
	}
	
	/**
	 * Returns true when Check Box is checked.
	 *
	 * @return true, if is checked
	 */
	public boolean isChecked() {
		return ButtonHandler.getInstance().isSelected(swtWidget);
	}
	
	/**
	 * Sets checkbox to state 'checked'.
	 *
	 * @param checked whether checked or not
	 */
	public void toggle(boolean checked){
		log.info("Select checkbox " + getDescriptiveText());
		if (checked){
			if (isChecked()) {
				log.debug("Checkbox " + getDescriptiveText() + " already selected, no action performed");
				return;
			}else{
				log.info("Check checkbox " + getDescriptiveText());
				click();
			}
		}else{
			if (isChecked()) {
				log.info("Uncheck checkbox " + getDescriptiveText());
				click();
			}else{
				log.debug("Checkbox " + getDescriptiveText() + " not checked, no action performed");
				return;
			}
		}
	}
}
