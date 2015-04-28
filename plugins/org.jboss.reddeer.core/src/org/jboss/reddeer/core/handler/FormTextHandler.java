package org.jboss.reddeer.core.handler;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.internal.forms.widgets.FormTextModel;
import org.eclipse.ui.internal.forms.widgets.IHyperlinkSegment;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link FormText} widgets.
 * 
 * @author rhopp
 *
 */

@SuppressWarnings("restriction")
public class FormTextHandler {

	private static FormTextHandler instance;

	/**
	 * Gets instance of FormTextHandler.
	 * 
	 * @return instance of FormTextHandler
	 */
	public static FormTextHandler getInstance() {
		if (instance == null) {
			instance = new FormTextHandler();
		}
		return instance;
	}

	/**
	 * Performs click on first hyperlink in specified {@link FormText} widget.
	 * 
	 * @param widget form text widget to click
	 */
	public void click(final FormText widget) {
		click(widget, 0);
	}

	/**
	 * Performs click on hyperlink place on specified index in specified {@link FormText} widget.
	 * 
	 * @param widget form text widget to click
	 * @param hyperlinkSegmentIndex index of hyperlink to click
	 */
	public void click(final FormText widget, final int hyperlinkSegmentIndex) {
		final FormTextModel model = getModel(widget);
		if (hyperlinkSegmentIndex > model.getHyperlinkCount()) {
			throw new CoreLayerException("FormText with text \""
					+ getText(widget) + "\" has only "
					+ model.getHyperlinkCount() + " hyperlink segments.");
		}
		Display.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				IHyperlinkSegment hyperlink = model
						.getHyperlink(hyperlinkSegmentIndex);
				model.selectLink(hyperlink);
				notifyWidget(SWT.KeyDown, widget);
			}
		});
	}

	/**
	 * Finds out whether specified {@link FormText} has control or not.
	 * 
	 * @param widget form text widget to handle
	 * @return true if specified form text widget has control, false otherwise
	 */
	public boolean hasFocus(final FormText widget) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return Display.getDisplay().getFocusControl() == widget;
			}
		});
	}

	/**
	 * Gets text of specified {@link FormText} widget.
	 * 
	 * @param widget form text widget to handle
	 * @return text of specified form text widget
	 */
	public String getText(FormText widget) {
		FormTextModel model = getModel(widget);
		return model.getAccessibleText().trim();

	}

	/**
	 * Gets tool tip text of specified {@link FormText} widget.
	 * 
	 * @param widget widget to handle
	 * @return tool tip text of specified form text widget
	 */
	public String getTooltipText(final FormText widget) {
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return widget.getToolTipText();
			}
		});
	}

	private void notifyWidget(int eventType, FormText widget) {
		Event event = createEvent(widget);
		WidgetHandler.getInstance().notify(eventType, event, widget);
	}

	private Event createEvent(FormText widget) {
		Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = widget;
		event.display = Display.getDisplay();
		event.type = SWT.KeyDown;
		event.character = '\r';
		return event;
	}

	private FormTextModel getModel(FormText widget) {
		Field field;
		try {
			field = widget.getClass().getDeclaredField("model");
		} catch (SecurityException e1) {
			throw new SecurityException(e1);
		} catch (NoSuchFieldException e1) {
			throw new SecurityException(e1);
		}
		FormTextModel model;
		field.setAccessible(true);
		try {
			model = (FormTextModel) field.get(widget);
		} catch (IllegalArgumentException e1) {
			throw new SecurityException(e1);
		} catch (IllegalAccessException e1) {
			throw new SecurityException(e1);
		}
		return model;
	}
}
