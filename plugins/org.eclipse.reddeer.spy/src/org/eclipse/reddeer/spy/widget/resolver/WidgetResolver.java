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
package org.eclipse.reddeer.spy.widget.resolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TaskBar;
import org.eclipse.swt.widgets.TaskItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.reddeer.spy.widget.ListChild;

/**
 * Widget resolver resolves children and parent of supported types.
 * 
 * @author mlabuda@redhat.com
 * @since 0.8.0
 *
 */
public class WidgetResolver  {
	
	private static WidgetResolver instance = null;
	
	private WidgetResolver() {}
	
	/**
	 * Gets instance of WidgetResolver.
	 * @return instance of WidgetResolver
	 */
	public static WidgetResolver getInstance() {
		if (instance == null) {
			instance = new WidgetResolver();
		}
		return instance;
	}
	
	/**
	 * Gets parent of specific widget. If widgets is not resolvable, null is returned.
	 * 
	 * @param w widget to resolve
	 * @return parent widget or null
	 */
	public Widget getParent(Widget w) {
		if (w instanceof Composite) {
			return ((Composite) w).getParent();
		} else if (w instanceof Control) {
			return ((Control) w).getParent();
		} else if (w instanceof TreeColumn) {
			return ((TreeColumn) w).getParent();
		} else if (w instanceof CoolItem) {
			return ((CoolItem) w).getParent();
		} else if (w instanceof ExpandItem) {
			return ((ExpandItem) w).getParent();
		} else if (w instanceof CTabItem) {
			return ((CTabItem) w).getParent();
		} else if (w instanceof TabItem) {
			return ((TabItem) w).getParent();
		} else if (w instanceof ToolItem) {
			return ((ToolItem) w).getParent();
		} else if (w instanceof TreeItem) {
			return ((TreeItem) w).getParent();	
		} else if (w instanceof Menu) {
			return ((Menu) w).getParent();
		} else if (w instanceof ScrollBar) {
			return ((ScrollBar) w).getParent();
		} else if (w instanceof ToolTip) {
			return ((ToolTip) w).getParent();
		} else {
			return null;
		}
	}

	
	/**
	 * Gets children of specified widget.
	 * @param w widget to resolve
	 * @return list of children widgets or empty list if there are no children
	 */
	public List<Widget> getChildren(Widget w) {
		List<Widget> children = new ArrayList<Widget>();
		if (w instanceof CoolItem) {
			Control control = ((CoolItem) w).getControl();
			if (control != null) {
				children.add(control);
			}
		} else if (w instanceof CTabItem) {
			Control control = ((CTabItem) w).getControl();
			if (control != null) {
				children.add(control);
			}
		} else if (w instanceof ExpandItem) {
			Control control = ((ExpandItem) w).getControl();
			if (control != null) {
				children.add(control);
			}
		} else if (w instanceof TabItem) {
			Control control = ((TabItem) w).getControl();
			if (control != null) {
				children.add(control);
			}
		} else if (w instanceof ToolItem) {
			Control control = ((ToolItem) w).getControl();
			if (control != null) {
				children.add(control);
			}
		} else if (w instanceof TreeItem) {
			TreeItem[] items = ((TreeItem) w).getItems();
			if (items.length > 0) {
				children.addAll(Arrays.asList(items));
			}
		} else if (w instanceof Menu) {
			MenuItem[] items = ((Menu) w).getItems();
			if (items.length > 0) {
				children.addAll(Arrays.asList(items));
			}
		} else if (w instanceof TaskBar) {
			TaskItem[] items = ((TaskBar) w).getItems();
			if (items.length > 0) {
				children.addAll(Arrays.asList(items));
			}
		} else if (w instanceof Tray) {
			TrayItem[] items = ((Tray) w).getItems();
			if (items.length > 0) {
				children.addAll(Arrays.asList(items));
			}
		} else if (w instanceof org.eclipse.swt.widgets.List) {
			String[] items = ((org.eclipse.swt.widgets.List) w).getItems();
			if (items.length > 0) {
				for (String listChild: items) {
					children.add(new ListChild(w, listChild));
				}
			}			
		} else if (w instanceof Composite) {
			Control[] items = ((Composite) w).getChildren();
			if (items.length > 0) {
				children.addAll(Arrays.asList(items));
			}
		}
		return children;
	}

	/**
	 * Finds out whether specified widget has parent.
	 * @param w widget
	 * @return true if widget has parent, false otherwise
	 */
	public boolean hasParent(Widget w) {
		return getParent(w) != null;
	}

	/**
	 * Finds out whether specified widget has children.
	 * 
	 * @param w widget
	 * @return true if widget has children (or at least 1 child), false otherwise
	 */
	public boolean hasChildren(Widget w) {
		return getChildren(w).size() > 0;
	}
}
