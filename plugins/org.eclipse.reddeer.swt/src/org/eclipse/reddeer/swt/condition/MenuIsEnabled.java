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
