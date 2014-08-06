package org.jboss.reddeer.swt.handler;

import java.util.Arrays;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Combo} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class ComboHandler {

	private static ComboHandler instance;

	private static final Logger log = Logger.getLogger(ComboHandler.class);

	private ComboHandler() {

	}

	/**
	 * Gets instance of ComboHandler.
	 * 
	 * @return instance of ComboHandler
	 */
	public static ComboHandler getInstance() {
		if (instance == null) {
			instance = new ComboHandler();
		}
		return instance;
	}

	/**
	 * Gets items included in specified {@link Combo}.
	 * 
	 * @param combo combo to handle
	 * @return names of items included in combo
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
	 * Sets selection of specified {@link Combo} to the item on specified position.
	 * 
	 * @param combo combo to handle
	 * @param index index of item to select
	 */
	public void setSelection(final Combo combo,
			final int index) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				int itemsLength = getItems(combo).length;
				if (index >= itemsLength) {
					log.error("Combo does not have " + index + 1 + "items!");
					log.info("Combo has " + itemsLength + " items");
					throw new SWTLayerException(
							"Nonexisted item in combo was requested");
				} else {
					combo.select(index);
				}
			}
		});
	}

	/**
	 * Sets selection of specified {@link Combo} to specified text.
	 * 
	 * @param combo combo to handle
	 * @param text text to set
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
					throw new SWTLayerException(
							"Nonexisting item in combo was requested");
				} else {
					combo.select(index);
				}
			}
		});
	}

	/**
	 * Gets text of the selection of specified {@link Combo}.
	 * 
	 * @param combo combo to handle
	 * @return text of specified selection of specified combo
	 */
	public String getSelection(final Combo combo) {
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				Point selection = combo.getSelection();
				String comboText = combo.getText();
				String selectionText = "";
				if (selection.y > selection.x) {
					selectionText = comboText.substring(selection.x,
							selection.y);
				}
				return selectionText;
			}
		});
	}

	/**
	 * Gets index of the selection of specified {@link Combo}.
	 * 
	 * @param combo combo to handle
	 * @return index of the selection of specified combo
	 */
	public int getSelectionIndex(final Combo combo) {
		return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				return combo.getSelectionIndex();
			}
		});
	}
}
