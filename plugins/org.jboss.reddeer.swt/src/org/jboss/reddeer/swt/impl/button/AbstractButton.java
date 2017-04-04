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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.handler.ButtonHandler;
import org.jboss.reddeer.core.matcher.WithStyleMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.condition.ControlIsEnabled;
import org.jboss.reddeer.swt.widgets.AbstractControl;

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
