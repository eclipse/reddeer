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
package org.eclipse.reddeer.junit.test.annotation.resources;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.test.annotation.resources.DefaultTestRequirement.SomeReq;

public class DefaultTestRequirement implements Requirement<SomeReq>{

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface SomeReq {
		
	}

	@Override
	public void fulfill() {}

	@Override
	public void setDeclaration(SomeReq declaration) {}

	@Override
	public SomeReq getDeclaration() {
		return null;
	}

	@Override
	public void cleanUp() {}
}
