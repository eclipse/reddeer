package org.jboss.reddeer.eclipse.test.datatools.ui;

import java.io.File;

import org.jboss.reddeer.eclipse.datatools.ui.preference.DriverDefinitionPreferencePage;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.DriverDefinitionPage;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.DriverDefinitionWizard;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.junit.Test;

/**
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * 
 */
public class DriverDefinitionTest extends RedDeerTest {

	@Test
	public void driverDefinitionTest() {
		DriverDefinitionPreferencePage preferencePage = new DriverDefinitionPreferencePage();
		preferencePage.open();
		DriverDefinitionWizard wizard = preferencePage.addDriverDefinition();
		DriverDefinitionPage page = wizard.getFirstPage();
		page.selectDriverTemplate("HSQLDB JDBC Driver", "1.8");
		page.setName("Test HSLQDB Driver");
		page.addDriverLibrary(new File("target/lib/hsqldb-1.8.0.10.jar").getAbsolutePath());
		wizard.finish();
		// test if a driver was successfully created
		new DefaultTable().getItem("Test HSLQDB Driver");
		preferencePage.ok();
	}
}
