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
package org.eclipse.reddeer.junit.test.integration.runner.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.test.integration.runner.injection.RunnerIntegrationRequirement.RequirementAAnnotation;
import org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence;

public class RunnerIntegrationRequirement implements Requirement<RequirementAAnnotation>{

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface RequirementAAnnotation {

	}
	
	@Override
	public void fulfill() {
		TestSequence.addFulfill(RunnerIntegrationRequirement.class);
	}

	@Override
	public void setDeclaration(RequirementAAnnotation declaration) {
		TestSequence.addSetDeclaration(RunnerIntegrationRequirement.class);
	}

	@Override
	public void cleanUp() {
		TestSequence.addCleanup(RunnerIntegrationRequirement.class);
	}

	@Override
	public void runBeforeClass() {
		TestSequence.addReqBeforeClass(RunnerIntegrationRequirement.class);
	}

	@Override
	public void runBefore() {
		TestSequence.addReqBefore(RunnerIntegrationRequirement.class);
	}

	@Override
	public void runAfterClass() {
		TestSequence.addReqAfterClass(RunnerIntegrationRequirement.class);
	}

	@Override
	public void runAfter() {
		TestSequence.addReqAfter(RunnerIntegrationRequirement.class);
	}

	@Override
	public RequirementAAnnotation getDeclaration() {
		return null;
	}
}
