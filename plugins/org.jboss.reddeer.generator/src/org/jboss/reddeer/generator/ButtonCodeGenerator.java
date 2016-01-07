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
package org.jboss.reddeer.generator;

import static org.jboss.reddeer.generator.builder.MethodBuilder.method;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.jboss.reddeer.swt.generator.framework.rules.RedDeerUtils;
import org.jboss.reddeer.core.handler.WidgetHandler;

/**
 * Code generator for buttons.
 * 
 * @author apodhrad
 *
 */
public class ButtonCodeGenerator implements CodeGenerator {

	@Override
	public boolean isSupported(Control control) {
		return control instanceof Button;
	}

	@Override
	public String getConstructor(Control control) {
		if (!isSupported(control)) {
			throw new IllegalArgumentException("Given " + control.getClass() + "is not supported");
		}
		Button button = (Button) control;
		String label = WidgetHandler.getInstance().getText(button);
		String type = null;
		int style = button.getStyle();
		if ((style & SWT.PUSH) != 0) {
			type = "PushButton";
		} else if ((style & SWT.CHECK) != 0) {
			type = "CheckBox";
		} else if ((style & SWT.ARROW) != 0) {
			type = "ArrowButton";
		} else if ((style & SWT.RADIO) != 0) {
			type = "RadioButton";
		} else if ((style & SWT.TOGGLE) != 0) {
			type = "ToggleButton";
		}
		if (type == null) {
			throw new IllegalArgumentException("Unsupported button style " + style);
		}
		if (label == null || label.isEmpty()) {
			label = String.valueOf(WidgetUtils.getIndex(button));
		} else {
			label = "\"" + label + "\"";
		}
		String ref = RedDeerUtils.getReferencedCompositeString(RedDeerUtils.getComposites(button));
		return "new " + type + "(" + ref + WidgetUtils.cleanText(label) + ")";
	}

	@Override
	public String getGeneratedCode(Control control) {
		if (control instanceof Button) {
			Button button = (Button) control;
			String label = WidgetHandler.getInstance().getText(button);
			if (label == null || label.isEmpty()) {
				return null;
			}
			return method().type("Button").get(label).returnCommand(getConstructor(control)).toString();
		}
		return null;
	}
}
