/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.requirement;

import java.lang.annotation.Annotation;

import org.eclipse.reddeer.junit.execution.IExecutionPriority;

/**
 * 
 * Represents one requirement that should be fulfilled before the test runs. 
 * If it cannot be fulfilled the test should be ignored.
 * 
 * @author Lucia Jelinkova, Ondrej Dockal
 *
 */
public interface Requirement<T extends Annotation> extends IExecutionPriority {
	
	/**
	 * Can fulfill.
	 *
	 * @return true, if successful
	 */
	boolean canFulfill();

	/**
	 * Fulfill.
	 */
	void fulfill();
	
	/**
	 * Sets the declaration.
	 *
	 * @param declaration the new declaration
	 */
	void setDeclaration(T declaration);
	
	/**
	 * Clean up after requirement.
	 */
	void cleanUp();
	
	/**
	 * Decides whether it is possible to accept specific requirement's configuration object or not
	 * @return true, if declaration fits in
	 */
	default boolean isDeclarationAcceptable() { return true; }
	
	@Override
	default long getPriority() {
		return 0;
	}
}

