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
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.reddeer.junit.Activator;
import org.eclipse.reddeer.junit.configuration.RedDeerConfigurationException;
import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.requirement.RequirementException;
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
			throw new RedDeerConfigurationException(
					"Provided configuration file " + file.getAbsolutePath() + "" + "does not exist.");
		}
		try {
			JsonNode root = mapper.readTree(file);
			return getConfigurationsFromRootNode(root);
		} catch (IOException ioe) {
			throw new RedDeerConfigurationException("Could not create configurations for requirements from file "
					+ file.getAbsolutePath() + " due to IO exception", ioe);
		} catch (ClassNotFoundException cnfe) {
			throw new RedDeerConfigurationException("Could not create configurations due to not existing declared "
					+ "configuration class in configuration file " + file.getAbsolutePath(), cnfe);
		} catch (ClassCastException cce) {
			throw new RedDeerConfigurationException(
					"Could not create configurations for requirements from file " + file.getAbsolutePath()
							+ "due to class hierarchy. Class specified in file is not descendant of requirement configuration",
					cce);
		}
	}

	private List<RequirementConfiguration> getConfigurationsFromRootNode(JsonNode root)
			throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		List<RequirementConfiguration> configs = new ArrayList<RequirementConfiguration>();
		Iterator<Entry<String, JsonNode>> it = root.fields();

		while (it.hasNext()) {
			Entry<String, JsonNode> node = it.next();
			String annotationClassString = node.getKey();
			String requirementClassString = annotationClassString.substring(0, annotationClassString.lastIndexOf("."));

			Requirement<Annotation> requirement = getRequirement(requirementClassString);
			if (ConfigurableRequirement.class.isAssignableFrom(requirement.getClass())) {
				ConfigurableRequirement<?, ?> configurableRequirement = (ConfigurableRequirement<?, ?>) requirement;
				CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class,
						configurableRequirement.getConfigurationClass());
				List<RequirementConfiguration> resultList = mapper.readValue(node.getValue().toString(), typeReference);
				configs.addAll(resultList);
			} else {
				throw new RequirementException("Annotation class for requirement " + requirement + " located in "
						+ "configuration file is not encapsulated in configurable requirement. Annotation class"
						+ " must belong to a requirement implementing ConfigurableRequirement interface");
			}
		}

		return configs;
	}

	/**
	 * 
	 * @param className
	 * @return
	 */
	private Requirement<Annotation> getRequirement(String className) {
		List<Requirement<Annotation>> requirements = Activator.getRequirements();
		for(Requirement<Annotation> req : requirements){
			if(req.getClass().getName().equals(className)){
				return req;
			}
		}
		throw new RequirementException(className+" is not registered via extension point "+Activator.REQUIREMENTS_EXTENSION_POINT);
	}
}
