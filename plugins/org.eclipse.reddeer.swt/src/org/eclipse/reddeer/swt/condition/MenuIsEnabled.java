/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.eclipse.reddeer.swt.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.swt.api.Menu;

/**
 * Condition is met when specified menu is enabled.
 * 
 * @author jnovak@redhat.com
 *
 */
public class MenuIsEnabled extends AbstractWaitCondition {
	
	private Menu menu;

	public MenuIsEnabled(Menu menu) {
		this.menu = menu;
	}

	@Override
	public boolean test() {
		return menu.isEnabled();
	}
	
	@Override
	public String description() {
		return "menu is enabled";
	}

}
