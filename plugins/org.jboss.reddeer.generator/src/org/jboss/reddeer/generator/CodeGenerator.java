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

import org.eclipse.swt.widgets.Control;

/**
 * Interface for all code generators. If the code generator supports a given control/widget then it will generate the
 * appropriate code (it will generate constants, getter/setter methods, etc).
 * 
 * @author apodhrad
 *
 */
public interface CodeGenerator {

	/**
	 * Returns whether a given control/widget is supported.
	 * 
	 * @param control
	 *            control/widget
	 * @return whether a given control/widget is supported
	 */
	boolean isSupported(Control control);

	/**
	 * Returns lookup constructor for a given control/widget
	 * 
	 * @param control
	 *            control/widget
	 * @return lookup constructor for a given control/widget
	 */
	String getConstructor(Control control);

	/**
	 * Return the appropriate code (constants, getter/setter methods, etc).
	 * 
	 * @param control
	 *            control/widget
	 * @return generated code
	 */
	String getGeneratedCode(Control control);
}
