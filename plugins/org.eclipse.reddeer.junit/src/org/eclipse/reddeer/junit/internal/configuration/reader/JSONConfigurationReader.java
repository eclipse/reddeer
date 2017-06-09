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
package org.eclipse.reddeer.junit.internal.configuration.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.reddeer.junit.configuration.RedDeerConfigurationException;
import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * JSON requirement reader read a requirements configurations from JSON file.
 * 
 * @author mlabuda@redhat.com
 */
public class JSONConfigurationReader implements ConfigurationReader {

	private ObjectMapper mapper;

	public JSONConfigurationReader() {
		mapper = new ObjectMapper();
	}

	@Override
	public List<RequirementConfiguration> loadConfigurations(File file) {
		if (!file.exists()) {
			throw new RedDeerConfigurationException("Provided configuration file " + file.getAbsolutePath() + ""
					+ "does not exist.");
		}
		try {
			JsonNode root = mapper.readTree(file);
			return getConfigurationsFromRootNode(root);
		} catch (IOException ioe) {
			throw new RedDeerConfigurationException("Could not create configurations for requirements from file "
					+ file.getAbsolutePath() + " due to IO exception" , ioe);
		} catch (ClassNotFoundException cnfe) {
			throw new RedDeerConfigurationException("Could not create configurations due to not existing declared "
					+ "configuration class in configuration file " + file.getAbsolutePath(), cnfe);
		} catch (ClassCastException cce) {
			throw new RedDeerConfigurationException("Could not create configurations for requirements from file " + file.getAbsolutePath() 
					+ "due to class hierarchy. Class specified in file is not descendant of requirement configuration", cce);
		}
	}

	private List<RequirementConfiguration> getConfigurationsFromRootNode(JsonNode root) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		List<RequirementConfiguration> configs = new ArrayList<RequirementConfiguration>();
		Iterator<Entry<String, JsonNode>> it = root.fields();

		while (it.hasNext()) {
			Entry<String, JsonNode> node = it.next();
			Class<RequirementConfiguration> clazz = loadClass(node.getKey());
			
			CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class, clazz);
			List<RequirementConfiguration> resultList = mapper.readValue(node.getValue().toString(), typeReference);
			configs.addAll(resultList);
		}
		
		return configs;
	}
	
	/**
	 * Loads a class from given class name. At first, it uses bundle class loader. If fails, then it uses context 
	 * class loader of current thread.
	 * @param className class name
	 * @return Class matching specified class name
	 */
	@SuppressWarnings("unchecked")
	private Class<RequirementConfiguration> loadClass(String className) {
		Thread currentThread = Thread.currentThread();
		ClassLoader classLoader = currentThread.getContextClassLoader();
		currentThread.setContextClassLoader(this.getClass().getClassLoader());
		try {
			return (Class<RequirementConfiguration>) Thread.currentThread().getContextClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			try {
				return (Class<RequirementConfiguration>) classLoader.loadClass(className);
			} catch (ClassNotFoundException e1) {
				throw new RedDeerConfigurationException("Class " + className + " could not be loaded using context nor bundle class loader.", e);
			}
		} finally {
			currentThread.setContextClassLoader(classLoader);
		}
	}
}
