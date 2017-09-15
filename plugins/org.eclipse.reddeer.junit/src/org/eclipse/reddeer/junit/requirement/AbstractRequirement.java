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
