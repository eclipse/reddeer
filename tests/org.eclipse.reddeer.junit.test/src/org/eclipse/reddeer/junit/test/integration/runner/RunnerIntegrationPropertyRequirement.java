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
package org.eclipse.reddeer.junit.test.integration.runner;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence;

public class RunnerIntegrationPropertyRequirement implements Requirement<Annotation> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface RequirementAAnnotation {
		
	}
	
	public boolean canFulfill() {
		return true;
	}
	
	public void fulfill() {
		TestSequence.addFulfill(RunnerIntegrationPropertyRequirement.class);
	}
	
	@Override
	public void setDeclaration(Annotation declaration) {
		TestSequence.addSetDeclaration(RunnerIntegrationPropertyRequirement.class);
	}

	@Override
	public void cleanUp() {
		TestSequence.addCleanup(RunnerIntegrationPropertyRequirement.class);
	}
}
