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
package org.jboss.reddeer.core.condition;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.matcher.AndMatcher;
import org.jboss.reddeer.common.matcher.MatcherBuilder;
import org.jboss.reddeer.common.platform.RunningPlatform;
import org.jboss.reddeer.core.handler.ControlHandler;
import org.jboss.reddeer.core.handler.ItemHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.lookup.WidgetLookup;
import org.jboss.reddeer.core.matcher.ClassMatcher;

/**
 * WidgetIsFound is general condition to find desired widget.
 * 
 * @author Jiri Peterka, mlabuda@redhat.com
 * 
 */
public class WidgetIsFound extends AbstractWaitCondition {

	private Control parent;
	private AndMatcher am;
	private int index;
	private Widget properWidget;
	WidgetLookup widgetLookup = WidgetLookup.getInstance();
	
	/**
	 * Looks for widgets under given parent control with given index and matching specified matchers.
	 *
	 * @param clazz class of a widget to find
	 * @param parent given parent control
	 * @param index given index
	 * @param matchers given matchers
	 */
	public WidgetIsFound(Class<? extends Widget> clazz, Control parent, int index, Matcher<?>... matchers) {
		if (parent == null) {
			this.parent = widgetLookup.findParent();
		} else {
			this.parent = parent;
		}
		if (matchers != null && matchers.length > 0) {
			am=new AndMatcher(MatcherBuilder.getInstance().addMatcher(matchers, new ClassMatcher(clazz)));
		} else {
			am = new AndMatcher(new ClassMatcher(clazz));
		}
		this.index=index;
	}

	/**
	 * Looks for first widget under given parent control matching specified matchers.
	 *
	 * @param clazz class of a widget to find
	 * @param parent given parent control
	 * @param matchers given matchers
	 */	
	public WidgetIsFound(Class<? extends Widget> clazz, Control parent, Matcher<?>... matchers) {
		this(clazz, parent, 0, matchers);
	}

	/**
	 * Looks for first widget under the default parent control matching specified matchers.
	 *
	 * @param clazz class of a widget to find
	 * @param matchers given matchers
	 */		
	public WidgetIsFound(Class<? extends Widget> clazz, Matcher<?>... matchers) {		
		this(clazz, null,0, matchers);
	}

	/**
	 * Looks for first widget under the default parent control.
	 * 
	 * @param clazz class of a widget to find
	 */
	public WidgetIsFound(Class<? extends Widget> clazz) {
		this(clazz, (Matcher[]) null);
	}
	
	/**
	 * Tests if given widget is found.
	 *
	 * @return true if widget is found, false otherwise
	 */
	public boolean test() {
		properWidget = widgetLookup.activeWidget(parent, am, index);	
		
		if (properWidget == null || 
				(properWidget instanceof Control && !ControlHandler.getInstance().isVisible((Control)properWidget))) {
			return false;
		}
		return true;
	}

	/**
	 * Gets found widget.
	 *
	 * @return found widget
	 */
	public Widget getWidget(){
		if (properWidget != null) {
			setFocus();
		}
		return properWidget;
	}
	
	/**
	 * Gets condition description.
	 *
	 * @return the string
	 */
	@Override
	public String description() {
		return "widget is found";
	}

	private void setFocus(){
		if (RunningPlatform.isWindows() && properWidget instanceof Button &&
				((WidgetHandler.getInstance().getStyle((Button)properWidget) & SWT.RADIO) != 0)){
			// do not set focus because it also select radio button on Windows
		} else {
			if(properWidget instanceof Item){
				ItemHandler.getInstance().setFocus((Item)properWidget);
			} else if (properWidget instanceof Control){
				ControlHandler.getInstance().setFocus((Control)properWidget);
			}
		}
	}
	
	/**
	 * Gets And Matcher encapsulating all matchers used in the wait condition.
	 * @return all matchers used in and matcher
	 */
	public AndMatcher getAndMatcher() {
		return am;
	}
}
