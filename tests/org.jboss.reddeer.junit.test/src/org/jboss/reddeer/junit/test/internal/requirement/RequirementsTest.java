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
package org.jboss.reddeer.junit.test.internal.requirement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.junit.Test;

public class RequirementsTest {

	private Requirements requirements;
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_nullList(){
		new Requirements(null, null, null);
	}
	
	@Test
	public void size() {
		Requirement<?> requirement1 = mock(Requirement.class);
		Requirement<?> requirement2 = mock(Requirement.class);
		
		requirements = new Requirements(asList(requirement1, requirement2), String.class, null);

		assertThat(requirements.size(), is(2));
	}
	
	@Test
	public void iterator() {
		Requirement<?> requirement1 = mock(Requirement.class);
		Requirement<?> requirement2 = mock(Requirement.class);
		
		requirements = new Requirements(asList(requirement1, requirement2), String.class, null);
		Iterator<Requirement<?>> iterator = requirements.iterator();
		
		assertSame(requirement1, iterator.next());
		assertSame(requirement2, iterator.next());
	}
	
	@Test
	public void canFulfill_emptyList() {
		requirements = new Requirements(new ArrayList<Requirement<?>>(), String.class, null);
		
		assertTrue(requirements.canFulfill());
	}
	
	@Test
	public void canFulfill_yes() {
		Requirement<?> requirement1 = mock(Requirement.class);
		when(requirement1.canFulfill()).thenReturn(true);
		
		Requirement<?> requirement2 = mock(Requirement.class);
		when(requirement2.canFulfill()).thenReturn(true);
		
		requirements = new Requirements(asList(requirement1, requirement2), String.class, null);
		
		assertTrue(requirements.canFulfill());
	}
	
	@Test
	public void canFulfill_no() {
		Requirement<?> requirement1 = mock(Requirement.class);
		when(requirement1.canFulfill()).thenReturn(true);
		
		Requirement<?> requirement2 = mock(Requirement.class);
		when(requirement2.canFulfill()).thenReturn(false);
		
		requirements = new Requirements(asList(requirement1, requirement2), String.class, null);
		
		assertFalse(requirements.canFulfill());
	}
	
	@Test
	public void fulfill() {
		Requirement<?> requirement1 = mock(Requirement.class);
		Requirement<?> requirement2 = mock(Requirement.class);

		requirements = new Requirements(asList(requirement1, requirement2), String.class, null);
		requirements.fulfill();
		
		verify(requirement1).fulfill();
		verify(requirement2).fulfill();
	}
	
	@Test
	public void cleanup() {
		Requirement<?> requirement1 = mock(Requirement.class);
		Requirement<?> requirement2 = mock(Requirement.class);

		requirements = new Requirements(asList(requirement1, requirement2), String.class, null);
		requirements.cleanUp();
		
		verify(requirement1).cleanUp();
		verify(requirement2).cleanUp();
	}
	
	private List<Requirement<?>> asList(Requirement<?>... requirements) {
		return Arrays.asList(requirements);
	}
}
