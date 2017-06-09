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
package org.eclipse.reddeer.junit.test.internal.requirement;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.Requirement;

public class TestRequirementC implements Requirement<Annotation> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@interface TestRequirementCAnnotation {
		
	}	
	
	private Annotation declaration;

	@Override
	public void fulfill() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDeclaration(Annotation declaration) {
		this.declaration = declaration;
	}
	
	public Annotation getDeclaration() {
		return this.declaration;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public long getPriority() {
		// TODO Auto-generated method stub
		return 100;
	}
	
}
