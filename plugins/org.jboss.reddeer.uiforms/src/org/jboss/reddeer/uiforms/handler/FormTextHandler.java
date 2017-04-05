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
package org.jboss.reddeer.uiforms.handler;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.internal.forms.widgets.FormTextModel;
import org.eclipse.ui.internal.forms.widgets.IHyperlinkSegment;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.handler.ControlHandler;

/**
 * Contains methods for handling UI operations on {@link FormText} widgets.
 * 
 * @author rhopp
 *
 */

@SuppressWarnings("restriction")
public class FormTextHandler extends ControlHandler{
	
	private static FormTextHandler instance;
	
	/**
	 * Gets instance of FormTextHandler.
	 * 
	 * @return instance of FormTextHandler
	 */
	public static FormTextHandler getInstance(){
		if(instance == null){
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
		checkModalShells(widget);
		click(widget, 0);
	}

	/**
	 * Performs click on hyperlink place on specified index in specified {@link FormText} widget.
	 * 
	 * @param widget form text widget to click
	 * @param hyperlinkSegmentIndex index of hyperlink to click
	 */
	public void click(final FormText widget, final int hyperlinkSegmentIndex) {
		checkModalShells(widget);
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
	 * Gets text of specified {@link FormText} widget.
	 * 
	 * @param widget form text widget to handle
	 * @return text of specified form text widget
	 */
	public String getText(FormText widget) {
		FormTextModel model = getModel(widget);
		return model.getAccessibleText().trim();

	}

	private void notifyWidget(int eventType, FormText widget) {
		Event event = createEvent(widget);
		notifyWidget(eventType, event, widget);
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
