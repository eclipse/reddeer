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
package org.jboss.reddeer.junit.test.integration.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.Collection;
import java.util.List;

import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.junit.internal.configuration.RequirementsConfiguration;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.test.integration.configuration.JavaRequirement.CustomConfigJavaRequirementAAnnotation;
import org.jboss.reddeer.junit.test.integration.configuration.RequirementA.RequirementAAnnotation;
import org.jboss.reddeer.junit.test.integration.configuration.RequirementB.RequirementBAnnotation;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

public class ComplexConfigurationTest {

	private SuiteConfiguration config;
	
	@Test
	public void numberOfTestRunsNoConfig() throws InitializationError {
		config = new SuiteConfiguration(TestSuite.class);
		assertThat(getTestRunConfigurationCount(config.getTestRunConfigurations().values()), is(1));
	}
	
	@Test
	public void numberOfTestRunsWithConfig() throws InitializationError {
		config = new SuiteConfiguration(RequirementABClass1.class);
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), 
				"resources/org/jboss/reddeer/junit/integration/configuration/requirements.xml");
		assertThat(getTestRunConfigurationCount(config.getTestRunConfigurations().values()), is(4));
	}

	@Test
	public void testRunA() throws InitializationError {
		SuiteConfiguration suiteConfiguration = new SuiteConfiguration(AllRequirementsClass.class);
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), 
				"resources/org/jboss/reddeer/junit/integration/configuration/requirementsA.xml");
		
		assertThat(suiteConfiguration.getTestRunConfigurations().keySet().size(), is(1));
		
		List<TestRunConfiguration> list = suiteConfiguration.getTestRunConfigurations().values().iterator().next();
		RequirementsConfiguration requirementsConfiguration = list.get(0).getRequirementConfiguration();

		RequirementA requirementA = new RequirementA();
		RequirementB requirementB = new RequirementB();
		JavaRequirement javaRequirement = new JavaRequirement();
		
		requirementsConfiguration.configure(requirementA);
		requirementsConfiguration.configure(requirementB);
		requirementsConfiguration.configure(javaRequirement);
		
		assertThat(requirementA.getA(), is("1"));
		
		assertThat(requirementB.getA(), is("3"));
		assertThat(requirementB.getB(), is("2"));

		assertThat(javaRequirement.getConfig().getRuntime(), is("/home/java"));
		assertThat(javaRequirement.getConfig().getVersion(), is("1.6"));
	}
	
	@Test
	public void testRunB() throws InitializationError {
		SuiteConfiguration suiteConfiguration = new SuiteConfiguration(AllRequirementsClass.class);
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), "resources/org/jboss/reddeer/junit/integration/configuration/requirementsB.xml");
		
		assertThat(suiteConfiguration.getTestRunConfigurations().keySet().size(), is(1));
		
		List<TestRunConfiguration> list = suiteConfiguration.getTestRunConfigurations().values().iterator().next();
		RequirementsConfiguration requirementsConfiguration = list.get(0).getRequirementConfiguration();
		RequirementA requirementA = new RequirementA();
		RequirementB requirementB = new RequirementB();
		JavaRequirement javaRequirement = new JavaRequirement();
		
		requirementsConfiguration.configure(requirementA);
		requirementsConfiguration.configure(requirementB);
		requirementsConfiguration.configure(javaRequirement);
		
		assertThat(requirementA.getA(), is("10"));
		
		assertThat(requirementB.getA(), is("30"));
		assertThat(requirementB.getB(), is("20"));

		assertThat(javaRequirement.getConfig().getRuntime(), is("/java"));
		assertThat(javaRequirement.getConfig().getVersion(), nullValue());
	}
	
	@Test
	public void testRunAll() throws InitializationError {
		SuiteConfiguration suiteConfiguration = new SuiteConfiguration(AllRequirementsClass.class);
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), "resources/org/jboss/reddeer/junit/integration/configuration/requirements.xml");
		
		assertThat(suiteConfiguration.getTestRunConfigurations().keySet().size(), is(1));
		assertThat(suiteConfiguration.getTestRunConfigurations().values().iterator().next().size(), is(8));
	}
	
	private int getTestRunConfigurationCount(Collection<List<TestRunConfiguration>> collection) {
		short testCount = 0;
		for (List<TestRunConfiguration> testRuns : collection) {
			testCount += testRuns.size();
		}
		return testCount;
	}
	
	private static class TestSuite {}
	
	@RequirementAAnnotation
	private static class RequirementAClass1 {}
	
	@RequirementBAnnotation
	private static class RequirementBClass1 {}
	
	@CustomConfigJavaRequirementAAnnotation
	private static class RequirementJavaClass {}
	
	@RequirementAAnnotation
	@RequirementBAnnotation
	private static class RequirementABClass1 {}
	
	@RequirementAAnnotation
	@RequirementBAnnotation
	@CustomConfigJavaRequirementAAnnotation
	private static class AllRequirementsClass {}
}
