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
