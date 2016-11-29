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

import java.util.Arrays;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Point;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.core.exception.CoreLayerException;

/**
 * Contains methods for handling UI operations on {@link CCombo} widgets.
 * 
 * @author Andrej Podhradsky
 *
 */
public class CComboHandler {

	private static CComboHandler instance;

	private static final Logger log = Logger.getLogger(CComboHandler.class);

	private CComboHandler() {

	}

	/**
	 * Gets instance of CComboHandler.
	 * 
	 * @return instance of CComboHandler
	 */
	public static CComboHandler getInstance() {
		if (instance == null) {
			instance = new CComboHandler();
		}
		return instance;
	}

	/**
	 * Gets items included in specified {@link CCombo}.
	 * 
	 * @param ccombo
	 *            custom combo to handle
	 * @return names of items included in custom combo
	 */
	public String[] getItems(final CCombo ccombo) {
		return Display.syncExec(new ResultRunnable<String[]>() {

			@Override
			public String[] run() {
				return ccombo.getItems();
			}
		});
	}

	/**
	 * Sets selection of specified {@link CCombo} to the item on specified
	 * position.
	 * 
	 * @param ccombo
	 *            custom combo to handle
	 * @param index
	 *            index of item to select
	 */
	public void setSelection(final CCombo ccombo, final int index) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				int itemsLength = getItems(ccombo).length;
				if (index >= itemsLength) {
					log.error("CCombo does not have " + index + 1 + "items!");
					log.info("CCombo has " + itemsLength + " items");
					throw new CoreLayerException("Nonexisted item in combo was requested");
				} else {
					ccombo.select(index);
				}
			}
		});
	}

	/**
	 * Sets selection of specified {@link CCombo} to specified text.
	 * 
	 * @param ccombo
	 *            custom combo to handle
	 * @param text
	 *            text to set
	 */
	public void setSelection(final CCombo ccombo, final String text) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				String[] items = getItems(ccombo);
				int index = Arrays.asList(items).indexOf(text);
				if (index == -1) {
					log.error("'" + text + "' is not contained in custom combo items");
					log.info("Items present in custom combo:");
					int i = 0;
					for (String item : items) {
						log.info("    " + item + "(index " + i);
						i++;
					}
					throw new CoreLayerException("Nonexisting item in custom combo with text \"" 
												+ text + "\" was requested");
				} else {
					ccombo.select(index);
				}
			}
		});
	}

	/**
	 * Gets text of the selection of specified {@link CCombo}.
	 * 
	 * @param ccombo
	 *            custom combo to handle
	 * @return text of specified selection of specified custom combo
	 */
	public String getSelection(final CCombo ccombo) {
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				Point selection = ccombo.getSelection();
				String comboText = ccombo.getText();
				String selectionText = "";
				if (selection.y > selection.x) {
					selectionText = comboText.substring(selection.x, selection.y);
				}
				return selectionText;
			}
		});
	}

	/**
	 * Gets index of the selection of specified {@link CCombo}.
	 * 
	 * @param ccombo
	 *            custom combo to handle
	 * @return index of the selection of specified custom combo
	 */
	public int getSelectionIndex(final CCombo ccombo) {
		return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				return ccombo.getSelectionIndex();
			}
		});
	}
}
