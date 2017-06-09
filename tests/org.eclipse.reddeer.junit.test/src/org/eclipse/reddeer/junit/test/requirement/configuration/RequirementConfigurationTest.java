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
package org.eclipse.reddeer.junit.test.requirement.configuration;

import static org.junit.Assert.*;

import org.eclipse.reddeer.junit.requirement.RequirementException;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.ComplexConfiguration;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.SimpleConfiguration;
import org.junit.Before;
import org.junit.Test;

public class RequirementConfigurationTest {

	private SimpleConfiguration simpleConfig;
	private ComplexConfiguration complexConfig;
	private SimpleConfiguration simpleConfig2;
	
	private String version1 = "version1";
	private String name1 = "name1";
	private String type1 = "type1";
	private String version2 = "version2";
	private String name2 = "name2";
	private String type2 = "type2";
	private String cname = "cname";
	private String ctype = "ctype";
	private String cversion = "cversion";
	
	@Before
	public void setup() {
		simpleConfig = new SimpleConfiguration();
		simpleConfig.setName(name1);
		simpleConfig.setType(type1);
		simpleConfig.setVersion(version1);
		
		simpleConfig2 = new SimpleConfiguration();
		simpleConfig2.setName(name2);
		simpleConfig2.setType(type2);
		simpleConfig2.setVersion(version2);
		
		complexConfig = new ComplexConfiguration();
		complexConfig.setComplexName(cname);
		complexConfig.setComplexType(ctype);
		complexConfig.setComplexVersion(cversion);
		complexConfig.setSimpleConfiguration(simpleConfig2);
	}
	
	@Test
	public void testGetStringAttribute() {
		assertTrue(simpleConfig.getAttribute("version").equals(version1));
		assertTrue(simpleConfig.getAttribute("name").equals(name1));
		assertTrue(simpleConfig.getAttribute("type").equals(type1));
	}
	
	@Test
	public void testGetObjectAttribute() {
		SimpleConfiguration config = complexConfig.getAttribute(SimpleConfiguration.class, "simpleConfiguration");
		assertTrue(config.getName().equals(name2));
		assertTrue(config.getVersion().equals(version2));
		assertTrue(config.getType().equals(type2));
	}
	
	@Test
	public void testGetStringObjectAttributeInPlaceOfSimpleAttribute() {
		simpleConfig.getAttribute(String.class, "name");
	}
	
	@Test(expected=RequirementException.class)
	public void testGetAttributeOfComplexConfiguration() {
		simpleConfig.getAttribute(ComplexConfiguration.class, "name");
	}
	
	@Test(expected=RequirementException.class)
	public void getNonexistingStringAttribute() {
		simpleConfig.getAttribute("idontexist");
	}
	
	@Test(expected=RequirementException.class)
	public void testGetNonexistingObjectAttribute() {
		simpleConfig.getAttribute(SimpleConfiguration.class, "simpleConfiguration");
	}
	
	@Test(expected=RequirementException.class)
	public void testGetAttributeWithWrongAttributeClassAndCorrectAttributeName() {
		complexConfig.getAttribute(ComplexConfiguration.class, "simpleConfiguration");
	}
	
	@Test(expected=RequirementException.class)
	public void testGetAttributeWithWrongAttributeClassAndWrongAttributeName() {
		complexConfig.getAttribute(ComplexConfiguration.class, "idontexist");
	}
}
