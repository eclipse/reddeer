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
package org.eclipse.reddeer.junit.requirement;

import java.lang.annotation.Annotation;

/**
 * An abstract implementation of Requirement which provides protected field
 * 'annotation' with its getter and setter.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 * @param <T>
 *            configuration annotation
 */
public abstract class AbstractRequirement<T extends Annotation> implements Requirement<T> {

	protected T annotation;

	@Override
	public void setDeclaration(T declaration) {
		this.annotation = declaration;
	}

	@Override
	public T getDeclaration() {
		return annotation;
	}

}
