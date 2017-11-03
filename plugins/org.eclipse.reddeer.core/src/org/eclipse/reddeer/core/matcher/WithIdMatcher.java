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
package org.eclipse.reddeer.core.matcher;

import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.core.handler.WidgetHandler;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher matching a pair key/value obtained by {@link org.eclipse.swt.widgets.Widget#getData(String)}.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class WithIdMatcher extends TypeSafeMatcher<Widget> {

	private String key;
	private Object value;

	/**
	 * Constructs the matcher with a key specified by "rd.defaultKey" and a given value.
	 * 
	 * @param value
	 *            value
	 */
	public WithIdMatcher(Object value) {
		this(RedDeerProperties.DEFAULT_KEY.getValue(), value);
	}

	/**
	 * Constructs the matcher with a given key and value. If key is null then the value is obtained by
	 * {@link org.eclipse.swt.widgets.Widget#getData()}.
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	public WithIdMatcher(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public void describeTo(Description desc) {
		desc.appendText(" with key '" + key + "' and value '" + value + "'");
	}

	@Override
	protected boolean matchesSafely(Widget widget) {
		Object widgetValue = null;
		if (key == null) {
			widgetValue = WidgetHandler.getInstance().getData(widget);
		} else {
			widgetValue = WidgetHandler.getInstance().getData(widget, key);
		}
		if (widgetValue == null) {
			return value == null;
		}
		return widgetValue.equals(value);
	}

}
