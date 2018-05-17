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
package org.eclipse.reddeer.junit.test.internal.requirement;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.Requirement;

public class TestRequirementA implements Requirement<Annotation> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@interface TestRequirementAAnnotation {
		
	}
	
	private Annotation declaration;
	
	public boolean canFulfill() {
		return false;
	}
	
	public void fulfill() {

	}

	@Override
	public void setDeclaration(Annotation declaration) {
		this.declaration = declaration;
	}
	
	public Annotation getDeclaration() {
		return declaration;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
	}
}
