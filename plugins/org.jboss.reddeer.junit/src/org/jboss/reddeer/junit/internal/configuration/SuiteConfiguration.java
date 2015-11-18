package org.jboss.reddeer.junit.internal.configuration;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;

/**
 * Finds configuration files and provides access to that configuration.
 * 
 * The configuration files location is specified via a system property {@link #PROPERTY_CONFIG_LOC}. It can points 
 * either to a single file or to a directory. Please note that the directory cannot contain any other files except for the
 * configuration files and that it is not processed recursively. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class SuiteConfiguration {

    private static final Logger log = Logger.getLogger(SuiteConfiguration.class);
	
    private List<TestRunConfiguration> testRunConfigs;
    
	/**
	 * Gets the test run configurations.
	 *
	 * @return the test run configurations
	 */
	public List<TestRunConfiguration> getTestRunConfigurations(){
		if (testRunConfigs == null){
			testRunConfigs = findTestRunConfigurations();
		}
		return testRunConfigs;
	}

	private List<TestRunConfiguration> findTestRunConfigurations(){
		List<TestRunConfiguration> configurations = new ArrayList<TestRunConfiguration>();
		
		log.info("Looking up configuration files defined via property " + RedDeerProperties.CONFIG_FILE.getName() + "=" + RedDeerProperties.CONFIG_FILE.getValue());
		List<File> confFilesList = getConfigurationFiles();
		if (confFilesList.isEmpty()){
			log.info("No configuration file specified");
			configurations.add(new NullTestRunConfiguration());
			return configurations;
		}
		
		for (File file :confFilesList){
			log.info("Found configuration file " + file);
			configurations.add(new TestRunConfigurationImpl(file));
		}

		return configurations;
	}

	/**
	 * 
	 * Returns configuration files specified in a system property. It can be a single file 
	 * or a directory where all files in that directory are returned (non recursively).
	 * 
	 * @return List of configuration files
	 */
	public List<File> getConfigurationFiles(){
		if (RedDeerProperties.CONFIG_FILE.getValue() == null){
			return new ArrayList<File>();
		}

		return getConfigurationFiles(new File(RedDeerProperties.CONFIG_FILE.getValue()));
	}

	/**
	 * Gets the configuration files.
	 *
	 * @param location the location
	 * @return the configuration files
	 */
	public List<File> getConfigurationFiles(File location){
		if (!location.exists()){
			throw new RedDeerConfigurationException("The configuration location " + location.getAbsolutePath() + " does not exist");
		}

		if (location.isFile()){
			return Arrays.asList(location);
		} else if (location.isDirectory()){
			return getFiles(location);
		} else {
			throw new RedDeerConfigurationException("The configuration location " + location.getAbsolutePath() + " is nor a file nor a directory");
		}
	}

	private List<File> getFiles(File dir) {
		File[] files = dir.listFiles(new RequirementFileFilter());

		if (files.length == 0){
			throw new RedDeerConfigurationException("The configuration directory " + dir.getAbsolutePath() + " does not contain any files.");
		}

		return Arrays.asList(files);
	}

	private static class RequirementFileFilter implements FileFilter {

		/*
		 * Accept only *.xml files
		 * @see java.io.FileFilter#accept(java.io.File)
		 */
		@Override
		public boolean accept(File pathname) {
			if (pathname.isFile() && pathname.toString().endsWith(".xml")) {
				return true;
			} else {
				return false;
			}
		}
	}
}
