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
package org.eclipse.reddeer.core.resolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.exception.CoreLayerException;

/**
 * Widget resolver resolves children and parent of supported type.
 * Note: Must be used in UI Thread.
 * 
 * Supported widgets:
 * <ul>
 * <li>ExpandBar</li>
 * <li>ExpandItem</li>
 * <li>CTabFolder</li>
 * <li>TabFolder</li>
 * <li>CTabItem</li>
 * <li>TabItem</li>
 * <li>ToolBar</li>
 * <li>Composite</li>
 * <li>Control</li>
 * <li>Text</li>
 * </ul>
 * 
 * @author Jiri Peterka
 *
 */
public class WidgetResolver  {
	
	private static final Logger log = Logger.getLogger(WidgetResolver.class);
	
	private static WidgetResolver instance = null;
	private WidgetResolver() {}
	
	/**
	 * Returns instance of WidgetResolver.
	 * 
	 * @return widgetResolver instance
	 */
	public static WidgetResolver getInstance() {
		if (instance == null) instance = new WidgetResolver();
		return instance;
	}
	
	private static final Class<?>[] supported = {
		ExpandBar.class,
		ExpandItem.class,
		CTabFolder.class,
		TabFolder.class,
		CTabItem.class,
		TabItem.class,
		ToolBar.class,
		Composite.class,
		Control.class
	};
	
	/**
	 * Returns parent of specified widget. If widget is not resolvable, return null.
	 * Must be called from UI Thread.
	 * 
	 * @param w widget to resolve
	 * @return parent widget or null
	 */
	public Widget getParent(Widget w) {
			if (w instanceof ExpandBar) {
				return ((ExpandBar) w).getParent();
			}
			if (w instanceof ExpandItem) {
				return ((ExpandItem) w).getParent();
			}
			else if (w instanceof CTabFolder) {
				return ((CTabFolder) w).getParent();
			}
			else if (w instanceof TabFolder) {
				return ((TabFolder) w).getParent();
			}
			else if (w instanceof CTabItem) {
				return ((CTabItem) w).getParent();
			}
			else if (w instanceof TabItem) {
				return ((TabItem) w).getParent();
			}
			else if (w instanceof ToolBar) {
				return ((ToolBar) w).getParent();
			}
			else if (w instanceof ToolItem) {
				return ((ToolItem) w).getParent();
			}
			else if (w instanceof TableItem) {
				return ((TableItem) w).getParent();
			}
			else if (w instanceof Composite) {
				Composite parent = ((Composite)w).getParent();
				
				// If composite under TabFolder find widget under TabFolder siblings
				if (parent instanceof TabFolder) {
						
					if ((parent == null) || parent.isDisposed()) {
						throw new CoreLayerException("TabFolder is null or disposed while resolving");
					}
					TabItem[] tabItems = ((TabFolder) parent).getItems();

					int index = Arrays.asList(tabItems).indexOf(w);
					
					if (index == -1) {
						throw new CoreLayerException("Widget not found under TabFolder");
					}
					
					return tabItems[index];
				}
				return parent;
			}
			else if (w instanceof Control) {
				return ((Control) w).getParent();
			}
		log.warn("Cannot find parent of widget. Widget type is not supported (" + w.getClass() + ")");
		return null;
	}

	
	/**
	 * Returns children of specified widget. If widget is not resolvable, return null.
	 * Must be called from UI Thread.
	 * 
	 * @param w widget to resolve
	 * @return list of children widgets
	 */
	public List<Widget> getChildren(Widget w) {
		List<Widget> children = new ArrayList<Widget>();
		
		if (w instanceof ExpandBar) {
			Widget[] items = ((ExpandBar) w).getItems();
			children = Arrays.asList(items);
			
		} else if (w instanceof ExpandItem) {
			Control control = ((ExpandItem) w).getControl();
			if (control != null) children.add(control);
			
		} else if (w instanceof CTabFolder) {
			List<Widget> tempList = new ArrayList<>();
			tempList.addAll(Arrays.asList(((CTabFolder) w).getChildren()));
			tempList.addAll(Arrays.asList(((CTabFolder) w).getItems()));
			children = tempList;

		} else if (w instanceof TabFolder) {
			Widget[] items = ((TabFolder) w).getItems();
			children = Arrays.asList(items);
		}
		else if (w instanceof CTabItem) {
			Control control = ((CTabItem) w).getControl();
			if (control != null) children.add(control);
		}
		else if (w instanceof TabItem) {
			Control control = ((TabItem) w).getControl();
			if (control != null) children.add(control);
		}
		else if (w instanceof ToolBar) {
			Widget[] items = ((ToolBar) w).getItems();
			children = Arrays.asList(items);
		} else if (w instanceof ToolItem) {
			// do nothing
		} else if (w instanceof Table) {
			List<Widget> tempList = new ArrayList<Widget>();
			tempList.addAll(Arrays.asList(((Table)w).getChildren()));
			tempList.addAll(Arrays.asList(((Table)w).getItems()));
			children = tempList;
		}
		else if (w instanceof TableItem) {
			// do nothing
		}
		else if (w instanceof Composite)  {
			Widget[] items = ((Composite) w).getChildren();
			children = Arrays.asList(items);
		} 
		else if (w instanceof Control) {
			// do nothing
		}
		else {
			log.warn("Cannot find children of widget. Widget type is not supported (" + w.getClass() + ")");
		}
		
		return children;
	}

	/**
	 * Finds out whether specified widget has parent.
	 * 
	 * @param w widget to resolve
	 * @return true if widget has a parent, false otherwise.
	 */
	public boolean hasParent(Widget w) {
		return getParent(w) != null;
	}

	
	/**
	 * Find out whether specified widget has children.
	 * 
	 * @param w widget to resolve
	 * @return true if widget has children, false otherwise
	 */
	public boolean hasChildren(Widget w) {
		return isResolvable(w) && (getChildren(w).size() > 0) ;
	}

	
	/**
	 * Finds out whether specified widget is resolvable or not.
	 * 
	 * @param w widget to resolve
	 * @return true if widget is resolvable, false otherwise.
	 */
	public boolean isResolvable(Widget w) {
		// DateTime is not supported because of eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=206868
		if (w instanceof DateTime) return false;
		return Arrays.asList(supported).contains(w.getClass());
	}
}
