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
 * Class for handling FormText widget
 * 
 * @author rhopp
 *
 */

@SuppressWarnings("restriction")
public class FormTextHandler {

	private static FormTextHandler instance;

	/**
	 * Creates (if it was not yet created) and returns instance of this class.
	 * 
	 * @return
	 */

	public static FormTextHandler getInstance() {
		if (instance == null) {
			instance = new FormTextHandler();
		}
		return instance;
	}

	/**
	 * Performs 'click' on first hyperlink in this FormText widget
	 * <p>
	 * (Selects desired hyperlink and sends SWT.KeyDown event with character
	 * '/r')
	 * 
	 * @param widget
	 */

	public void click(final FormText widget) {
		click(widget, 0);
	}

	/**
	 * Performs 'click' on nth hyperlink in this FormText widget
	 * <p>
	 * (Selects desired hyperlink and sends SWT.KeyDown event with character
	 * '/r')
	 * 
	 * @param widget
	 * @param hyperlinkSegmentIndex
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
	 * Returns true if given widget has focus
	 * 
	 * @param widget
	 * @return
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
	 * Fetches widgets text
	 * <p>
	 * Uses reflection API to get to the FormTextModel of given FormText widget
	 * 
	 * @param widget
	 * @return
	 */

	public String getText(FormText widget) {
		FormTextModel model = getModel(widget);
		return model.getAccessibleText().trim();

	}

	/**
	 * Returns tooltip text of given FormText widget
	 * 
	 * @param widget
	 * @return
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
