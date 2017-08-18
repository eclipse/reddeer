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
 * Represents one requirement that should be fulfilled before the test runs. If
 * it cannot be fulfilled the test should be ignored.
 * 
 * @author Lucia Jelinkova, Ondrej Dockal, mlabuda@redhat.com
 *
 */
public interface Requirement<T extends Annotation> extends IExecutionPriority {

	/**
	 * Executes requirement.
	 */
	void fulfill();

	/**
	 * Sets an annotation used for declaring a requirement. Declaration could
	 * contain requirement instance settings, such as customization of its
	 * fulfilling.
	 *
	 * @param declaration
	 *            declaration of a requirement
	 */
	void setDeclaration(T declaration);

	/**
	 * Gets declaration of a requirement
	 * 
	 * @return declaration of requirement
	 */
	T getDeclaration();

	/**
	 * Clean up after requirement.
	 */
	void cleanUp();

	/**
	 * Runs the requirement before a test class.
	 */
	default void runBeforeClass() {

	}

	/**
	 * Runs the requirement before each test.
	 */
	default void runBefore() {

	}

	/**
	 * Runs the requirement before after a test class.
	 */
	default void runAfterClass() {

	}

	/**
	 * Runs the requirement after each test.
	 */
	default void runAfter() {

	}

	@Override
	default long getPriority() {
		return 0;
	}
}
