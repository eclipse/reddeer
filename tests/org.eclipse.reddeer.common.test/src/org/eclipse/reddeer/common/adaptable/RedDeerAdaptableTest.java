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
package org.eclipse.reddeer.common.adaptable;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RedDeerAdaptableTest {
	
	@Test
	public void testAdoptBottomClassAToMidClass() {
		MidClass midClass = new MidClass(1);
		BottomClassA bottomClassA = midClass.getAdapter(BottomClassA.class);
		
		assertTrue(bottomClassA.getString().equals("BottomClassA"));
	}
	
	@Test
	public void testAdoptBottomClassBToMidClass() {
		MidClass midClass = new MidClass(2);
		BottomClassB bottomClassB = midClass.getAdapter(BottomClassB.class);
		
		assertTrue(bottomClassB.getString().equals("BottomClassB"));
	}
}
