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
package org.eclipse.reddeer.jface.matcher;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.core.handler.WidgetHandler;
/**
 * Matches shell which is JFace Window
 * @author rawagner
 *
 * @param <T> class that extends window
 */
public class WindowMatcher<T extends Window> extends BaseMatcher<T>{
	
	private Class<T> windowClass;
	
	public WindowMatcher(Class<T> windowClass) {
		this.windowClass = windowClass;
	}

	@Override
	public boolean matches(Object item) {
		if(item instanceof Shell){
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
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub
		
	}

}
