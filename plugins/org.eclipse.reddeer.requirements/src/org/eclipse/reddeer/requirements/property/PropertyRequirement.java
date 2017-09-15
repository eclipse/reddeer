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
package org.eclipse.reddeer.requirements.property;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.AbstractConfigurableRequirement;
import org.eclipse.reddeer.requirements.property.PropertyRequirement.PropertyReq;

/**
 * Property based configurable requirements. Useful for reading varios key-pair
 * values from a configuration file to be used within a test.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class PropertyRequirement extends AbstractConfigurableRequirement<PropertyConfiguration, PropertyReq> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface PropertyReq {

	}

	@Override
	public void fulfill() {
	}

	@Override
	public void cleanUp() {
	}

	@Override
	public Class<PropertyConfiguration> getConfigurationClass() {
		return PropertyConfiguration.class;
	}
}
