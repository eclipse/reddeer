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
package org.eclipse.tools.reddeer.swt.test.model;


public class TestModel {

	private static boolean clicked = false;
	
	public static void setClicked() {
		clicked = true;
	}
	
	public static boolean getClickedAndReset() {

		boolean ret = clicked;
		clicked = false;
		return ret;
	}	
}
