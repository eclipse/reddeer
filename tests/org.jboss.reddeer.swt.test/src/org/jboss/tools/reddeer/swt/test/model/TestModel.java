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
package org.jboss.tools.reddeer.swt.test.model;


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
