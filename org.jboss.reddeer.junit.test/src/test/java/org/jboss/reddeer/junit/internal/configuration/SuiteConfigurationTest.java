package org.jboss.reddeer.junit.internal.configuration;

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

import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SuiteConfigurationTest {

	private String LOCATIONS_ROOT_DIR = "src/test/resources/org/jboss/reddeer/junit/internal/configuration/";

	private SuiteConfiguration config;

	@Before
	public void setup(){
		config = new SuiteConfiguration();
	}
	
	@Test
	public void getTestRunConfigurations(){
		System.setProperty(SuiteConfiguration.PROPERTY_CONFIG_LOC, LOCATIONS_ROOT_DIR + "dirWithFiles");

		List<TestRunConfiguration> result = config.getTestRunConfigurations();
		Collections.sort(result, new TestRunComparator());
		
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getId(), is("fileA"));
		assertThat(result.get(1).getId(), is("fileB"));
	}
	
	@Test
	public void getTestRunConfigurations_caching(){
		System.setProperty(SuiteConfiguration.PROPERTY_CONFIG_LOC, LOCATIONS_ROOT_DIR + "dirWithFiles");

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
		System.setProperty(SuiteConfiguration.PROPERTY_CONFIG_LOC, "/abc/reddeer/nonexistingloc");

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
		System.setProperty(SuiteConfiguration.PROPERTY_CONFIG_LOC, LOCATIONS_ROOT_DIR + "emptyFile");

		List<File> result = config.getConfigurationFiles();

		assertThat(result.size(), is(1));
		assertThat(result.get(0).getPath(), endsWith(LOCATIONS_ROOT_DIR + "emptyFile"));
	}

	@Test
	public void getTestRunConfigurations_directory() {
		System.setProperty(SuiteConfiguration.PROPERTY_CONFIG_LOC, LOCATIONS_ROOT_DIR + "dirWithFiles");

		List<File> result = config.getConfigurationFiles();
		Collections.sort(result);
		
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getPath(), endsWith(LOCATIONS_ROOT_DIR + "dirWithFiles/fileA"));
		assertThat(result.get(1).getPath(), endsWith(LOCATIONS_ROOT_DIR + "dirWithFiles/fileB"));
	}

	@Test(expected=RedDeerConfigurationException.class)
	public void getTestRunConfigurationss_noFilesInDirectory() {
		System.setProperty(SuiteConfiguration.PROPERTY_CONFIG_LOC, LOCATIONS_ROOT_DIR + "emptyDir");

		config.getConfigurationFiles();
	}

	@After
	public void cleanProperties(){
		System.clearProperty(SuiteConfiguration.PROPERTY_CONFIG_LOC);
	}
	
	private static class TestRunComparator implements Comparator<TestRunConfiguration> {

		public int compare(TestRunConfiguration o1, TestRunConfiguration o2) {
			return o1.getId().compareTo(o2.getId());
		}
	}
}
