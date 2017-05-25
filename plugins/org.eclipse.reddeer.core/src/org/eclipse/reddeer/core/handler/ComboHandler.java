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
package org.eclipse.reddeer.core.handler;


import java.util.Arrays;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.exception.CoreLayerException;

/**
 * Contains methods for handling UI operations on {@link Combo} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class ComboHandler extends ControlHandler{

	private static final Logger log = Logger.getLogger(ComboHandler.class);
	private static ComboHandler instance;
	
	/**
	 * Gets instance of ComboHandler.
	 * 
	 * @return instance of ComboHandler
	 */
	public static ComboHandler getInstance(){
		if(instance == null){
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
	public void setSelection(final Combo combo, final int index) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				int itemsLength = getItems(combo).length;
				if (index >= itemsLength) {
					log.error("Combo does not have " + index + 1 + "items!");
					log.info("Combo has " + itemsLength + " items");
					throw new CoreLayerException(
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
					throw new CoreLayerException(
							"Nonexisting item in combo with text \"" + text + "\" was requested");
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
	
	/**
	 * Sets text of combo
	 * @param combo combo to handler
	 * @param text that should be set to specified combo
	 */
	public void setText(final Combo combo, String text){
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				combo.setText(text);
				
			}
		});
	}
	
	/**
	 * Gets text of combo
	 * @param combo to get text from
	 * @return text of specified combo
	 */
	public String getText(final Combo combo){
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return combo.getText();
			}
		});
	}
}
