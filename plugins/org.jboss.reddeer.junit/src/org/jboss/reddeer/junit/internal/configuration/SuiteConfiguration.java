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
package org.jboss.reddeer.junit.internal.configuration;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.annotation.AnnotationsFinder;
import org.jboss.reddeer.junit.internal.requirement.RequirementAnnotationMatcher;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.model.InitializationError;

/**
 * Class represents test suite configuration. Provides information about test classes' requirement annotations,
 * finds all nested test classes, creates test run configuration reader that provides configuration for test runs
 * @author Lucia Jelinkova
 * @author Ondrej Dockal
 *
 */
public class SuiteConfiguration {
    
    private Class<?> suiteClass;
    
    private List<TestClassRequirementMap> annotatedTestClasses = new ArrayList<>();
    
    private List<Annotation> annotationRequirements = new ArrayList<>();
    
    private Map<TestClassRequirementMap, List<TestRunConfiguration>> testRunConfigurations = new HashMap<>();
    
	// helps to find annotations over class
	private AnnotationsFinder finder = new AnnotationsFinder(new RequirementAnnotationMatcher());
    
    /**
     * Constructor with class parameter
     * @param clazz given class object
     * @throws InitializationError if suite is null
     */
	public SuiteConfiguration(Class<?> clazz) throws InitializationError {
		this.suiteClass = clazz;
		readSubclassAnnotations();
	}
	
	/**
	 * Creates test run configuration reader that harvest configuration file according to all requirements
	 * that use configuration
	 * @return test run configuration reader
	 */
	public TestRunConfigurationReader getConfigurationFromFile() {
		File file = getConfigurationFile();
		return file == null ? null : new TestRunConfigurationReader(file, this.annotationRequirements);
	}
	
	private void createTestRunConfigurations() {
		TestRunConfigurationReader configurationReader = getConfigurationFromFile();
		if (configurationReader != null) {
			// we must create suite with its own list of test classes based on used annotations/requirements
			// take all test classes that are grouped based on configuration requirements used
			for (TestClassRequirementMap testClasses : getAnnotatedTestClasses()) {
				List<TestRunConfiguration> testRuns = new ArrayList<>();
				for (List<Object> list : getConfigurationMatrix(testClasses, configurationReader)) {
					testRuns.add(list.isEmpty() ? new NullTestRunConfiguration() : new TestRunConfigurationImpl(list));
				}
				this.testRunConfigurations.put(testClasses, testRuns);
			}
		} else {
			this.testRunConfigurations.put(new TestClassRequirementMap(new HashSet<>(), this.suiteClass), Arrays.asList(new NullTestRunConfiguration()));
		}
	}
	
	public List<TestClassRequirementMap> getAnnotatedTestClasses() {
		return this.annotatedTestClasses;
	}

	public Map<TestClassRequirementMap, List<TestRunConfiguration>> getTestRunConfigurations() {
		if (this.testRunConfigurations == null || this.testRunConfigurations.isEmpty()) {
			createTestRunConfigurations();
		}
		return this.testRunConfigurations;
	}
	
	/**
	 * Returns cartesian product of all possible sets of configuration objects
	 * @param testClasses test class requirement set object
	 * @param reader test run config. reader
	 * @return list of lists with all combination of read configurations
	 */
	@SuppressWarnings("unchecked")
	private static <T> List<List<T>> getConfigurationMatrix(TestClassRequirementMap testClasses, TestRunConfigurationReader reader) {
		/*
		 * If set of configuration objects SCO1 = {A, B, C}, SCO2 = {1, 2} and SCO3 = {3, 4}
		 * we will get list/set of results like this:
		 * [A, 1, 3], [A, 1, 4], [A, 2, 3], [A, 2, 4],
		 * [B, 1, 3], [B, 1, 4], [B, 2, 3], [B, 2, 4],
		 * [C, 1, 3], [C, 1, 4], [C, 2, 3], [C, 2, 4]
		 * but just in cases where any of test classes would contain such combination of requirements
		 */
		List<List<T>> lists = new ArrayList<>();
		for (RequirementConfiguration configObject : reader.getConfigurationList()) {
			if (testClasses.getRequirementConfiguration().contains(configObject.getRequirementConfiguration())) {
				// we need to obtain all the lists of objects that will be used for cartesian product
				lists.add((List<T>)configObject.getConfiguration());
			}
		}
		return cartesianProduct(lists);
	}
	
	/**
	 * Cartesian product of given lists/sets
	 * @param lists of all configurations
	 * @return list of lists of all combinations of configurations
	 */
	private static <T> List<List<T>> cartesianProduct(List<List<T>> lists) {
	    List<List<T>> resultLists = new ArrayList<List<T>>();
	    if (lists.size() == 0) { // case where there is empty input list
	        resultLists.add(new ArrayList<T>());
	        return resultLists;
	    } else {
	    	// take first list
	        List<T> firstList = lists.get(0); 
	        // recursion called for remaining lists
	        List<List<T>> remainingLists = cartesianProduct(lists.subList(1, lists.size())); 
	        // for each configuration from first list
	        for (T configuration : firstList) {
	        	// and each remaining list
	            for (List<T> remainingList : remainingLists) {
	                ArrayList<T> resultList = new ArrayList<T>();
	                resultList.add(configuration);
	                resultList.addAll(remainingList);
	                resultLists.add(resultList);
	            }
	        }
	    }
	    return resultLists;
	}
	
	/**
	 * Fills up annotationRequirements that holds all annotated requirements used for all test classes in suite
	 * Groups together all test classes' requirement types and creates TestClassRequirementSet objects
	 * @throws InitializationError if suite object is null
	 */
	private void readSubclassAnnotations() throws InitializationError {
		if (this.suiteClass == null) {
			throw new InitializationError("Suite class given is null");
		}
		List<Class<?>> listOfClasses = getTestClasses(this.suiteClass);
		for (Class<?> clazz : listOfClasses) {
			Set<Class<?>> requirements = getClassRequirements(clazz);
			TestClassRequirementMap existingReqSet = getExistingRequirementSet(requirements);
			// if null then add new set of requirements 
			if (existingReqSet == null) {
				this.annotatedTestClasses.add(new TestClassRequirementMap(requirements, clazz));
			} else {
				existingReqSet.addClass(clazz);
			}
		}
	}
	
	/**
	 * Gets all requirement annotations from specified class.
	 * @param clazz
	 * @return
	 */
	private Set<Class<?>> getClassRequirements(Class<?> clazz){
		Set<Class<?>> requirements = new HashSet<>();
		for (Annotation annotation : this.finder.find(clazz)) {
			// check if requirement is implementing configuration
			Requirement<?> requirement = RequirementConfiguration.getRequirement(annotation);
			if (requirement instanceof CustomConfiguration || requirement instanceof PropertyConfiguration) {
				this.annotationRequirements.add(annotation);
				requirements.add(annotation.annotationType());
			}
		}
		return requirements;
	}
	
	/**
	 * Returns test class requirement set object if such exists according to given set of classes
	 * @param set set of requirements
	 * @return test class requirement set if such exists
	 */
	private TestClassRequirementMap getExistingRequirementSet(Set<Class<?>> set) {
		if(set != null){
			for (TestClassRequirementMap classSet : this.annotatedTestClasses) {
				if (classSet.equalAnnotationSet(set)) {
					return classSet;
				}
			}
		}
		return null;
	}
	
	/**
	 * Tests whether class is class suite
	 * @param clazz class to be checked
	 * @return true if class given has SuiteClass annotation
	 */
	private boolean isSuite(Class<?> clazz) {
		SuiteClasses annotation = clazz.getAnnotation(SuiteClasses.class);
		return annotation != null;
	}
	
	/**
	 * Return list of all classes that the class given as a parameter could contains, uses recursion for nested suites
	 * @param suiteClass class to be examined
	 * @return list of all test classes
	 */
	private List<Class<?>> getTestClasses(Class<?> suiteClass) {
		List<Class<?>> classes = new ArrayList<>();
		// SuiteClass annotation is used with its test classes
		SuiteClasses suiteAnnontation = suiteClass.getAnnotation(SuiteClasses.class);
		if (suiteAnnontation != null) {
	        for (Class<?> clazz : suiteAnnontation.value()) {
	        	if (isSuite(clazz)) {
	        		classes.addAll(getTestClasses(clazz));
	        	} else {
	        		classes.add(clazz);
	        	}
	        }
	    // only one test class is run with RunWith(RedDeerSuite.class) annotation
		} else {
			classes.add(this.suiteClass);
		}
		return classes;
	}

	/**
	 * 
	 * Returns configuration file specified in a system property. It can be a single file 
	 * or a directory where only one configuration xml file is returned (non recursively).
	 * 
	 * @return configuration file
	 */
	public File getConfigurationFile(){
		if (RedDeerProperties.CONFIG_FILE.getValue() == null){
			return null;
		}

		return getConfigurationFile(new File(RedDeerProperties.CONFIG_FILE.getValue()));
	}

	/**
	 * Gets the configuration file.
	 *
	 * @param location the location
	 * @return the configuration file
	 */
	public File getConfigurationFile(File location) {
		if (!location.exists()) {
			throw new RedDeerConfigurationException("The configuration location " + location.getAbsolutePath() + " does not exist");
		}

		if (location.isFile() && location.getName().endsWith(".xml")) {
			return location;
		} else {
			throw new RedDeerConfigurationException("The configuration location " + location.getAbsolutePath() + " must be a xml file");
		}
	}
}
