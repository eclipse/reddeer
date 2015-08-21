package org.jboss.reddeer.eclipse.ui.wizards;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.junit.wizards.NewTestCaseWizardPageTwo;
import org.eclipse.jface.resource.ImageDescriptor;
import org.jboss.reddeer.ui.Activator;

/**
 * Second page of Red Deer Test Case wizard allows user to select methods that should be
 * tested. Extends JUnit wizard page. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRedDeerTestWizardPageTwo extends NewTestCaseWizardPageTwo {

	protected NewRedDeerTestWizardPageTwo() {
		super();
		setImageDescriptor(ImageDescriptor.createFromURL(FileLocator.find(
				Platform.getBundle(Activator.PLUGIN_ID), new Path(
						"resources/reddeer_icon.png"), null)));
	}
}