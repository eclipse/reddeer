/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.test.integration.runner.order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.test.integration.runner.order.OrderRunnerRequirement.RequirementOrderAnnotation;

public class OrderRunnerRequirement implements Requirement<RequirementOrderAnnotation> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface RequirementOrderAnnotation {

	}
	
	@Override
	public void fulfill() {
		TestSequence.addFulfill(OrderRunnerRequirement.class);
	}

	@Override
	public void setDeclaration(RequirementOrderAnnotation declaration) {
	}

	@Override
	public void cleanUp() {
		TestSequence.addCleanup(OrderRunnerRequirement.class);
	}

	@Override
	public void runBeforeClass() {
		TestSequence.addReqBeforeClass(OrderRunnerRequirement.class);
	}

	@Override
	public void runBefore() {
		TestSequence.addReqBefore(OrderRunnerRequirement.class);
	}

	@Override
	public void runAfterClass() {
		TestSequence.addReqAfterClass(OrderRunnerRequirement.class);
	}

	@Override
	public void runAfter() {
		TestSequence.addReqAfter(OrderRunnerRequirement.class);
	}

	@Override
	public RequirementOrderAnnotation getDeclaration() {
		return null;
	}
	
}
