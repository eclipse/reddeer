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
package org.jboss.reddeer.junit.test.internal.requirement.inject;

import java.lang.annotation.Annotation;

import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;

public class RequirementB implements Requirement<Annotation>, PropertyConfiguration {

	private String a;
	
	public boolean canFulfill() {
		return false;
	}

	public void fulfill() {
	}
	
	public void setA(String a) {
		this.a = a;
	}
	
	public String getA() {
		return a;
	}
	
	@Override
	public void setDeclaration(Annotation declaration) {
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}
}
