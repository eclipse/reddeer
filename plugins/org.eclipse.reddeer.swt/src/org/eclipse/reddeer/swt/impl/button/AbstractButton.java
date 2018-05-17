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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.handler.ButtonHandler;
import org.eclipse.reddeer.core.matcher.WithStyleMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.condition.ControlIsEnabled;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

/**
 * Basic Button class is abstract class for all Button implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractButton extends AbstractControl<org.eclipse.swt.widgets.Button> implements Button {

	private static final Logger log = Logger.getLogger(AbstractButton.class);
	
	protected AbstractButton(org.eclipse.swt.widgets.Button widget){
		super(widget);
	}

	protected AbstractButton (ReferencedComposite refComposite, int index, int style, Matcher<?>... matchers){
        super(org.eclipse.swt.widgets.Button.class, refComposite, index, createMatchers(style, matchers));
	}
	
	public static Matcher<?>[] createMatchers(int style, Matcher<?>... matchers) {
		List<Matcher<?>> list= new ArrayList<Matcher<?>>();

		list.add(new WithStyleMatcher(style));			
		list.addAll(Arrays.asList(matchers));
		return list.toArray(new Matcher[list.size()]);
	}
	
	@Override
	public void click() {
		log.info("Click button " + getDescriptiveText());
		new WaitUntil(new ControlIsEnabled(this));
		ButtonHandler.getInstance().click(swtWidget);
	}
	
	@Override
	public String getText() {
		return ButtonHandler.getInstance().getText(swtWidget);
	}

	/**
	* Returns some text identification of button - 
	* either text, tooltip text or info that no text is available. 
	* Used in logging. 
	* 
	* @return descriptive text of a button
	*/
	protected String getDescriptiveText() {
		return getText() != null ? getText() : (
				getToolTipText() != null ? getToolTipText()
				: "with no text or tooltip");
	}
	
	@Override
	public void setFocus(){
		ButtonHandler.getInstance().setFocus(swtWidget);
	}
}
