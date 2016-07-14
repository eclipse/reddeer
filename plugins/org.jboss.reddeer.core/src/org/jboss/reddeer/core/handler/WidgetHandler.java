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
package org.jboss.reddeer.core.handler;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.lookup.WidgetLookup;
import org.jboss.reddeer.core.resolver.WidgetResolver;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ObjectUtil;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Widget}. 
 * 
 * @author Jiri Peterka
 * @author Rastislav Wagner
 * @author Jaroslav Jankovic
 */
public class WidgetHandler {

	private static WidgetHandler instance;
	
	private static final Logger log = Logger.getLogger(WidgetHandler.class);

	private WidgetHandler() {

	}

	/**
	 * Gets instance of WidgetHandler.
	 * 
	 * @return instance of WidgetHandler
	 */
	public static WidgetHandler getInstance() {
		if (instance == null) {
			instance = new WidgetHandler();
		}
		return instance;
	}

	/**
	 * Finds out whether specified {@link Widget} is enabled or not.
	 * 
	 * @param widget widget to handle
	 * @return true if widget is enabled, false otherwise
	 */
	public boolean isEnabled(Widget widget) {
		boolean ret = true;
		Object o = null;
		try {
			o = ObjectUtil.invokeMethod(widget, "isEnabled");
		} catch (RuntimeException e) {
			return true;
		}
		if (o == null) {
			return ret;
		}
		if (o instanceof Boolean) {
			ret = ((Boolean) o).booleanValue();
		}
		return ret;
	}
	
	/**
	 * Finds out whether specified {@link Widget} is disposed or not.
	 * 
	 * @param widget widget to handle
	 * @return true if widget is disposed, false otherwise
	 */
	public boolean isDisposed(final Widget widget) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return widget.isDisposed();
			}
		});
	}

	/**
	 * Finds out whether specified {@link Widget} is visible or not.
	 * 
	 * @param widget widget to handle
	 * @return true if widget is visible, false otherwise
	 */
	public boolean isVisible(Widget widget) {
		boolean ret = true;
		Object o = null;
		try {
			o = ObjectUtil.invokeMethod(widget, "isVisible");
		} catch (RuntimeException e) {
			throw new CoreLayerException(
					"Runtime error during checking widget visibility", e);
		}
		if (o == null) {
			return ret;
		}
		if (o instanceof Boolean) {
			ret = ((Boolean) o).booleanValue();
		}
		return ret;
	}


	/**
	 * Gets style of specified widget.
	 *
	 * @param <T> the generic type
	 * @param w widget to handle
	 * @return style of specified widget
	 */
	public <T extends Widget> int getStyle(final T w) {
		int style = Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				if (w instanceof Widget) {
					return ((Widget) w).getStyle();
				} else {
					throw new CoreLayerException("Unsupported type");
				}
			}
		});
		return style;
	}

	/**
	 * Sets specified text to specified widget.
	 *
	 * @param <T> the generic type
	 * @param widget widget to handle
	 * @param text text to set
	 */
	public <T extends Widget> void setText(final T widget, final String text) {
		try {
			ObjectUtil.invokeMethod(widget, "setText",
					new Class[] { String.class }, new Object[] { text });
		} catch (RuntimeException e) {
			throw new CoreLayerException(
					"Runtime error during setting widget's text", e);
		}
	}

	/**
	 * Gets text of specified widget.
	 *
	 * @param <T> the generic type
	 * @param widget widget to handle
	 * @return text of specified widget
	 */
	public <T extends Widget> String getText(final T widget) {
		Object o = ObjectUtil.invokeMethod(widget, "getText");

		if (o == null){
			return null;
		}

		if (o instanceof String) {
			return (String) o;
		}

		throw new CoreLayerException(
				"Return value of method getText() on class " + o.getClass()
						+ " should be String, but was " + o.getClass());
	}

	/**
	 * Gets label of specified widget.
	 *
	 * @param <T> the generic type
	 * @param w widget to handle
	 * @return label of specified widget
	 */
	public <T extends Widget> String getLabel(final T w) {
		String label = Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				Control parent = ((Control) w).getParent();
				java.util.List<Widget> children = WidgetResolver.getInstance()
						.getChildren(parent);
				// check whether a label is defined using form data layout
				for (Widget child : children) {
					if (child instanceof Label || child instanceof CLabel) {
						Object layoutData = ((Control) child).getLayoutData();
						if (layoutData instanceof FormData) {
							FormData formData = (FormData) layoutData;
							if (formData.right != null && w.equals(formData.right.control)) {
								if (child instanceof Label) {
									return ((Label) child).getText();
								} else if (child instanceof CLabel) {
									return ((CLabel) child).getText();
								}
							}
						}
					}
				}
				List<Control> allWidgets = WidgetLookup.getInstance().findAllParentWidgets();
				int widgetIndex = allWidgets.indexOf(w);
				if (widgetIndex < 0) {
					return null;
				}
				ListIterator<? extends Widget> listIterator = allWidgets.listIterator(widgetIndex);
				while (listIterator.hasPrevious()) {
					Widget previousWidget = listIterator.previous();
					if (previousWidget instanceof Label) {
						Label label = (Label) previousWidget;
						if (label.getImage() == null) {
							return label.getText();
						}
					}
					if (previousWidget instanceof CLabel) {
						CLabel cLabel = (CLabel) previousWidget;
						if (cLabel.getImage() == null) {
							return cLabel.getText();
						}
					}
				}
				return null;
			}
		});
		if (label != null) {
			label = label.replaceAll("&", "").split("\t")[0];
		}
		return label;
	}

	/**
	 * Gets tool tip text of specified widget.
	 *
	 * @param <T> the generic type
	 * @param widget widget to handle
	 * @return tool tip text of specified widget
	 */
	public <T extends Widget> String getToolTipText(final T widget) {
		Object o = null;
		try {
			o = ObjectUtil.invokeMethod(widget, "getToolTipText");
		} catch (RuntimeException e) {
			throw new CoreLayerException(
					"Runtime error during retrieving widget's text", e);
		}
		if (o == null) {
			return null;
		}

		if (o instanceof String) {
			return (String) o;
		}

		throw new CoreLayerException(
				"Return value of method getText() on class " + o.getClass()
						+ " should be String, but was " + o.getClass());
	}

	/**
	 * Sets focus to specified widget. The method is called from {@link WidgetLookup}
	 * so it need to be common for all widgets and cannot be decomposed to
	 * separate handlers.
	 *
	 * @param <T> the generic type
	 * @param w widget to handle
	 */
	public <T extends Widget> void setFocus(final T w) {

		if (w instanceof CTabItem) {
			CTabItemHandler.getInstance().setFocus((CTabItem) w);
		} else if (w instanceof CTabFolder) {
			CTabFolderHandler.getInstance().setFocus((CTabFolder) w);
		} else if (w instanceof TabItem) {
			TabItemHandler.getInstance().setFocus((TabItem) w);
		} else if (w instanceof TableItem) {
			TableItemHandler.getInstance().setFocus((TableItem) w);
		} else if (w instanceof TabFolder) {
			TabFolderHandler.getInstance().setFocus((TabFolder) w);
		} else if (w instanceof Shell) {
			ShellHandler.getInstance().setFocus((Shell) w);
		} else if (w instanceof ToolItem) {
			//ToolItem can't have focus -> set focus to parent ToolBar
			setFocus(ToolItemHandler.getInstance().getParent((ToolItem) w));
		} else {
			Display.syncExec(new Runnable() {
				@Override
				public void run() {
					if (w instanceof Control) {
						((Control) w).setFocus();
					} else {
						throw new CoreLayerException("Unsupported type");
					}
				}
			});
		}
	}
	
	/**
	 * Sends a click (SWT.Selection) notification to specified widget.
	 * 
	 * @param widget widget to handle
	 */
	public void sendClickNotifications(Widget widget) {
		notify(SWT.Selection, widget);
	}

	/**
	 * Notifies specified widget about the event of specified event type. 
	 * See {@link Event}.
	 * 
	 * @param eventType type of the event
	 * @param widget widget to handle
	 */
	public void notify(int eventType, Widget widget) {
		Event event = createEvent(widget);
		notify(eventType, event, widget);
	}

	/**
	 * Notifies specified widget about the event of specified event type with 
	 * specified details and item. See {@link Event}.
	 * 
	 * @param eventType type of the event
	 * @param detail details of the event
	 * @param widget widget to handle
	 * @param widgetItem item of the event
	 */
	public void notifyItem(int eventType, int detail, Widget widget,
			Widget widgetItem) {
		Event event = createEventItem(eventType, detail, widget, widgetItem);
		notify(eventType, event, widget);
	}

	/**
	 * Notifies specified widget about the mouse event of specified event type,
	 * specified position, button and item. See {@link Event}.
	 * 
	 * @param eventType type of the event
	 * @param detail details of the event
	 * @param widget widget to handle
	 * @param widgetItem item of the event
	 * @param x x of the event
	 * @param y y of the event
	 * @param button button of the event
	 */
	public void notifyItemMouse(int eventType, int detail, Widget widget,
			Widget widgetItem, int x, int y, int button) {
		Event event = createMouseItemEvent(eventType, detail, widget,
				widgetItem, x, y, button);
		notify(eventType, event, widget);
	}

	/**
	 * Notifies specified widget about specified event of specified type. See {@link Event}.
	 * 
	 * @param eventType type of specified event
	 * @param createEvent event
	 * @param widget widget to handle
	 */
	public void notify(final int eventType, final Event createEvent,
			final Widget widget) {
		createEvent.type = eventType;

		log.trace("Notify "+widget+" with event "+eventType);
		Display.asyncExec(new Runnable() {
			public void run() {
				if ((widget == null) || widget.isDisposed()) {
					return;
				}

				widget.notifyListeners(eventType, createEvent);
			}
		});

		log.trace("Wait for synchronization");
		// Wait for synchronization
		Display.syncExec(new Runnable() {
			public void run() {
				// do nothing here
			}
		});
	}

	private Event createEvent(Widget widget) {
		Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = widget;
		event.display = Display.getDisplay();
		return event;
	}

	private Event createEventItem(int eventType, int detail, Widget widget,
			Widget widgetItem) {
		Event event = new Event();
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = widgetItem;
		event.widget = widget;
		event.detail = detail;
		event.type = eventType;
		return event;
	}

	private Event createMouseItemEvent(int eventType, int detail,
			Widget widget, Widget widgetItem, int x, int y, int button) {
		Event event = new Event();
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = widgetItem;
		event.widget = widget;
		event.detail = detail;
		event.type = eventType;
		event.button = button;
		event.x = x;
		event.y = y;
		return event;
	}
	
	/**
	 * Gets path to widget within widget tree including widget getting path for
	 * as last element of returned list.
	 *
	 * @param widget widget to get path for
	 * @param classFilter optional array of classes included in returned list
	 * @return ordered list of widgets
	 */
	public List<Widget> getPathToWidget(final Widget widget, final Class<?>... classFilter) {
		final Control firstParent = getParent(widget);
		List<Widget> parents = Display.syncExec(new ResultRunnable<List<Widget>>() {
			@Override
			public List<Widget> run() {
				LinkedList<Widget> result = new LinkedList<Widget>();
				if (WidgetHandler.isClassOf(widget.getClass(), classFilter)){
					result.add(widget);
				}
				Control control = firstParent;
				while (control != null){
					if (WidgetHandler.isClassOf(control.getClass(), classFilter)){
						result.addFirst(control);
					}
					control = control.getParent();
				}
				return result;
			}
		});
		return parents;
	}
	
	/**
	 * Gets parent of specified widget.
	 * 
	 * @param widget widget to find parent
	 * @return parent widget of specified widget
	 */
	public Control getParent(final Widget widget) {
		Object o = ObjectUtil.invokeMethod(widget, "getParent");

		if (o == null){
			return null;
		}

		if (o instanceof Control) {
			return (Control) o;
		}

		throw new CoreLayerException(
				"Return value of method getObject() on class " + o.getClass()
						+ " should be Control, but was " + o.getClass());
	}
	
	/**
	 * Returns control children.
	 *
	 * @param composite the composite
	 * @return the children
	 */
	public Control[] getChildren(final Composite composite) {
		return Display.syncExec(new ResultRunnable<Control[]>() {
			@Override
			public Control[] run() {
				return composite.getChildren();
			}
		});
	}
	
	private static boolean isClassOf(Class<?> clazz,Class<?>[] classes){
		boolean filterPassed = false;
		if (classes != null && classes.length > 0){
			int index = 0;
			while (!filterPassed && index < classes.length){
				if (clazz.getName().equals(classes[index].getName())){
					filterPassed = true;
				}
				index++;
			}
		}
		else{
			filterPassed = true;
		}
		
		return filterPassed;
	}

	/**
	 * Force focus to SWT Widget. Use with caution
	 * @param swtWidget SWT widget
	 */
	public boolean forceFocus(final org.eclipse.swt.widgets.Text swtWidget) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				boolean ret = swtWidget.forceFocus();
				return ret;
			}
		});		
	}
}
