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

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.matcher.VersionMatcher;
import org.eclipse.reddeer.junit.requirement.RequirementException;
import org.eclipse.reddeer.junit.requirement.matcher.RequirementMatcher;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.ComplexConfiguration;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.ComplexRequirement;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.ComplexRequirement.ComplexReq;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.SimpleConfiguration;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.SimpleRequirement;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.SimpleRequirement.SimpleReq;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.SubConfiguration;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.SubRequirement;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.SubRequirement.SubReq;
import org.junit.BeforeClass;
import org.junit.Test;

public class RequirementMatcherTest {

	private static SimpleRequirement simpleReq;
	private static SimpleConfiguration simpleConfig;
	
	private static ComplexRequirement complexReq;
	private static ComplexConfiguration complexConfig;
	
	private static SubRequirement subReq;
	private static SubConfiguration subConfig;

	@BeforeClass
	public static void setup() {
		simpleReq = new SimpleRequirement();
		
		simpleConfig = new SimpleConfiguration();
		simpleConfig.setName("SimpleConfig");
		simpleConfig.setVersion("1.1");
		simpleReq.setConfiguration(simpleConfig);
		
		complexReq = new ComplexRequirement();
		complexConfig = new ComplexConfiguration();
		complexConfig.setComplexName("ComplexConfig");
		complexConfig.setComplexType("ComplexType");
		complexConfig.setComplexVersion("2.0");
		complexConfig.setSimpleConfiguration(simpleConfig);
		complexReq.setConfiguration(complexConfig);
		
		subReq = new SubRequirement();
		subConfig = new SubConfiguration();
		subConfig.setName("SimpleConfig");
		subConfig.setSubName("SubConfig");
		subReq.setConfiguration(subConfig);
	}

	@Test
	public void testNestedAttributeOnPreciseMatch() {
		RequirementMatcher matcher = new RequirementMatcher(ComplexReq.class, "simpleConfiguration.name", "SimpleConfig");
		assertTrue("Name attribute of configuration should be matched on precise match", matcher.matches(complexConfig));
	}
	
	@Test
	public void testNestedAttributeOnPreciseMatchInSubClass() {
		RequirementMatcher matcher = new RequirementMatcher(SubReq.class, "name", "SimpleConfig");
		assertTrue("Name attribute of configuration should be matched on precise match", matcher.matches(subConfig));
		matcher = new RequirementMatcher(SubReq.class, "subName", "SubConfig");
		assertTrue("Name attribute of configuration should be matched on precise match", matcher.matches(subConfig));
	}
	
	@Test
	public void testNestedAttributeOnVersionMatch() {
		RequirementMatcher matcher = new RequirementMatcher(ComplexReq.class, "simpleConfiguration.version", new VersionMatcher(">1.0"));
		assertTrue("Version attribute of configuration should be matched on version match", matcher.matches(complexConfig));
	}
	
	@Test(expected=RequirementException.class)
	public void testThrowExceptionOnNonexistingAttribute() {
		RequirementMatcher matcher = new RequirementMatcher(ComplexReq.class, "noConfig.version", new VersionMatcher(">1.0"));
		matcher.matches(complexConfig);
	}
	
	@Test(expected=RequirementException.class)
	public void testThrowExceptionOnNonexistingNestedAttribute() {
		RequirementMatcher matcher = new RequirementMatcher(ComplexReq.class, "simpleConfiguration.what", new VersionMatcher(">1.0"));
		matcher.matches(complexConfig);
	}
	
	@Test
	public void testRequirementRegexMatcherOnRegex() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "name", new RegexMatcher("Simple.*"));
		assertTrue("Name attribute of configuration should be matched with regex.", matcher.matches(simpleConfig));
	}

	@Test
	public void testRequirementRegexMatcherOnNonexistingValue() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "name", new RegexMatcher("Complex.*"));
		assertFalse("Name of attribute should not be matched with current regex Complex*", matcher.matches(simpleConfig));
	}
	
	@Test
	public void testRequirementVersionMatcherOnPreciseMatch() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "version", new VersionMatcher("1.1"));
		assertTrue("Version attribute should be matched to version matcher with value 1.1", matcher.matches(simpleConfig));
	}
	
	@Test
	public void testRequirementVersionMatcherOnNonexistingvalue() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "version", new VersionMatcher("2.0"));
		assertFalse("Version attribute should not be matched to version matcher with value 2.0", matcher.matches(simpleConfig));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonGreaterThan() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "version", new VersionMatcher(">1.0"));
		assertTrue("Version attribute should match to version matcher with value >1.0", matcher.matches(simpleConfig));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonGreaterThanNoMatch() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "version", new VersionMatcher(">1.1"));
		assertFalse("Version attribute should match to version matcher with value >1.1", matcher.matches(simpleConfig));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonGreaterThanOrEquals() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "version", new VersionMatcher(">=1.1"));
		assertTrue("Version attribute should match to version matcher with value >=1.1", matcher.matches(simpleConfig));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonGreaterThanOrEqualsNoMatch() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "version", new VersionMatcher("<=1.1.1"));
		assertTrue("Version attribute should match to version matcher with value >=1.1.1", matcher.matches(simpleConfig));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonRange() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "version", new VersionMatcher("[1.1.0;1.2]"));
		assertTrue("Version attribute should match to version matcher with value [1.1.0;1.2]", matcher.matches(simpleConfig));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonRange2() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "version", new VersionMatcher("(1.0.4;1.2]"));
		assertTrue("Version attribute should match to version matcher with value (1.0.4;1.2]", matcher.matches(simpleConfig));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonRangeNoMatch() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "version", new VersionMatcher("(1.1;1.2]"));
		assertFalse("Version attribute should not match to version matcher with value (1.1;1.2]", matcher.matches(simpleConfig));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonRange3() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "version", new VersionMatcher("[0.9.0;1.1]"));
		assertTrue("Version attribute should match to version matcher with value [0.9.0;1.1]", matcher.matches(simpleConfig));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonRangeNoMatch2() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "version", new VersionMatcher("[0.9.0;1.1)"));
		assertFalse("Version attribute should match to version matcher with value [0.9.0;1.1)", matcher.matches(simpleConfig));
	}
	
	@Test
	public void testRequirementVersionMatcherOnComparisonRange4() {
		RequirementMatcher matcher = new RequirementMatcher(SimpleReq.class, "version", new VersionMatcher("[0.9.0;1.1.1)"));
		assertTrue("Version attribute should match to version matcher with value [0.9.0;1.1.1)", matcher.matches(simpleConfig));
	}

}
