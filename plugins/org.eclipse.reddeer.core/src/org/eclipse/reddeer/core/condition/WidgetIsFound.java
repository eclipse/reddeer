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
package org.eclipse.reddeer.core.condition;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.matcher.AndMatcher;
import org.eclipse.reddeer.common.matcher.MatcherBuilder;
import org.eclipse.reddeer.core.handler.ControlHandler;
import org.eclipse.reddeer.core.lookup.WidgetLookup;
import org.eclipse.reddeer.core.matcher.ClassMatcher;

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
	private Widget foundWidget;
	private WidgetLookup widgetLookup = WidgetLookup.getInstance();
	
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
			this.foundWidget = null;
			return false;
		}
		this.foundWidget = properWidget;
		return true;
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
	
	/**
	 * Gets And Matcher encapsulating all matchers used in the wait condition.
	 * @return all matchers used in and matcher
	 */
	public AndMatcher getAndMatcher() {
		return am;
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public Widget getResult() {
		return this.foundWidget;
	}
}
