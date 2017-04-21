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
package org.eclipse.reddeer.core.util;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.reddeer.common.util.ObjectUtil;
import org.eclipse.reddeer.core.exception.CoreLayerException;

/**
 * Util class to retrieve text or tooltip text of widget
 * @author rawagner
 *
 */
public class TextWidgetUtil {
	
	/**
	 * Gets text of specified widget.
	 *
	 * @param widget widget to handle
	 * @return text of specified widget
	 */
	public static String getText(Widget widget){
		Object o = ObjectUtil.invokeMethod(widget, "getText");

		if (o == null){
			return null;
		}

		if (o instanceof String) {
			return (String) o;
		}

		throw new CoreLayerException(
				"Return value of method getText() on class " + o.getClass()
						+ " should be String, but was " + o.getClass());
	}
	
	
	/**
	 * Gets tool tip text of specified widget.
	 *
	 * @param widget widget to handle
	 * @return tool tip text of specified widget
	 */
	public static String getToolTipText(final Widget widget) {
		Object o = null;
		try {
			o = ObjectUtil.invokeMethod(widget, "getToolTipText");
		} catch (RuntimeException e) {
			throw new CoreLayerException(
					"Runtime error during retrieving widget's text", e);
		}
		if (o == null) {
			return null;
		}

		if (o instanceof String) {
			return (String) o;
		}

		throw new CoreLayerException(
				"Return value of method getText() on class " + o.getClass()
						+ " should be String, but was " + o.getClass());
	}

}
