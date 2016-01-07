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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.core.handler.WidgetHandler;

/**
 * Contains methods for handling UI operations on {@link Button} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class ButtonHandler {

	private static ButtonHandler instance;

	private ButtonHandler() {

	}

	/**
	 * Gets instance of ButtonHandler.
	 * 
	 * @return instance of ButtonHandler
	 */
	public static ButtonHandler getInstance() {
		if (instance == null) {
			instance = new ButtonHandler();
		}
		return instance;
	}

	/**
	 * Clicks specified {@link Button}.
	 * 
	 * @param button button to handle
	 */
	public void click(final Button button) {

		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (((button.getStyle() & SWT.TOGGLE) != 0)
						|| ((button.getStyle() & SWT.CHECK) != 0)) {
					button.setSelection(!button.getSelection());
				}
			}
		});

		WidgetHandler.getInstance().sendClickNotifications(button);

		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (!button.isDisposed()) {
					handleNotSelectedRadioButton(button);
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
	 * Checks whether specified {@link Button} is selected or not.
	 * 
	 * @param button button to handle
	 * @return true if button is selected, false otherwise
	 */
	public boolean isSelected(final Button button) {
		boolean selectionState = Display
				.syncExec(new ResultRunnable<Boolean>() {

					@Override
					public Boolean run() {
						return button.getSelection();
					}
				});
		return selectionState;
	}
}
