package org.jboss.reddeer.swt.handler;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.lookup.WidgetResolver;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Handler operating basic widgets Create instance via getInstance() method
 * Currently supported:
 * <ul>
 * <li>Text</li>
 * <li>List</li>
 * <li>Combo</li>
 * </ul>
 * 
 * @author Jiri Peterka
 * @author Rastislav Wagner
 * @author Jaroslav Jankovic
 */
public class WidgetHandler {
  
	private final Logger log = Logger.getLogger(this.getClass());
  
	private static WidgetHandler instance;

	private WidgetHandler() {

	}

	/**
	 * Creates and returns widget of WidgetHandler class
	 * 
	 * @return
	 */
	public static WidgetHandler getInstance() {
		if (instance == null) {
			instance = new WidgetHandler();
		}
		return instance;
	}
	
	/**
	 * Click for supported widget type
	 * 
	 * @param w given widgets
	 */
	public <T extends Widget> void click(final T w) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (w instanceof Button) {
					final Button button = (Button) w;
					if (((button.getStyle() & SWT.TOGGLE) != 0) ||
						((button.getStyle() & SWT.CHECK) != 0)) {
						button.setSelection(!button.getSelection());
					}
				}else if (w instanceof ToolItem) {
					final ToolItem toolItem = (ToolItem) w;
					if (((toolItem.getStyle() & SWT.TOGGLE) != 0) ||
						((toolItem.getStyle() & SWT.CHECK) != 0) ||
						((toolItem.getStyle() & SWT.RADIO) != 0)) {
						toolItem.setSelection(!toolItem.getSelection());
					}
				}else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
		WidgetLookup.getInstance().sendClickNotifications(w);
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (w != null && !w.isDisposed()){
					if (w instanceof Button) {
						final Button button = (Button) w;
						handleNotSelectedRadioButton(button);
					}else if (w instanceof ToolItem) {
						// do nothing toolItem is supported type
					}else {
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
						if ((sibling.getStyle() & SWT.RADIO) != 0 && 
								sibling.getSelection()) {
							WidgetLookup.getInstance().notify(SWT.Deactivate, sibling);
							sibling.setSelection(false);
						}
					}	
				}
			}
			
			private void selectRadio(Button button) {
				WidgetLookup.getInstance().notify(SWT.Activate, button);
				WidgetLookup.getInstance().notify(SWT.MouseDown, button);
				WidgetLookup.getInstance().notify(SWT.MouseUp, button);
				button.setSelection(true);
				WidgetLookup.getInstance().notify(SWT.Selection, button);
			}

		});
	}
	
	/**
	 * Gets style for supported widget type
	 * 
	 * @param w given widget
	 * @return	returns widget style
	 */
	public <T extends Widget> int getStyle(final T w) {
		int style = Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				if (w instanceof Widget)
					return ((Widget) w).getStyle();
				else
					throw new SWTLayerException("Unsupported type");
			}
		});
		return style;
	}
	
	/**
	 * Checks if supported widget is selected
	 * 
	 * @param w	given widget
	 * @return	returns widget label text
	 */
	public <T extends Widget> boolean isSelected(final T w) {
		boolean selectionState = Display.syncExec(new ResultRunnable<Boolean>() {
			
			@Override
			public Boolean run() {
				if (w instanceof Button)
					return ((Button) w).getSelection();
				else if (w instanceof TableItem){
					for(TableItem i: ((TableItem) w).getParent().getSelection()){
						if(i.equals(w)){
							return true;
						}
					}
					return false;
				}else if (w instanceof ToolItem)
					return ((ToolItem) w).getSelection(); 
				else
					throw new SWTLayerException("Unsupported type");
			}
		});
		return selectionState;
	}

	/**
	 * Set text for supported widget type
	 * 
	 * @param w
	 *            given widgets
	 * @param text
	 *            text to be set
	 */
	public <T extends Widget> void setText(final T w, final String text) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof Text)
					((Text) w).setText(text);
				else if (w instanceof StyledText)
					((StyledText) w).setText(text);
				else if (w instanceof Combo)
          ((Combo) w).setText(text);
        else
					throw new SWTLayerException("Unsupported type");

			}
		});
	}
	/**
	 * Gets text on given cell index for supported widget type 
	 * 
	 * @param w given widget
	 * @Param cellIndex index of cell
	 * @return returns widget text
	 */
	public <T> String getText(final T w, final int cellIndex) {
		String text = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				if(w instanceof TableItem){
					return ((TableItem)w).getText(cellIndex);
				}else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
		return text;
	}
	
	/**
	 * Gets text for supported widget type
	 * 
	 * @param w
	 *            given widget
	 * @return returns widget text
	 */
	public <T extends Widget> String getText(final T w) {
		String text = Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				if (!w.isDisposed()){
					if (w instanceof Text)
						return ((Text) w).getText();
					else if (w instanceof Label)
						return ((Label) w).getText();
					else if (w instanceof Hyperlink)
						return ((Hyperlink) w).getText();
					else if (w instanceof Section)
						return ((Section) w).getText();
					else if (w instanceof StyledText)
						return ((StyledText) w).getText();
					else if (w instanceof Group)
						return ((Group) w).getText();
					else if (w instanceof Combo)
						return ((Combo) w).getText();
					else if (w instanceof Button)
						return ((Button) w).getText();
					else if (w instanceof CTabItem)
						return ((CTabItem) w).getText();
					else if (w instanceof Shell)
						return ((Shell) w).getText();
					else if (w instanceof TableItem)
						return ((TableItem) w).getText();
					else if (w instanceof ExpandItem)
						return ((ExpandItem) w).getText();
					else if (w instanceof Link){
						String[] split1 = ((Link) w).getText().split(".*<[aA]>");
						String[] split2 = split1[split1.length-1].split("</[aA]>.*");
						return split2[0];
					}
					else
						throw new SWTLayerException("Unsupported type");
				}
				else{
					return null;
				}
			}
		});
		return text;
	}

	/**
	 * Checks item for supported widget type
	 * 
	 * @param w given widget
	 * @param itemIndex item index to check
	 */
	public <T> void check(final T w,final int itemIndex) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof Table){
					Table widget = (Table)w;
					if(itemIndex == -1){
						throw new SWTLayerException("Unable to check item with index "+itemIndex+" because it does not exist");
					}
					if((widget.getStyle() & SWT.CHECK) != SWT.CHECK){
						throw new SWTLayerException("Unable to check item because table does not have SWT.CHECK style");
						
					} 
					widget.getItem(itemIndex).setChecked(true);
					WidgetLookup.getInstance().notifyItem(SWT.Selection, SWT.CHECK, widget, widget.getItem(itemIndex));
				}
				else{
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}
	
	/**
	 * Gets items for supported widget type
	 * 
	 * @param w
	 *            given widget
	 * @return array of items in widget
	 */
	public <T extends Widget> String[] getItems(final T w) {
		String[] text = Display.syncExec(new ResultRunnable<String[]>() {

			@Override
			public String[] run() {
				if (w instanceof List)
					return ((List) w).getItems();
				else if (w instanceof Combo)
          return ((Combo) w).getItems();
				else
					throw new SWTLayerException("Unsupported type");
			}
		});
		return text;
	}
	
	/**
	 * Gets swt items for supported widget type
	 * 
	 * @param w
	 *            given widget
	 * @return array of items in widget
	 */
	public <T> Object[] getSWTItems(final T w) {
		return Display.syncExec(new ResultRunnable<Object[]>() {

			@Override
			public Object[] run() {
				if (w instanceof Table)
					return ((Table) w).getItems();
				else
					throw new SWTLayerException("Unsupported type");
			}
		});
	}
	
	/**
	 * Gets swt items for supported widget type
	 * 
	 * @param w
	 *            given widget
	 * @return array of items in widget
	 */
	public <T> Object getSWTItem(final T w, final int index) {
		return Display.syncExec(new ResultRunnable<Object>() {

			@Override
			public Object run() {
				if (w instanceof Table)
					return ((Table) w).getItem(index);
				else
					throw new SWTLayerException("Unsupported type");
			}
		});
	}
	
	/**
	 * Deselects all items for supported widget type
	 * 
	 * @param w given widget
	 */
	public <T extends Widget> void deselect(final T w) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List)
					((List) w).deselectAll();
				else
					throw new SWTLayerException("Unsupported type");
			}
		});
	}
	
	/**
	 * Selects all items for supported widget type
	 * 
	 * @param w given widget
	 */
	public <T extends Widget> void selectAll(final T w) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List){
					List widget = (List)w;
					if((widget.getStyle() & SWT.MULTI) !=0){
						((List) w).selectAll();
						WidgetLookup.getInstance().notify(SWT.Selection, (List)w);
					} else {
						throw new SWTLayerException("List does not support multi selection - it does not have SWT MULTI style");
					}
				} else if (w instanceof Table){
					Table widget = (Table)w;
					if((widget.getStyle() & SWT.MULTI) !=0){
						((Table) w).selectAll();
						WidgetLookup.getInstance().notify(SWT.Selection, (Table)w);
					} else {
						throw new SWTLayerException("Table does not support multi selection - it does not have SWT MULTI style");
					}
				}
				else
					throw new SWTLayerException("Unsupported type");
			}
		});
	}
	
	/**
	 * Selects item for supported widget type
	 * 
	 * @param w given widget
	 * @param item to select
	 */
	public <T extends Widget> void select(final T w,final String item) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List){
					List widget = (List)w;
					int index = (widget.indexOf(item))	;
					if(index == -1){
						throw new SWTLayerException("Unable to select item "+item+" because it does not exist");
					}
					widget.select(widget.indexOf(item));
				}else if (w instanceof Combo){
					Combo widget = (Combo)w;
					int index = (widget.indexOf(item))  ;
					if(index == -1){
						throw new SWTLayerException("Unable to select item "+item+" because it does not exist");
					}
					widget.select(widget.indexOf(item));
				}
				else{
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}
	
	/**
	 * Selects item for supported widget type
	 * 
	 * @param w given widget
	 * @param item to select
	 */
	public <T> void select(final T wItem) {
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				if(wItem instanceof TableItem){
					TableItem swtTableItem = (TableItem)wItem;
					swtTableItem.getParent().setFocus();
					swtTableItem.getParent().setSelection(swtTableItem);
					WidgetLookup.getInstance().notifyItem(SWT.Selection, SWT.NONE, swtTableItem.getParent(), swtTableItem);
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}
	
	/**
	 * Checks item for supported widget type
	 * 
	 * @param w given widget
	 * @param item to check
	 */
	public <T> void setChecked(final T wItem, final boolean check) {
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				if(wItem instanceof TableItem){
					TableItem swtTableItem = (TableItem)wItem;
					if((swtTableItem.getParent().getStyle() & SWT.CHECK) != SWT.CHECK){
						throw new SWTLayerException("Unable to check table item "+swtTableItem.getText()+" because table does not have SWT.CHECK style");
					}
					swtTableItem.getParent().setFocus();
					swtTableItem.setChecked(check);
					WidgetLookup.getInstance().notifyItem(SWT.Selection, SWT.CHECK, swtTableItem.getParent(), swtTableItem);
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}
	
	/**
	 * Checks if widget is checked
	 * 
	 * @param w given widget
	 */
	public <T> boolean isChecked(final T w) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			
			@Override
			public Boolean run() {
				if(w instanceof TableItem){
					TableItem swtTableItem = (TableItem)w;
					return swtTableItem.getChecked();
				} else {
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}
	
	/**
	 * Selects items for supported widget type if widget supports multi selection
	 * 
	 * @param w given widget
	 * @param items to select
	 */
	public <T extends Widget> void select(final T w,final String[] items) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List){
					List widget = (List)w;
					if((widget.getStyle() & SWT.MULTI) !=0){
						for(String item:items){
							int index = (widget.indexOf(item))	;
							if(index == -1){
								throw new SWTLayerException("Unable to select item "+item+" because it does not exist");
							}
							widget.select(widget.indexOf(item));
							WidgetLookup.getInstance().notify(SWT.Selection, widget);
						}
					} else {
						throw new SWTLayerException("List does not support multi selection - it does not have SWT MULTI style");
					}
				} else{
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}
	
	/**
	 * Selects items for supported widget type if widget supports multi selection
	 * 
	 * @param w given widget
	 * @param indices of items to select
	 */
	public <T extends Widget> void select(final T w,final int[] indices) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List){
					List widget = (List)w;
					if((widget.getStyle() & SWT.MULTI) !=0){
						widget.select(indices);
						WidgetLookup.getInstance().notify(SWT.Selection, widget);
					} else {
						throw new SWTLayerException("List does not support multi selection - it does not have SWT MULTI style");
					}
				} else if (w instanceof Table){
					Table widget = (Table)w;
					if((widget.getStyle() & SWT.MULTI) !=0){
						widget.select(indices);
						WidgetLookup.getInstance().notify(SWT.Selection, widget);
					} else {
						throw new SWTLayerException("Table does not support multi selection - it does not have SWT MULTI style");
					}
				}
				else{
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}
	
	/**
	 * Selects item for supported widget type
	 * 
	 * @param w given widget
	 * @param index of item to select
	 */
	public <T extends Widget> void select(final T w,final int index) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List){
					List widget = (List)w;
					if(widget.getItemCount()-1 < index){
						throw new SWTLayerException("Unable to select item with index "+index+" because it does not exist");
					}
					widget.select(index);
					WidgetLookup.getInstance().notify(SWT.Selection, widget);
				} else if (w instanceof Combo){
					Combo widget = (Combo)w;
					if(widget.getItemCount()-1 < index){
						throw new SWTLayerException("Unable to select item with index "+index+" because it does not exist");
					}
					widget.select(index);
					WidgetLookup.getInstance().notify(SWT.Selection, widget);
				} else if (w instanceof Table){
					Table widget = (Table)w;
					if(widget.getItemCount()-1 < index){
						throw new SWTLayerException("Unable to select item with index "+index+" because it does not exist");
					}
					widget.select(index);
					WidgetLookup.getInstance().notify(SWT.Selection, widget);
				}
				else{
					throw new SWTLayerException("Unsupported type");
				}
			}
		});
	}
	
	/**
	 * Gets label for supported widget type
	 * 
	 * @param w given widget
	 * @return returns widget label text
	 */
	public <T extends Widget> String getLabel(final T w) {
		String label = Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				if ((w instanceof List) || (w instanceof Text) || (w instanceof Combo)) {
					Widget parent = ((Control)w).getParent();;
					java.util.List<Widget> children = WidgetResolver.getInstance().getChildren(parent);
					for (int i = 1; i < children.size() ; i++) {						
						if (children.get(i) != null && children.get(i).equals(w)) {
							for(int y=1; i-y>=0 ;y++){
								if(children.get(i - y) instanceof Label){
									if(((Label)children.get(i - y)).getImage() == null){
										return ((Label)children.get(i - y)).getText();
									}
								} else {
									return null;
								}
							}
						}
					}
				}
				else {
					throw new SWTLayerException("Unsupported type");
				}
				return null;
			}}
		);
		if(label != null) {
			label = label.replaceAll("&", "").split("\t")[0];
		}
		return label;
	}

	/**
	 * Gets tooltip if supported widget type
	 * 
	 * @param widget
	 * @return widget text
	 */
	public <T extends Widget> String getToolTipText(final T w) {
		String text = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				if (w instanceof Text)
					return ((Text) w).getToolTipText();
				else if (w instanceof Combo)
					return ((Combo) w).getToolTipText();
				else if (w instanceof CTabItem)
					return ((CTabItem) w).getToolTipText();
				else if (w instanceof Button){
					return ((Button) w).getToolTipText();
				}else if (w instanceof ExpandItem)
					return ((ExpandItem) w).getParent().getToolTipText();
				else
					throw new SWTLayerException("Unsupported type");
			}
		});
		return text;
	}
	
	public <T extends Widget> void setFocus(final T w) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (w instanceof Control) {
					((Control)w).setFocus();
				} else if (w instanceof ExpandBar){
					((ExpandBar)w).setFocus();
				}
				else throw new SWTLayerException("Unsupported type");
			}
		});
	}
	
	/**
	 * Activates widget - link/hyperlink etc
	 * @param w widget to activate
	 */
	public <T> void activate(final T w) {
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				if (w instanceof Link) {
					((Link)w).setFocus();
					WidgetLookup.getInstance().notify(SWT.MouseDown, (Link)w);
					WidgetLookup.getInstance().notify(SWT.Selection, (Link)w);
					WidgetLookup.getInstance().notify(SWT.MouseUp, (Link)w);
				} else if (w instanceof Hyperlink) {
					((Hyperlink)w).setFocus();
					WidgetLookup.getInstance().notifyHyperlink(SWT.MouseEnter, (Hyperlink)w);
					WidgetLookup.getInstance().notifyHyperlink(SWT.MouseDown, (Hyperlink)w);
					WidgetLookup.getInstance().notifyHyperlink(SWT.MouseUp, (Hyperlink)w);
				}
				else throw new SWTLayerException("Unsupported type");
			}
		});
	}
	 /**
	  * Sets selection with given index for supported widget type
	  * 
	  * @param index
	  */
	 public <T extends Widget> void setSelection(final T w, final int index) {
	   Display.syncExec(new Runnable() {
	    
	    @Override
	    public void run() {
	      if (w instanceof Combo) {
	        int itemsLength = getItems(w).length;
	        if (index >= itemsLength) {
	          log.error("Combo does not have " + index + 1 + "items!");
	          log.info("Combo has " + itemsLength + " items");
	          throw new SWTLayerException("Nonexisted item in combo was requested");
	        } else {
	          ((Combo)w).select(index);
	        }
	      }
	      else 
	        throw new SWTLayerException("Unsupported type");
	    }
	  });
	}
	
	/**
	 * Sets selection with given text for supported widget type
	 * 
	 * @param index
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
	          log.info("Items present in combo:");
	          int i = 0;
	          for (String item : items) {
	            log.info("    " + item + "(index " + i);
	            i++;
	          }
	          throw new SWTLayerException("Nonexisting item in combo was requested");
	        }else {
	          ((Combo)w).select(index);
	        }
	      }
	    }
	  });
	}
	
	/**
	 * Gets selection text for supported widget type
	 * 
	 * @param index
	 */
	public <T extends Widget> String getSelection(final T w) {
	  return Display.syncExec(new ResultRunnable<String>() {
	    
	    @Override
	    public String run() {
	      if (w instanceof Combo) {
	    	Combo combo = (Combo)w;
	    	Point selection = combo.getSelection();
	    	String comboText = combo.getText();
	    	String selectionText = "";
	    	if (selection.y > selection.x){
	    		selectionText = comboText.substring(selection.x , selection.y);
	    	}
	        return selectionText;
	      }
	      else 
	        throw new SWTLayerException("Unsupported type");
	    }
	  });
	}
	 
	/**
	 * Gets selection index for supported widget type
	 * 
	 * @param index
	 */
	public <T extends Widget> int getSelectionIndex(final T w) {
	  return Display.syncExec(new ResultRunnable<Integer>() {
	    
	    @Override
	    public Integer run() {
	      if (w instanceof Combo) {
	        return ((Combo)w).getSelectionIndex();
	      }
	      else 
	        throw new SWTLayerException("Unsupported type");
	    }
	  });
	}
	/**
	 * Checks if supported widget is expanded
	 * 
	 * @param w	given widget
	 * @return	true if widget is expanded
	 */
	public <T extends Widget> boolean isExpanded(final T w) {
		boolean selectionState = Display.syncExec(new ResultRunnable<Boolean>() {
			
			@Override
			public Boolean run() {
				if (w instanceof ExpandItem)
					return ((ExpandItem) w).getExpanded();
				else if (w instanceof TreeItem)
					return ((TreeItem) w).getExpanded();
				else
					throw new SWTLayerException("Unsupported type");
			}
		});
		return selectionState;
	}
	/**
	 * Returns parent for supported widget
	 * 
	 * @param w	given widget
	 * @return	parent widget
	 */
	@SuppressWarnings("unchecked")
	public <T extends Widget,R extends Widget> R getParent(final T w) {
		R parent = Display.syncExec(new ResultRunnable<R>() {
			@Override
			public R run() {
				Widget parent = null;
				if (w instanceof ExpandItem)
					parent = ((ExpandItem) w).getParent();
				else if (w instanceof TreeItem)
					parent = ((TreeItem) w).getParent();
				else if (w instanceof TableItem)
					parent = ((TableItem) w).getParent();
				else
					throw new SWTLayerException("Unsupported type");
				return (R)parent;
			}
		});
		return parent;
	}
}
