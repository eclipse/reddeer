/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.common.adaptable;

public class MidClass implements RedDeerAdaptable<TopClass>, TopClass {

	private int firstField;
	
	public MidClass(int firstParam) {
		firstField = firstParam;
	}
	
	@Override
	public String getString() {
		return "MidClass";
	}
	
	public int getFirstField() {
		return firstField;
	}
	
	public void setFirstField(int firstField) {
		this.firstField = firstField;
	}
	
	@Override
	public Object[] getAdapterConstructorArguments() {
		return new Object[] {firstField};
	}
	
	@Override
	public Class<?>[] getAdapterConstructorClasses() {
		return new Class<?>[] {int.class};
	}
}
