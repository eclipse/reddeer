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
package org.eclipse.reddeer.eclipse.test.ui.views.properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.reddeer.eclipse.ui.views.properties.PropertySheetProperty;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Tests Properties View
 * @author Vlado Pakan
 *
 */
@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class PropertiesViewTest {
	private static final String TEST_PROJECT_NAME = "PropertiesViewTestProject";

	@Before
	public void setUp() {
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
		NewJavaProjectWizardPageOne page1 =new NewJavaProjectWizardPageOne(dialog);
		page1.setProjectName(PropertiesViewTest.TEST_PROJECT_NAME);
		dialog.finish();
		new PackageExplorerPart().getProject(PropertiesViewTest.TEST_PROJECT_NAME)
				.getTreeItem().select();
	}
	
	@Test
	public void getProperty(){
		PropertySheet propertiesView = new PropertySheet();
		propertiesView.open();
		propertiesView.toggleShowCategories(true);
		String namePropertyValue = propertiesView.getProperty("Info","name")
			.getPropertyValue();
		assertTrue("name property has to have value " + PropertiesViewTest.TEST_PROJECT_NAME +
				" but is " + namePropertyValue,
			namePropertyValue.equals(PropertiesViewTest.TEST_PROJECT_NAME));
	}

	@Test
	public void getProperties(){
		PropertySheet propertiesView = new PropertySheet();
		propertiesView.open();
		propertiesView.toggleShowCategories(true);
		List<PropertySheetProperty> properties = propertiesView.getProperties();
		assertTrue("Expected cound of properties was 8 but is" + properties.size() ,
			properties.size() == 8);
		LinkedList<String> propNames = new LinkedList<String>();
		for (PropertySheetProperty prop : properties){
			propNames.add(prop.getPropertyName());
		}
		String propName = "name";
		assertTrue("Properties list doesn't contain property " + propName ,
				propNames.contains(propName));
		propName = "location";
		assertTrue("Properties list doesn't contain property " + propName ,
				propNames.contains(propName));
	}
	
	@Test(expected=CoreLayerException.class)
	public void getNonExistingProperty(){
		PropertySheet propertiesView = new PropertySheet();
		propertiesView.getProperty("@#$_NON_EXISTING_PROPERTY_%^$");
	}
	
	@Test
	public void toggleShowCategories(){
		PropertySheet propertiesView = new PropertySheet();
		propertiesView.open();
		propertiesView.toggleShowCategories(true);
		final String infoPropName="Info";
		// Properties View has to contain Info property
		assertTrue("Properties view has to contain property " + infoPropName,
				containsProperty(propertiesView,infoPropName));
		propertiesView.toggleShowCategories(false);
		// Properties View cannot contain Info property
		assertFalse("Properties view cannot to contain property " + infoPropName,
			containsProperty(propertiesView,infoPropName));
	}
	@After
	public void tearDown() {
		PackageExplorerPart packageExplorer = new PackageExplorerPart();
		packageExplorer.open();
		if (packageExplorer.containsProject(PropertiesViewTest.TEST_PROJECT_NAME)) {
			DeleteUtils.forceProjectDeletion(packageExplorer.getProject(PropertiesViewTest.TEST_PROJECT_NAME),
				true);
		}
	}
	
	private boolean containsProperty(PropertySheet propertiesView , String... propertyNamePath){
		boolean result = false;
		try{
			propertiesView.getProperty(propertyNamePath);
			result = true;
		} catch (CoreLayerException swtle){
			result = false;
		}
		return result;
	}
}
