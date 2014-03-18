package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods that handle UI operations on {@link Button} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ButtonHandler {

	private static ButtonHandler instance;

	private ButtonHandler() {

	}

	/**
	 * Creates and returns instance of ButtonHandler class
	 * 
	 * @return
	 */
	public static ButtonHandler getInstance() {
		if (instance == null) {
			instance = new ButtonHandler();
		}
		return instance;
	}
	
	/**
	 * Click the {@link Button}
	 * 
	 * @param w given widgets
	 */
	public void click(final Button button) {
		
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
					if (((button.getStyle() & SWT.TOGGLE) != 0) ||
						((button.getStyle() & SWT.CHECK) != 0)) {
						button.setSelection(!button.getSelection());
					}
			}
		});
		
		WidgetLookup.getInstance().sendClickNotifications(button);
		
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (!button.isDisposed()){
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
	 * Checks if button is selected
	 * 
	 * @param w	given widget
	 * @return	returns widget label text
	 */
	public boolean isSelected(final Button button) {
		boolean selectionState = Display.syncExec(new ResultRunnable<Boolean>() {
			
			@Override
			public Boolean run() {
				return button.getSelection();
			}
		});
		return selectionState;
	}
}
