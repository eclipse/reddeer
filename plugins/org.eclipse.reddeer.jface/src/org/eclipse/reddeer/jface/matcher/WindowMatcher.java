/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.jface.matcher;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.core.handler.WidgetHandler;
/**
 * Matches shell which is JFace Window
 * @author rawagner
 *
 */
public class WindowMatcher extends TypeSafeMatcher<Shell>{
	
	private Class<? extends Window> windowClass;
	
	public <T extends Window> WindowMatcher(Class<T> windowClass) {
		this.windowClass = windowClass;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Window matching class "+windowClass);
		
	}

	@Override
	protected boolean matchesSafely(Shell item) {
		try{
			Object data = WidgetHandler.getInstance().getData((Shell)item);
			if(data != null) {
				return windowClass.isInstance(data);
			}
		} catch (RedDeerException e) {
			if(((Shell) item).isDisposed()){
				return false;
			}
			throw e;
		}
		return false;
	}

}
