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

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;

/**
 * An abstract implementation of ConfigurableRequirement which provides
 * protected fields 'configuration' and 'annotation' with their getters and
 * setters.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 * @param <T>
 *            requirement configuration
 * @param <K>
 *            requirement annotation
 */
public abstract class AbstractConfigurableRequirement<T extends RequirementConfiguration, K extends Annotation>
		implements ConfigurableRequirement<T, K> {

	protected T configuration;
	protected K annotation;

	@Override
	public void setDeclaration(K declaration) {
		this.annotation = declaration;
	}

	@Override
	public K getDeclaration() {
		return annotation;
	}

	@Override
	public void setConfiguration(T configuration) {
		this.configuration = configuration;
	}

	@Override
	public T getConfiguration() {
		return configuration;
	}
}
