package org.jboss.reddeer.swt.handler;

import java.util.Arrays;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods that handle UI operations on {@link Combo} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ComboHandler {

	private static ComboHandler instance;

	private final Logger log = Logger.getLogger(this.getClass());

	private ComboHandler() {

	}

	/**
	 * Creates and returns instance of ComboHandler class
	 * 
	 * @return
	 */
	public static ComboHandler getInstance() {
		if (instance == null) {
			instance = new ComboHandler();
		}
		return instance;
	}

	/**
	 * Gets items
	 * 
	 * @param combo
	 *            given widget
	 * @return array of items in widget
	 */
	public String[] getItems(final Combo combo) {
		return Display.syncExec(new ResultRunnable<String[]>() {

			@Override
			public String[] run() {
				return combo.getItems();
			}
		});
	}

	/**
	 * Sets selection with given index for supported widget type
	 * 
	 * @param index
	 */
	public <T extends Widget> void setSelection(final Combo combo, final int index) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				int itemsLength = getItems(combo).length;
				if (index >= itemsLength) {
					log.error("Combo does not have " + index + 1 + "items!");
					log.info("Combo has " + itemsLength + " items");
					throw new SWTLayerException("Nonexisted item in combo was requested");
				} else {
					combo.select(index);
				}
			}
		});
	}

	/**
	 * Sets selection with given text for supported widget type
	 * 
	 * @param index
	 */
	public void setSelection(final Combo combo, final String text) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				String[] items = getItems(combo);
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
					combo.select(index);
				}
			}
		});
	}

	/**
	 * Gets selection text for supported widget type
	 * 
	 * @param index
	 */
	public String getSelection(final Combo combo) {
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				Point selection = combo.getSelection();
				String comboText = combo.getText();
				String selectionText = "";
				if (selection.y > selection.x){
					selectionText = comboText.substring(selection.x , selection.y);
				}
				return selectionText;
			}
		});
	}
	
	/**
	 * Gets selection index for supported widget type
	 * 
	 * @param index
	 */
	public <T extends Widget> int getSelectionIndex(final Combo combo) {
		return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
					return combo.getSelectionIndex();
			}
		});
	}
}
