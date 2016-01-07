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

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.core.handler.WidgetHandler;

/**
 * Code generator for shells.
 * 
 * @author apodhrad
 *
 */
public class ShellCodeGenerator implements CodeGenerator {

	@Override
	public boolean isSupported(Control control) {
		return control instanceof Shell;
	}

	@Override
	public String getConstructor(Control control) {
		if (!isSupported(control)) {
			throw new IllegalArgumentException("Given " + control.getClass() + "is not supported");
		}
		String title = WidgetHandler.getInstance().getText(control);
		return "new DefaultShell(\"" + title + "\")";
	}

	@Override
	public String getGeneratedCode(Control control) {
		if (control instanceof Shell) {
			return method().name("activate").command(getConstructor(control)).toString();
		}
		return null;
	}

}
