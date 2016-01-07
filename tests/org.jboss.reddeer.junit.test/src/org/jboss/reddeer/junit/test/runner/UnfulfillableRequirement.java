/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.junit.test.runner;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.requirement.Requirement;

public class UnfulfillableRequirement implements Requirement<Annotation> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@interface Unfulfillable {
		
	}
	
	public boolean canFulfill() {
		return false;
	}
	
	public void fulfill() {

	}

	@Override
	public void setDeclaration(Annotation declaration) {
		
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}
}
