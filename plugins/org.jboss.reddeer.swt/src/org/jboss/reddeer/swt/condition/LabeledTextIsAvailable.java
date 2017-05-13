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
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * Wait condition to wait for a LabeledText widget with specified text is available.
 * 
 * @author ldimaggi@redhat.com (based on CLabelWithTextIsAvailable by mlabuda@redhat.com)
 *
 */
public class LabeledTextIsAvailable extends AbstractWaitCondition {

	private String textLabel;

	public LabeledTextIsAvailable(String textLabel) {
		this.textLabel = textLabel;
	}

	@Override
	public boolean test() {
		try {
			new LabeledText (textLabel);
			return true;
		} catch (CoreLayerException ex) {
			return false;
		}
	}

	@Override
	public String description() {
		return "    LabeledText '" + textLabel + "' is available.";
	}
}
