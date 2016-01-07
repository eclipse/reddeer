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
package org.jboss.reddeer.junit.test.internal.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.NullTestRunConfiguration;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SuiteConfigurationTest {

	private static final String LOCATIONS_ROOT_DIR;

	private SuiteConfiguration config;

	static{
		StringBuffer sbRootDir = new StringBuffer("");
		sbRootDir.append("resources");
		sbRootDir.append(File.separator);
		sbRootDir.append("org");
		sbRootDir.append(File.separator);
		sbRootDir.append("jboss");
		sbRootDir.append(File.separator);
		sbRootDir.append("reddeer");
		sbRootDir.append(File.separator);
		sbRootDir.append("junit");
		sbRootDir.append(File.separator);
		sbRootDir.append("internal");
		sbRootDir.append(File.separator);
		sbRootDir.append("configuration");
		sbRootDir.append(File.separator);
		LOCATIONS_ROOT_DIR = sbRootDir.toString();
	}
	@Before
	public void setup(){
		config = new SuiteConfiguration();
	}
	
	@Test
	public void getTestRunConfigurations(){
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), SuiteConfigurationTest.LOCATIONS_ROOT_DIR + "dirWithFiles");

		List<TestRunConfiguration> result = config.getTestRunConfigurations();
		Collections.sort(result, new TestRunComparator());
		
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getId(), is("correct_fileA.xml"));
		assertThat(result.get(1).getId(), is("correct_fileB.xml"));
	}
	
	@Test
	public void getTestRunConfigurations_caching(){
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), SuiteConfigurationTest.LOCATIONS_ROOT_DIR + "dirWithFiles");

		List<TestRunConfiguration> result1 = config.getTestRunConfigurations();
		List<TestRunConfiguration> result2 = config.getTestRunConfigurations();
		
		assertSame(result1, result2);
	}
	
	@Test
	public void getTestRunConfigurations_noConfig() {
		List<TestRunConfiguration> runConfigList = config.getTestRunConfigurations();
		assertEquals(1, runConfigList.size());
		assertTrue("RunConfiguration should be instance of NullTestRunConfiguration", runConfigList.get(0) instanceof NullTestRunConfiguration);
	}
	
	@Test
	public void getConfigurationFiles_noConfig() {
		List<File> listOfFiles = config.getConfigurationFiles();
		assertEquals("Should return empty List with no reddeer configuration file defined in <reddeer.conf>.", 0, listOfFiles.size());
	}

	@Test(expected=RedDeerConfigurationException.class)
	public void getTestRunConfigurations_notExistingLocation() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), "/abc/reddeer/nonexistingloc");

		config.getConfigurationFiles();
	}

	@Test(expected=RedDeerConfigurationException.class)
	public void getTestRunConfigurations_nottFileNotDirectory() {
		File location = mock(File.class);
		when(location.exists()).thenReturn(true);
		when(location.isFile()).thenReturn(false);
		when(location.isDirectory()).thenReturn(false);

		config.getConfigurationFiles(location);
	}

	@Test
	public void getTestRunConfigurations_file() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), SuiteConfigurationTest.LOCATIONS_ROOT_DIR + "emptyFile");

		List<File> result = config.getConfigurationFiles();

		assertThat(result.size(), is(1));
		assertThat(result.get(0).getPath(), endsWith(SuiteConfigurationTest.LOCATIONS_ROOT_DIR + "emptyFile"));
	}

	@Test
	public void getTestRunConfigurations_directory() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), SuiteConfigurationTest.LOCATIONS_ROOT_DIR + "dirWithFiles");

		List<File> result = config.getConfigurationFiles();
		Collections.sort(result);

		assertThat(result.size(), is(2));
		assertThat(result.get(0).getPath(), endsWith(SuiteConfigurationTest.LOCATIONS_ROOT_DIR + "dirWithFiles" + File.separator + "correct_fileA.xml"));
		assertThat(result.get(1).getPath(), endsWith(SuiteConfigurationTest.LOCATIONS_ROOT_DIR + "dirWithFiles" + File.separator + "correct_fileB.xml"));
	}

	@Test(expected=RedDeerConfigurationException.class)
	public void getTestRunConfigurationss_noFilesInDirectory() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), SuiteConfigurationTest.LOCATIONS_ROOT_DIR + "emptyDir");

		config.getConfigurationFiles();
	}

	@After
	public void cleanProperties(){
		System.clearProperty(RedDeerProperties.CONFIG_FILE.getName());
	}
	
	private static class TestRunComparator implements Comparator<TestRunConfiguration> {

		public int compare(TestRunConfiguration o1, TestRunConfiguration o2) {
			return o1.getId().compareTo(o2.getId());
		}
	}
}
