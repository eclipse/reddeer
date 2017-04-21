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
package org.eclipse.reddeer.junit.test.internal.configuration;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.reddeer.junit.internal.configuration.TestClassRequirementMap;
import org.eclipse.reddeer.junit.test.internal.requirement.TestCustomJavaConfiguration;
import org.eclipse.reddeer.junit.test.internal.requirement.TestCustomServerConfiguration;
import org.junit.Before;
import org.junit.Test;

public class TestClassRequirementSetTest {

	private TestClassRequirementMap set;
	
	private Set<Class<?>> configs;
	
	@Before
	public void setup() {
		configs = new HashSet<>();
	}
	
	@Test
	public void getRequirementConfiguration() {
		set = new TestClassRequirementMap(configs, null);
		assertThat(set.getRequirementConfiguration().size(), is(0));
		
		configs.add(TestCustomJavaConfiguration.class);
		configs.add(TestCustomServerConfiguration.class);
		
		set = new TestClassRequirementMap(configs, null);
		
		assertThat(set.getRequirementConfiguration().size(), is(2));
	}
	
	@Test
	public void getClasses() {
		set = new TestClassRequirementMap(configs, null); 
		
		assertThat(set.getClasses().size(), is(0));
		
		set = new TestClassRequirementMap(configs, TestClass1.class);
		set.addClass(TestClass2.class);
		
		assertThat(set.getClasses().size(), is(2));
		assertTrue(set.getClasses().get(0).equals(TestClass1.class));
		assertTrue(set.getClasses().get(1).equals(TestClass2.class));
	}
	
	@Test
	public void getClassesAsArray() {
		set = new TestClassRequirementMap(configs, null);
		
		assertThat(set.getClassesAsArray().length, is(0));
		
		set = new TestClassRequirementMap(configs, TestClass1.class);
		set.addClass(TestClass2.class);
		
		assertThat(set.getClassesAsArray().length, is(2));
		
		assertTrue(set.getClassesAsArray()[0].equals(TestClass1.class));
		assertTrue(set.getClassesAsArray()[1].equals(TestClass2.class));
	}
	
	@Test
	public void equalsAnnotationSet() {
		set = new TestClassRequirementMap(configs, null);
		
		Set<Class<?>> testSet = new HashSet<>();
		assertTrue(set.equalAnnotationSet(testSet));
		
		testSet.add(String.class);
		assertFalse(set.equalAnnotationSet(testSet));
		
		configs.add(TestCustomJavaConfiguration.class);
		configs.add(TestCustomServerConfiguration.class);		
		
		set = new TestClassRequirementMap(configs, null);
		
		testSet.clear();
		testSet.add(TestCustomServerConfiguration.class);
		testSet.add(TestCustomJavaConfiguration.class);
		
		assertTrue(set.equalAnnotationSet(testSet));
		
		testSet.add(String.class);
		assertFalse(set.equalAnnotationSet(testSet));
	}
	
	@Test
	public void getRequirementConfiguration_caching() {
		
		configs.add(TestCustomJavaConfiguration.class);
		configs.add(TestCustomServerConfiguration.class);
		
		set = new TestClassRequirementMap(configs, null);
		
		Set<Class<?>> reqSet1 = set.getRequirementConfiguration();
		Set<Class<?>> reqSet2 = set.getRequirementConfiguration();
		
		assertSame(reqSet1, reqSet2);
		assertThat(reqSet1, instanceOf(Set.class));
		assertSame(reqSet1.iterator().next(), reqSet2.iterator().next());
		assertSame(reqSet1.iterator().next(), reqSet2.iterator().next());
		assertTrue(reqSet1.contains(TestCustomJavaConfiguration.class));
		assertTrue(reqSet2.contains(TestCustomJavaConfiguration.class));
	}
	
	private static class TestClass1{}
	
	private static class TestClass2{}
	
}
