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
package org.eclipse.reddeer.junit.test.requirement.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.junit.requirement.matcher.RequirementVersionMatcher;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.SimpleConfiguration;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.ComplexConfiguration;
import org.eclipse.reddeer.junit.requirement.RequirementException;
import org.eclipse.reddeer.junit.requirement.matcher.RequirementRegexMatcher;
import org.junit.BeforeClass;
import org.junit.Test;

public class RequirementMatcherTest {

	private static SimpleConfiguration config;
	
	private static ComplexConfiguration complexConfig;

	@BeforeClass
	public static void setup() {
		config = new SimpleConfiguration();
		config.setName("SimpleConfig");
		config.setVersion("1.1");
		
		complexConfig = new ComplexConfiguration();
		complexConfig.setComplexName("ComplexConfig");
		complexConfig.setComplexType("ComplexType");
		complexConfig.setComplexVersion("2.0");
		complexConfig.setSimpleConfiguration(config);
	}

	@Test
	public void testNestedAttributeOnPreciseMatch() {
		RequirementRegexMatcher matcher = new RequirementRegexMatcher(ComplexConfiguration.class, "simpleConfiguration.name", "SimpleConfig");
		assertTrue("Name attribute of configuration should be matched on precise match", matcher.matches(complexConfig));
	}
	
	@Test
	public void testNestedAttributeOnVersionMatch() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(ComplexConfiguration.class, "simpleConfiguration.version", ">1.0");
		assertTrue("Version attribute of configuration should be matched on version match", matcher.matches(complexConfig));
	}
	
	@Test(expected=RequirementException.class)
	public void testThrowExceptionOnNonexistingAttribute() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(ComplexConfiguration.class, "noConfig.version", ">1.0");
		matcher.matches(complexConfig);
	}
	
	@Test(expected=RequirementException.class)
	public void testThrowExceptionOnNonexistingNestedAttribute() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(ComplexConfiguration.class, "simpleConfiguration.what", ">1.0");
		matcher.matches(complexConfig);
	}
	
	@Test
	public void testRequirementRegexMatcherOnPreciseMatch() {
		RequirementRegexMatcher matcher = new RequirementRegexMatcher(SimpleConfiguration.class, "name", "SimpleConfig");
		assertTrue("Name attribute of configuration should be matched on precise match", matcher.matches(config));
	}

	@Test
	public void testRequirementRegexMatcherOnWrongConfigurationTypeMatch() {
		RequirementRegexMatcher matcher = new RequirementRegexMatcher(ComplexConfiguration.class, "name", "SimpleConfig");
		assertFalse("Name attribute of configuration should be matched on precise match", matcher.matches(config));
	}
	
	@Test
	public void testRequirementRegexMatcherOnRegex() {
		RequirementRegexMatcher matcher = new RequirementRegexMatcher(SimpleConfiguration.class, "name", "Simple.*");
		assertTrue("Name attribute of configuration should be matched with regex.", matcher.matches(config));
	}

	@Test
	public void testRequirementRegexMatcherOnNonexistingValue() {
		RequirementRegexMatcher matcher = new RequirementRegexMatcher(SimpleConfiguration.class, "name", "Complex.*");
		assertFalse("Name of attribute should not be matched with current regex Complex*", matcher.matches(config));
	}
	
	@Test
	public void testRequirementVersionMatcherOnPreciseMatch() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(SimpleConfiguration.class, "version", "1.1");
		assertTrue("Version attribute should be matched to version matcher with value 1.1", matcher.matches(config));
	}
	
	@Test
	public void testRequirementVersionMatcherOnNonexistingvalue() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(SimpleConfiguration.class, "version", "2.0");
		assertFalse("Version attribute should not be matched to version matcher with value 2.0", matcher.matches(config));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonGreaterThan() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(SimpleConfiguration.class, "version", ">1.0");
		assertTrue("Version attribute should match to version matcher with value >1.0", matcher.matches(config));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonGreaterThanNoMatch() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(SimpleConfiguration.class, "version", ">1.1");
		assertFalse("Version attribute should match to version matcher with value >1.1", matcher.matches(config));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonGreaterThanOrEquals() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(SimpleConfiguration.class, "version", ">=1.1");
		assertTrue("Version attribute should match to version matcher with value >=1.1", matcher.matches(config));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonGreaterThanOrEqualsNoMatch() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(SimpleConfiguration.class, "version", "<=1.1.1");
		assertTrue("Version attribute should match to version matcher with value >=1.1.1", matcher.matches(config));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonRange() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(SimpleConfiguration.class, "version", "[1.1.0;1.2]");
		assertTrue("Version attribute should match to version matcher with value [1.1.0;1.2]", matcher.matches(config));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonRange2() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(SimpleConfiguration.class, "version", "(1.0.4;1.2]");
		assertTrue("Version attribute should match to version matcher with value (1.0.4;1.2]", matcher.matches(config));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonRangeNoMatch() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(SimpleConfiguration.class, "version", "(1.1;1.2]");
		assertFalse("Version attribute should not match to version matcher with value (1.1;1.2]", matcher.matches(config));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonRange3() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(SimpleConfiguration.class, "version", "[0.9.0;1.1]");
		assertTrue("Version attribute should match to version matcher with value [0.9.0;1.1]", matcher.matches(config));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonRangeNoMatch2() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(SimpleConfiguration.class, "version", "[0.9.0;1.1)");
		assertFalse("Version attribute should match to version matcher with value [0.9.0;1.1)", matcher.matches(config));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonRange4() {
		RequirementVersionMatcher matcher = new RequirementVersionMatcher(SimpleConfiguration.class, "version", "[0.9.0;1.1.1)");
		assertTrue("Version attribute should match to version matcher with value [0.9.0;1.1.1)", matcher.matches(config));
	}
}
