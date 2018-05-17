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
package org.eclipse.reddeer.swt.test.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class ParameterizedHandler extends AbstractHandler {

	private static boolean toggledA = false;
	private static boolean toggledB = false;
	
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String parameter = event.getParameter("RedDeerParameterID");
		if (parameter.equals("A")){
			toggledA = true;
		}else if(parameter.equals("B")){
			toggledB = true;
		}
		return null;
	}
	
	public static boolean isToggledA() {
		return toggledA;
	}
	
	public static boolean isToggledB() {
		return toggledB;
	}
	

}
