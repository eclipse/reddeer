package org.jboss.reddeer.swt.handler;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.lookup.WidgetResolver;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ObjectUtil;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Handler operating basic widgets.
 * 
 * @author Jiri Peterka
 * @author Rastislav Wagner
 * @author Jaroslav Jankovic
 */
public class WidgetHandler {

	private static final Logger log = Logger.getLogger(WidgetHandler.class);

	private static WidgetHandler instance;

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
			throw new SWTLayerException(
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
	 * Clicks specific widget.
	 * 
	 * @deprecated Use click method on the specific handlers.
	 * Will be removed in version 0.6.
	 * @param w widget to handle
	 */
	public <T extends Widget> void click(final T w) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (w instanceof Button) {
					final Button button = (Button) w;
					if (((button.getStyle() & SWT.TOGGLE) != 0)
							|| ((button.getStyle() & SWT.CHECK) != 0)) {
						button.setSelection(!button.getSelection());
					}
				} else if (w instanceof ToolItem) {
					final ToolItem toolItem = (ToolItem) w;
					if (((toolItem.getStyle() & SWT.TOGGLE) != 0)
							|| ((toolItem.getStyle() & SWT.CHECK) != 0)
							|| ((toolItem.getStyle() & SWT.RADIO) != 0)) {
						toolItem.setSelection(!toolItem.getSelection());
					}
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
		WidgetHandler.getInstance().sendClickNotifications(w);
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (w != null && !w.isDisposed()) {
					if (w instanceof Button) {
						final Button button = (Button) w;
						handleNotSelectedRadioButton(button);
					} else if (w instanceof ToolItem) {
						// do nothing toolItem is supported type
					} else {
						throw new SWTLayerException("Unsupported type");
					}
				}
			}

			private void handleNotSelectedRadioButton(final Button button) {
				if ((button.getStyle() & SWT.RADIO) == 0
						|| button.getSelection()) {
					return;
				}

				deselectSelectedSiblingRadio(button);
				selectRadio(button);
			}

			private void deselectSelectedSiblingRadio(final Button button) {
				if ((button.getParent().getStyle() & SWT.NO_RADIO_GROUP) != 0) {
					return;
				}

				Widget[] siblings = button.getParent().getChildren();
				for (Widget widget : siblings) {
					if (widget instanceof Button) {
						Button sibling = (Button) widget;
						if ((sibling.getStyle() & SWT.RADIO) != 0
								&& sibling.getSelection()) {
							WidgetHandler.getInstance().notify(SWT.Deactivate,
									sibling);
							sibling.setSelection(false);
						}
					}
				}
			}

			private void selectRadio(Button button) {
				WidgetHandler.getInstance().notify(SWT.Activate, button);
				WidgetHandler.getInstance().notify(SWT.MouseDown, button);
				WidgetHandler.getInstance().notify(SWT.MouseUp, button);
				button.setSelection(true);
				WidgetHandler.getInstance().notify(SWT.Selection, button);
			}

		});
	}

	/**
	 * Gets style of specified widget.
	 * 
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
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
		return style;
	}

	/**
	 * Finds out whether specified widget is selected or not.
	 * 
	 * @deprecated Use isSelected method on the specific widget.
	 * Will be removed in version 0.6.
	 * @param w widget to handle
	 * @return true if specified widget is selected, false otherwise
	 */
	public <T extends Widget> boolean isSelected(final T w) {
		boolean selectionState = Display
				.syncExec(new ResultRunnable<Boolean>() {

					@Override
					public Boolean run() {
						if (w instanceof Button) {
							return ((Button) w).getSelection();
						} else if (w instanceof TableItem) {
							for (TableItem i : ((TableItem) w).getParent()
									.getSelection()) {
								if (i.equals(w)) {
									return true;
								}
							}
							return false;
						} else if (w instanceof ToolItem) {
							return ((ToolItem) w).getSelection();
						} else {
							throw new SWTLayerException("Unsupported type");
						}
					}
				});
		return selectionState;
	}

	/**
	 * Sets specified text to specified widget.
	 * 
	 * @param widget widget to handle
	 * @param text text to set
	 */
	public <T extends Widget> void setText(final T widget, final String text) {
		try {
			ObjectUtil.invokeMethod(widget, "setText",
					new Class[] { String.class }, new Object[] { text });
		} catch (RuntimeException e) {
			throw new SWTLayerException(
					"Runtime error during setting widget's text", e);
		}
	}

	/**
	 * Gets text from the cell on the position specified by index in specified widget.
	 * 
	 * @deprecated Use getText method on the specific widget.
	 * Will be removed in version 0.6.
	 * @param w widget to handle
	 * @param cellIndex index of cell
	 * @return text from the cell on the position specified by the widget
	 */
	public <T> String getText(final T w, final int cellIndex) {
		String text = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				if (w instanceof TableItem) {
					return ((TableItem) w).getText(cellIndex);
				} else if (w instanceof TreeItem) {
					return ((TreeItem) w).getText(cellIndex);
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
		return text;
	}

	/**
	 * Gets text of specified widget.
	 * 
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

		throw new SWTLayerException(
				"Return value of method getText() on class " + o.getClass()
						+ " should be String, but was " + o.getClass());
	}

	/**
	 * Checks the item on the position specified by index in specified widget.
	 * 
	 * @deprecated Use check method on the specific widget.
	 * Will be removed in 0.6.
	 * @param w widget to handle
	 * @param itemIndex index of item to check
	 */
	public <T> void check(final T w, final int itemIndex) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof Table) {
					Table widget = (Table) w;
					if (itemIndex == -1) {
						throw new SWTLayerException(
								"Unable to check item with index " + itemIndex
										+ " because it does not exist");
					}
					if ((widget.getStyle() & SWT.CHECK) != SWT.CHECK) {
						throw new SWTLayerException(
								"Unable to check item because table does not have SWT.CHECK style");

					}
					widget.getItem(itemIndex).setChecked(true);
					WidgetHandler.getInstance().notifyItem(SWT.Selection,
							SWT.CHECK, widget, widget.getItem(itemIndex));
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * Gets items from specified widget.
	 * 
	 * @deprecated Use getItems method on the specific widget.
	 * Will be removed in 0.6.
	 * @param w widget to handle
	 * @return items from specified widget
	 */
	public <T extends Widget> String[] getItems(final T w) {
		String[] text = Display.syncExec(new ResultRunnable<String[]>() {

			@Override
			public String[] run() {
				if (w instanceof List) {
					return ((List) w).getItems();
				} else if (w instanceof Combo) {
					return ((Combo) w).getItems();
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
		return text;
	}

	/**
	 * Gets Eclipse SWT items from specified widget.
	 * 
	 * @deprecated Use getSWTItems method on the specific widget.
	 * Will be removed in 0.6.
	 * @param w widget ot handle
	 * @return Eclipse SWT items from specified widget
	 */
	public <T> Object[] getSWTItems(final T w) {
		return Display.syncExec(new ResultRunnable<Object[]>() {

			@Override
			public Object[] run() {
				if (w instanceof Table) {
					return ((Table) w).getItems();
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * Gets Eclipse SWT item from specified widget on the position 
	 * defined by specified index.
	 * 
	 * @deprecated Use getSWTItem method on the specific widget.
	 * Will be removed in 0.6.
	 * @param w widget to handle
	 * @param index index of the item
	 * @return Eclipse SWT item from specified widget
	 */
	public <T> Object getSWTItem(final T w, final int index) {
		return Display.syncExec(new ResultRunnable<Object>() {

			@Override
			public Object run() {
				if (w instanceof Table) {
					return ((Table) w).getItem(index);
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * Deselects all items on specified widget.
	 * 
	 * @deprecated Use deselectAll method on the specific widget.
	 * Will be removed in 0.6.
	 * @param w widget to handle
	 */
	public <T extends Widget> void deselectAll(final T w) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List) {
					((List) w).deselectAll();
				} else if (w instanceof Table) {
					((Table) w).deselectAll();
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * Selects all items on specified widget.
	 * 
	 * @deprecated Use selectAll method on the specific widget.
	 * Will be removed in 0.6.
	 * @param w widget to handle
	 */
	public <T extends Widget> void selectAll(final T w) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List) {
					List widget = (List) w;
					if ((widget.getStyle() & SWT.MULTI) != 0) {
						((List) w).selectAll();
						WidgetHandler.getInstance().notify(SWT.Selection,
								(List) w);
					} else {
						throw new SWTLayerException(
								"List does not support multi selection - it does not have SWT MULTI style");
					}
				} else if (w instanceof Table) {
					Table widget = (Table) w;
					if ((widget.getStyle() & SWT.MULTI) != 0) {
						((Table) w).selectAll();
						WidgetHandler.getInstance().notify(SWT.Selection,
								(Table) w);
					} else {
						throw new SWTLayerException(
								"Table does not support multi selection - it does not have SWT MULTI style");
					}
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * Selects specified item on specified widget.
	 * 
	 * @deprecated Use select method on the specific widget.
	 * Will be removed in 0.6.
	 * @param w widget ot handle
	 * @param item item to select
	 */
	public <T extends Widget> void select(final T w, final String item) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List) {
					List widget = (List) w;
					int index = (widget.indexOf(item));
					if (index == -1) {
						throw new SWTLayerException("Unable to select item "
								+ item + " because it does not exist");
					}
					widget.select(widget.indexOf(item));
					WidgetHandler.getInstance().sendClickNotifications(w);
				} else if (w instanceof Combo) {
					Combo widget = (Combo) w;
					int index = (widget.indexOf(item));
					if (index == -1) {
						throw new SWTLayerException("Unable to select item "
								+ item + " because it does not exist");
					}
					widget.select(widget.indexOf(item));
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.
	 * @param wItem item to select
	 */
	public <T> void select(final T wItem) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (wItem instanceof TableItem) {
					TableItem swtTableItem = (TableItem) wItem;
					swtTableItem.getParent().setFocus();
					swtTableItem.getParent().setSelection(swtTableItem);
					WidgetHandler.getInstance().notifyItem(SWT.Selection,
							SWT.NONE, swtTableItem.getParent(), swtTableItem);
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.
	 * @param wItem item to set checked
	 * @param check check specified item or not
	 */
	public <T> void setChecked(final T wItem, final boolean check) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (wItem instanceof TableItem) {
					TableItem swtTableItem = (TableItem) wItem;
					if ((swtTableItem.getParent().getStyle() & SWT.CHECK) != SWT.CHECK) {
						throw new SWTLayerException(
								"Unable to check table item "
										+ swtTableItem.getText()
										+ " because table does not have SWT.CHECK style");
					}
					swtTableItem.getParent().setFocus();
					swtTableItem.setChecked(check);
					WidgetHandler.getInstance().notifyItem(SWT.Selection,
							SWT.CHECK, swtTableItem.getParent(), swtTableItem);
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.
	 * @param w widget to handle
	 */
	public <T> boolean isChecked(final T w) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				if (w instanceof TableItem) {
					TableItem swtTableItem = (TableItem) w;
					return swtTableItem.getChecked();
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * 
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.
	 * @param w widget to handle
	 * @param items items to select
	 */
	public <T extends Widget> void select(final T w, final String[] items) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List) {
					List widget = (List) w;
					if ((widget.getStyle() & SWT.MULTI) != 0) {
						for (String item : items) {
							int index = (widget.indexOf(item));
							if (index == -1) {
								throw new SWTLayerException(
										"Unable to select item " + item
												+ " because it does not exist");
							}
							widget.select(widget.indexOf(item));
							WidgetHandler.getInstance().notify(SWT.Selection,
									widget);
						}
					} else {
						throw new SWTLayerException(
								"List does not support multi selection - it does not have SWT MULTI style");
					}
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.
	 * @param w widget to handle
	 * @param indices indices of items to select
	 */
	public <T extends Widget> void select(final T w, final int[] indices) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List) {
					List widget = (List) w;
					if ((widget.getStyle() & SWT.MULTI) != 0) {
						widget.select(indices);
						WidgetHandler.getInstance().notify(SWT.Selection,
								widget);
					} else {
						throw new SWTLayerException(
								"List does not support multi selection - it does not have SWT MULTI style");
					}
				} else if (w instanceof Table) {
					Table widget = (Table) w;
					if ((widget.getStyle() & SWT.MULTI) != 0) {
						widget.select(indices);
						WidgetHandler.getInstance().notify(SWT.Selection,
								widget);
					} else {
						throw new SWTLayerException(
								"Table does not support multi selection - it does not have SWT MULTI style");
					}
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.
	 * @param w widge to handle	 
	 * @param index index of item to select
	 */
	public <T extends Widget> void select(final T w, final int index) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List) {
					List widget = (List) w;
					if (widget.getItemCount() - 1 < index) {
						throw new SWTLayerException(
								"Unable to select item with index " + index
										+ " because it does not exist");
					}
					widget.select(index);
					WidgetHandler.getInstance().notify(SWT.Selection, widget);
				} else if (w instanceof Combo) {
					Combo widget = (Combo) w;
					if (widget.getItemCount() - 1 < index) {
						throw new SWTLayerException(
								"Unable to select item with index " + index
										+ " because it does not exist");
					}
					widget.select(index);
					WidgetHandler.getInstance().notify(SWT.Selection, widget);
				} else if (w instanceof Table) {
					Table widget = (Table) w;
					if (widget.getItemCount() - 1 < index) {
						throw new SWTLayerException(
								"Unable to select item with index " + index
										+ " because it does not exist");
					}
					widget.select(index);
					WidgetHandler.getInstance().notify(SWT.Selection, widget);
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * Gets label of specified widget.
	 * 
	 * @param w widget ot handle
	 * @return label of specified widget
	 */
	public <T extends Widget> String getLabel(final T w) {
		String label = Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				Widget parent = ((Control) w).getParent();
				java.util.List<Widget> children = WidgetResolver.getInstance()
						.getChildren(parent);
				for (int i = 1; i < children.size(); i++) {
					if (children.get(i) != null && children.get(i).equals(w)) {
						for (int y = 1; i - y >= 0; y++) {
							if (children.get(i - y) instanceof Label) {
								if (((Label) children.get(i - y)).getImage() == null) {
									return ((Label) children.get(i - y))
											.getText();
								}
							} else {
								return null;
							}
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
	 * @param widget widget to handle
	 * @return tool tip text of specified widget
	 */
	public <T extends Widget> String getToolTipText(final T widget) {
		Object o = null;
		try {
			o = ObjectUtil.invokeMethod(widget, "getToolTipText");
		} catch (RuntimeException e) {
			throw new SWTLayerException(
					"Runtime error during retrieving widget's text", e);
		}
		if (o == null) {
			return null;
		}

		if (o instanceof String) {
			return (String) o;
		}

		throw new SWTLayerException(
				"Return value of method getText() on class " + o.getClass()
						+ " should be String, but was " + o.getClass());
	}

	/**
	 * Sets focus to specified widget. The method is called from {@link WidgetLookup}
	 * so it need to be common for all widgets and cannot be decomposed to
	 * separate handlers.
	 * 
	 * @param w widget to handle
	 */
	public <T extends Widget> void setFocus(final T w) {

		if (w instanceof CTabItem) {
			CTabItemHandler.getInstance().setFocus((CTabItem) w);
		} else if (w instanceof CTabFolder) {
			CTabFolderHandler.getInstance().setFocus((CTabFolder) w);
		} else if (w instanceof TabItem) {
			TabItemHandler.getInstance().setFocus((TabItem) w);
		} else if (w instanceof TabFolder) {
			TabFolderHandler.getInstance().setFocus((TabFolder) w);
		} else if (w instanceof Shell) {
			ShellHandler.getInstance().setFocus((Shell) w);
		} else {
			Display.syncExec(new Runnable() {
				@Override
				public void run() {
					if (w instanceof Control) {
						((Control) w).setFocus();
					} else {
						throw new SWTLayerException("Unsupported type");
					}
				}
			});
		}
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.	 
	 * @param w widget to handle
	 */
	public <T> void activate(final T w) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof Link) {
					((Link) w).setFocus();
					WidgetHandler.getInstance().notify(SWT.MouseDown, (Link) w);
					WidgetHandler.getInstance().notify(SWT.Selection, (Link) w);
					WidgetHandler.getInstance().notify(SWT.MouseUp, (Link) w);
				} else if (w instanceof Hyperlink) {
					((Hyperlink) w).setFocus();
					WidgetLookup.getInstance().notifyHyperlink(SWT.MouseEnter,
							(Hyperlink) w);
					WidgetLookup.getInstance().notifyHyperlink(SWT.MouseDown,
							(Hyperlink) w);
					WidgetLookup.getInstance().notifyHyperlink(SWT.MouseUp,
							(Hyperlink) w);
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.	 
	 * @param w widget to handle
	 * @param index index of the item to select
	 */
	public <T extends Widget> void setSelection(final T w, final int index) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof Combo) {
					int itemsLength = getItems(w).length;
					if (index >= itemsLength) {
						log.error("Combo does not have " + index + 1 + "items!");
						log.debug("Combo has " + itemsLength + " items");
						throw new SWTLayerException(
								"Nonexisted item in combo was requested");
					} else {
						((Combo) w).select(index);
					}
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.
	 * @param w widget to handle
	 * @param text text to set in the selection
	 */
	public <T extends Widget> void setSelection(final T w, final String text) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof Combo) {
					String[] items = getItems(w);
					int index = Arrays.asList(items).indexOf(text);
					if (index == -1) {
						log.error("'" + text + "' is not "
								+ "contained in combo items");
						log.debug("Items present in combo:");
						int i = 0;
						for (String item : items) {
							log.info("    " + item + "(index " + i);
							i++;
						}
						throw new SWTLayerException(
								"Nonexisting item in combo was requested");
					} else {
						((Combo) w).select(index);
					}
				}
			}
		});
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.
	 * @param w widget to handle
	 */
	public <T extends Widget> String getSelection(final T w) {
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				if (w instanceof Combo) {
					Combo combo = (Combo) w;
					Point selection = combo.getSelection();
					String comboText = combo.getText();
					String selectionText = "";
					if (selection.y > selection.x) {
						selectionText = comboText.substring(selection.x,
								selection.y);
					}
					return selectionText;
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.
	 * @param w widget to handle
	 */
	public <T extends Widget> int getSelectionIndex(final T w) {
		return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				if (w instanceof Combo) {
					return ((Combo) w).getSelectionIndex();
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.
	 * @param w widget to handle
	 * @return true if widget is expanded, false otherwise
	 */
	public <T extends Widget> boolean isExpanded(final T w) {
		boolean selectionState = Display
				.syncExec(new ResultRunnable<Boolean>() {

					@Override
					public Boolean run() {
						if (w instanceof ExpandItem) {
							return ((ExpandItem) w).getExpanded();
						} else if (w instanceof TreeItem) {
							return ((TreeItem) w).getExpanded();
						} else {
							throw new SWTLayerException("Unsupported type");
						}
					}
				});
		return selectionState;
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.
	 * @param w widget to handle
	 * @return parent of specified widget
	 */
	@SuppressWarnings("unchecked")
	public <T extends Widget, R extends Widget> R getParent(final T w) {
		R parent = Display.syncExec(new ResultRunnable<R>() {
			@Override
			public R run() {
				Widget parent = null;
				if (w instanceof ExpandItem) {
					parent = ((ExpandItem) w).getParent();
				} else if (w instanceof TreeItem) {
					parent = ((TreeItem) w).getParent();
				} else if (w instanceof TableItem) {
					parent = ((TableItem) w).getParent();
				} else {
					throw new SWTLayerException("Unsupported type");
				}
				return (R) parent;
			}
		});
		return parent;
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.
	 * @param w widge to handle
	 * @return current value of specified widget
	 */
	public <T extends Widget> int getValue(final T w) {
		return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				if (w instanceof Spinner) {
					return ((Spinner) w).getSelection();
				}
				else
					throw new SWTLayerException("Unsupported type");
			}
		});
	}

	/**
	 * @deprecated Use concrete handler instead. Will be removed in 0.6.
	 * @param w widget to handle
	 * @param value value to set
	 */
	public <T extends Widget> void setValue(final T w, final int value) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof Spinner) {
					((Spinner) w).setSelection(value);
				}
				else
					throw new SWTLayerException("Unsupported type");
			}
		});
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

		Display.asyncExec(new Runnable() {
			public void run() {
				if ((widget == null) || widget.isDisposed()) {
					return;
				}

				widget.notifyListeners(eventType, createEvent);
			}
		});

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
}
