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
package org.eclipse.reddeer.swt.test;

import java.util.List;

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.lookup.WidgetLookup;
import org.eclipse.reddeer.core.resolver.WidgetResolver;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class WidgetResolverTest {
	private Logger log = Logger.getLogger(WidgetResolverTest.class);

	/**
	 * For each widget found in active window check that for each 
	 * child widget returned by WidgetResolver.getInstance().getChildren(widget) 
	 * the parent returned by WidgetResolver.getInstance().getParent(childWidget) 
	 * is equal to the widget with the exception of CTabItem when the parent 
	 * returned for its control is CTabFolder which is the parent to both of them.
	 */
	@Test
	public void parentMatchesChildrenTest() {
		Shell shell = new DefaultShell().getSWTWidget();
		List<Widget> all = WidgetLookup.getInstance().activeWidgets(shell, new BaseMatcher<Widget>() {
			@Override
			public boolean matches(Object obj) {
				return true;
			}

			@Override
			public void describeTo(Description desc) {				
			}
			
		});
		log.info("Testing " + all.size() + " widgets for matching children to parent.");

		Assert.assertFalse("Shell is empty, test cannot be done.", all.isEmpty());

		for (Widget c: all) {
			List<Widget> children = getChildrenSync(c);
			Widget p = getParentSync(c);
			for (Widget w: children) {
				Widget parent = getParentSync(w);
						
				Assert.assertNotNull("Widget " + syncToString(w) + " was returned as a child of control " + syncToString(c) + " but WidgetResolver.getInstance().getParent() returned null.", parent);
				if(c instanceof CTabItem) {
					//Now implementation should return its 'natural' parent CTabFolder.
					Assert.assertEquals("For child " + syncToString(w) + " of CTabItem was return wrong parent.", p, parent);
				} else {
					Assert.assertEquals("For child " + syncToString(w) + " was returned wrong parent " + syncToString(parent), c, parent);
				}
				log.info("Child widget " + syncToString(w) + " matches to its parent widget " + syncToString(c));
			}
		} 
	}

	List<Widget> getChildrenSync(final Widget widget) {
		return Display.syncExec(new ResultRunnable<List<Widget>>() {
			@Override
			public List<Widget> run() {
				return WidgetResolver.getInstance().getChildren(widget);			
			}
		});
	}

	Widget getParentSync(final Widget widget) {
		return Display.syncExec(new ResultRunnable<Widget>() {
			@Override
			public Widget run() {
				return WidgetResolver.getInstance().getParent(widget);			
			}
		});
	}

	String syncToString(final Widget widget) {
		return Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return widget.toString();			
			}
		});
	}

}
