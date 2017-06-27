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
package org.eclipse.reddeer.junit.internal.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.junit.annotation.AnnotationUtils;
import org.eclipse.reddeer.junit.annotation.RequirementRestriction;
import org.eclipse.reddeer.junit.internal.requirement.RequirementHelper;
import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.requirement.configuration.MissingRequirementConfiguration;
import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;
import org.eclipse.reddeer.junit.requirement.matcher.RequirementMatcher;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.model.InitializationError;

/**
 * Suite configuration. Suite configuration contains a map of requirement
 * configuration sets for a set of classes. Suites are constructed from this map
 * - there is one suite per configuration set and a set of test classes.
 *
 * @author mlabuda@redhat.com
 *
 */
public class SuiteConfiguration {

	private Class<?> suiteClass;

	private Map<RequirementConfigurationSet, List<Class<?>>> configurationSetSuites = new HashMap<>();

	/**
	 * Creates a new suite configuration for a suite class.
	 * 
	 * @param clazz
	 *            suite class
	 * @throws InitializationError
	 *             if suite class is null
	 */
	public SuiteConfiguration(Class<?> clazz) throws InitializationError {
		this.suiteClass = clazz;
		createTestSuites(clazz);
	}

	/**
	 * Gets all configurations sets suites for this suite configuration.
	 * 
	 * @return map of configuration sets and classes which should be executed
	 *         for specific configuration set
	 */
	public Map<RequirementConfigurationSet, List<Class<?>>> getConfigurationSetsSuites() {
		return configurationSetSuites;
	}

	/**
	 * Creates test suites for a specific set of requirement configurations and
	 * set of test classes having the same configuration set. If a test class
	 * does not belong to any set of requirements with configuration, this test
	 * class is executed in default/no-configuration test suite. If test class
	 * should have a configuration for a custom requirement but there is no
	 * suitable configuration, then such class is put to a special not-executed
	 * suite.
	 * 
	 * @param suiteClass
	 *            top level suite class, run by JUnit core runner.
	 * @throws InitializationError
	 */
	@SuppressWarnings("rawtypes")
	private void createTestSuites(Class<?> suiteClass) throws InitializationError {
		if (suiteClass == null) {
			throw new InitializationError("Suite class given is null");
		}
		List<Class<?>> testClasses = getTestClasses(suiteClass);
		for (Class<?> clazz : testClasses) {
			List<Requirement<?>> requirements = RequirementHelper.getRequirements(clazz);
			List<List<RequirementConfiguration>> requirementConfigurationsLists = new ArrayList<>();
			boolean shouldHaveConfig = false;
			Collection<RequirementMatcher> matchers = getRequirementRestrictions(clazz);
			checkMatchersAreValid(matchers);
			for (Requirement<?> requirement : requirements) {
				if (ConfigurableRequirement.class.isAssignableFrom(requirement.getClass())) {
					shouldHaveConfig = true;
					requirementConfigurationsLists
							.add(RequirementHelper.getRequirementConfigurations((ConfigurableRequirement) requirement,
									matchers));
				}
			}
			if (shouldHaveConfig  && requirementConfigurationsLists.get(0).isEmpty()) {
				Set<RequirementConfiguration> configSet = new HashSet<>();
				configSet.add(new MissingRequirementConfiguration());
				updateMap(new RequirementConfigurationSet(configSet), clazz);
			} else if (requirementConfigurationsLists.isEmpty()) {
				updateMap(new RequirementConfigurationSet(), clazz);
			} else {
				for (List<RequirementConfiguration> configurationList : getCartesianProduct(
						requirementConfigurationsLists)) {
					Set<RequirementConfiguration> configSet = new HashSet<>();
					configSet.addAll(configurationList);
					updateMap(new RequirementConfigurationSet(configSet), clazz);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private Collection<RequirementMatcher> getRequirementRestrictions(Class<?> clazz){
		Object obj =  AnnotationUtils.invokeStaticMethodWithAnnotation(clazz,
				RequirementRestriction.class, Collection.class, RequirementMatcher.class);
		
		if(obj instanceof RequirementMatcher){
			return Collections.singletonList((RequirementMatcher)obj);
		}
		return (Collection<RequirementMatcher>) obj;
	}
	
	private void checkMatchersAreValid(Collection<RequirementMatcher> matchers){
		if(matchers != null){
			matchers.stream().collect(Collectors.groupingBy(RequirementMatcher::getConfigurationClass))
			.forEach((confClass, withSameConfClass) -> {
				if(withSameConfClass.size() > 1){
					throw new RedDeerException("More than one matcher is defined for the '"+confClass+"' requirement.");
				}
			});
		}
		
	}

	private void updateMap(RequirementConfigurationSet requirementConfigurationSet, Class<?> clazz) {
		if (configurationSetSuites.containsKey(requirementConfigurationSet)) {
			List<Class<?>> classes = configurationSetSuites.get(requirementConfigurationSet);
			List<Class<?>> updatedList = new ArrayList<>();
			updatedList.addAll(classes);
			updatedList.add(clazz);;
			configurationSetSuites.put(requirementConfigurationSet, updatedList);
		} else {
			configurationSetSuites.put(requirementConfigurationSet, Arrays.asList(clazz));
		}
	}
	
	/**
	 * Gets Cartesian product of given lists.
	 * 
	 * @param lists
	 *            list of lists to get its Cartesian product
	 * @return Cartesian product of lists
	 */
	private static <T> List<List<T>> getCartesianProduct(List<List<T>> lists) {
		List<List<T>> resultLists = new ArrayList<List<T>>();
		if (lists.size() == 0) {
			resultLists.add(new ArrayList<T>());
			return resultLists;
		} else {
			List<T> firstList = lists.get(0);
			List<List<T>> remainingLists = getCartesianProduct(lists.subList(1, lists.size()));
			for (T listElement : firstList) {
				for (List<T> remainingList : remainingLists) {
					ArrayList<T> resultList = new ArrayList<T>();
					resultList.add(listElement);
					resultList.addAll(remainingList);
					resultLists.add(resultList);
				}
			}
		}
		return resultLists;
	}

	/**
	 * Gets a list of all test classes. Method recursively look up for nested
	 * suites. If there is no SuiteClasses annotation on the suite class, then
	 * the suite class is considered to be a test class.
	 * 
	 * @param suiteClass
	 *            class to be examined
	 * @return list of all test classes in specified suite class
	 */
	private List<Class<?>> getTestClasses(Class<?> suiteClass) {
		List<Class<?>> classes = new ArrayList<>();
		SuiteClasses suiteAnnontation = suiteClass.getAnnotation(SuiteClasses.class);
		if (suiteAnnontation != null) {
			for (Class<?> clazz : suiteAnnontation.value()) {
				if (clazz.getAnnotation(SuiteClasses.class) != null) {
					classes.addAll(getTestClasses(clazz));
				} else {
					classes.add(clazz);
				}
			}
		} else {
			classes.add(this.suiteClass);
		}
		return classes;
	}
}
