package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Handler operating basic widgets Create instance via getInstance() method
 * Currently supported:
 * <ul>
 * <li>Text</li>
 * <li>List</li>
 * </ul>
 * 
 * @author Jiri Peterka
 * @author Rastislav Wagner
 * @author Jaroslav Jankovic
 */
public class WidgetHandler {

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
	 * Set text for supported widget type
	 * 
	 * @param w
	 *            given widgets
	 * @param text
	 *            text to be set
	 */
	public <T> void setText(final T w, final String text) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof Text)
					((Text) w).setText(text);
				else
					throw new SWTLayerException("Unuspported type");

			}
		});
	}

	/**
	 * Gets text for supported widget type
	 * 
	 * @param w
	 *            given widget
	 * @return returns widget text
	 */
	public <T> String getText(final T w) {
		String text = Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				if (w instanceof Text)
					return ((Text) w).getText();
				else
					throw new SWTLayerException("Unuspported type");
			}
		});
		return text;
	}

	/**
	 * Gets items for supported widget type
	 * 
	 * @param w
	 *            given widget
	 * @return array of items in widget
	 */
	public <T> String[] getItems(final T w) {
		String[] text = Display.syncExec(new ResultRunnable<String[]>() {

			@Override
			public String[] run() {
				if (w instanceof List)
					return ((List) w).getItems();
				else
					throw new SWTLayerException("Unuspported type");
			}
		});
		return text;
	}
	
	/**
	 * Deselects all items for supported widget type
	 * 
	 * @param w given widget
	 */
	public <T> void deselect(final T w) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List)
					((List) w).deselectAll();
				else
					throw new SWTLayerException("Unuspported type");
			}
		});
	}
	
	/**
	 * Selects all items for supported widget type
	 * 
	 * @param w given widget
	 */
	public <T> void selectAll(final T w) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List){
					List widget = (List)w;
					if((widget.getStyle() & SWT.MULTI) !=0){
						((List) w).selectAll();
					} else {
						throw new SWTLayerException("List does not support multi selection - it does not have SWT MULTI style");
					}
				}
				else
					throw new SWTLayerException("Unuspported type");
			}
		});
	}
	
	/**
	 * Selects item for supported widget type
	 * 
	 * @param w given widget
	 * @param item to select
	 */
	public <T> void select(final T w,final String item) {
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
				}
				else{
					throw new SWTLayerException("Unuspported type");
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
	public <T> void select(final T w,final String[] items) {
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
						}
					} else {
						throw new SWTLayerException("List does not support multi selection - it does not have SWT MULTI style");
					}
				}
				else{
					throw new SWTLayerException("Unuspported type");
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
	public <T> void select(final T w,final int[] indices) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List){
					List widget = (List)w;
					if((widget.getStyle() & SWT.MULTI) !=0){
						widget.select(indices);
					} else {
						throw new SWTLayerException("List does not support multi selection - it does not have SWT MULTI style");
					}
				}
				else{
					throw new SWTLayerException("Unuspported type");
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
	public <T> void select(final T w,final int index) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof List){
					List widget = (List)w;
					if(widget.getItemCount()-1 < index){
						throw new SWTLayerException("Unable to select item with index "+index+" because it does not exist");
					}
					widget.select(index);
				}
				else{
					throw new SWTLayerException("Unuspported type");
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
	public <T> String getLabel(final T w) {
		String label = Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				if (w instanceof List) {
					List widget = (List) w;
					for(String i: widget.getItems()){
					System.out.println(i);
					}
					Control[] controls = widget.getParent().getChildren();
					for (int i = 0; i < controls.length; i++) {
						System.out.println(controls[i].getClass());
						if(controls[i] instanceof Label){
							System.out.println(((Label) controls[i]).getText());
						}
						if (controls[i] instanceof Label
								&& controls[i + 1].equals(widget)) {
							return ((Label) controls[i]).getText();
						}
					}
					throw new SWTLayerException(
							"Widget does not have any label");
				} else
					throw new SWTLayerException("Unuspported type");
			}
		});
		return label;
	}

	/**
	 * Gets tooltip if supported widget type
	 * 
	 * @param widget
	 * @return widget text
	 */
	public <T> String getToolTipText(final T w) {
		String text = Display.asynExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				if (w instanceof Text)
					return ((Text) w).getToolTipText();
				else
					throw new SWTLayerException("Unuspported type");
			}
		});
		return text;
	}
	
	public <T> void setFocus(final T w) {
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				if (w instanceof Control) ((Control)w).setFocus();
				else throw new SWTLayerException("Unuspported type");
			}
		});
	}

}
