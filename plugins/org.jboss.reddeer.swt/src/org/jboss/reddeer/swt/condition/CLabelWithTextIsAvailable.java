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
package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;

/**
 * Wait condition to wait for a CLabel widget with specified text is available.
 * 
 * @author mlabuda@redhat.com
 * @deprecated since 1.1.0. Use {@link WidgetIsFound} instead.
 *
 */
public class CLabelWithTextIsAvailable extends AbstractWaitCondition {

	private String cLabelText;

	public CLabelWithTextIsAvailable(String cLabelText) {
		this.cLabelText = cLabelText;
	}

	@Override
	public boolean test() {
		try {
			new DefaultCLabel(cLabelText);
			return true;
		} catch (CoreLayerException ex) {
			return false;
		}
	}

	@Override
	public String description() {
		return "    CLabel with text '" + cLabelText + "' is available.";
	}
}
