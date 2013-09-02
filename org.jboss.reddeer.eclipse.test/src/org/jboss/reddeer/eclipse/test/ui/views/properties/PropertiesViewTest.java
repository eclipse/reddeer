package org.jboss.reddeer.eclipse.test.ui.views.properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.views.properties.PropertiesView;
import org.jboss.reddeer.eclipse.ui.views.properties.PropertiesViewProperty;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Tests Properties View
 * @author Vlado Pakan
 *
 */
@RunWith(RedDeerSuite.class)
public class PropertiesViewTest extends RedDeerTest{
	private static final String TEST_PROJECT_NAME = "PropertiesViewTestProject";
	@Override
	protected void setUp() {
		super.setUp();
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = dialog.getFirstPage();
		page1.setProjectName(PropertiesViewTest.TEST_PROJECT_NAME);
		dialog.finish();
		new PackageExplorer().getProject(PropertiesViewTest.TEST_PROJECT_NAME)
				.getTreeItem().select();
	}
	
	@Test
	public void getProperty(){
		PropertiesView propertiesView = new PropertiesView();
		propertiesView.toggleShowCategories(true);
		String namePropertyValue = propertiesView.getProperty("Info","name")
			.getPropertyValue();
		assertTrue("name property has to have value " + PropertiesViewTest.TEST_PROJECT_NAME +
				" but is " + namePropertyValue,
			namePropertyValue.equals(PropertiesViewTest.TEST_PROJECT_NAME));
	}

	@Test
	public void getProperties(){
		PropertiesView propertiesView = new PropertiesView();
		propertiesView.toggleShowCategories(true);
		List<PropertiesViewProperty> properties = propertiesView.getProperties();
		assertTrue("Expected cound of properties was 8 but is" + properties.size() ,
			properties.size() == 8);
		LinkedList<String> propNames = new LinkedList<String>();
		for (PropertiesViewProperty prop : properties){
			propNames.add(prop.getPropertyName());
		}
		String propName = "name";
		assertTrue("Properties list doesn't contain property " + propName ,
				propNames.contains(propName));
		propName = "location";
		assertTrue("Properties list doesn't contain property " + propName ,
				propNames.contains(propName));
	}
	@Test(expected=SWTLayerException.class)
	public void getNonExistingProperty(){
		PropertiesView propertiesView = new PropertiesView();
		propertiesView.getProperty("@#$_NON_EXISTING_PROPERTY_%^$");
	}
	@Test
	public void toggleShowCategories(){
		PropertiesView propertiesView = new PropertiesView();
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
	@Override
	protected void tearDown() {
		PackageExplorer packageExplorer = new PackageExplorer();
		if (packageExplorer.containsProject(PropertiesViewTest.TEST_PROJECT_NAME)) {
			packageExplorer.getProject(PropertiesViewTest.TEST_PROJECT_NAME)
					.delete(true);
		}
		
		super.tearDown();
	}
	
	private boolean containsProperty(PropertiesView propertiesView , String... propertyNamePath){
		boolean result = false;
		try{
			propertiesView.getProperty(propertyNamePath);
			result = true;
		} catch (SWTLayerException swtle){
			result = false;
		}
		return result;
	}
}
