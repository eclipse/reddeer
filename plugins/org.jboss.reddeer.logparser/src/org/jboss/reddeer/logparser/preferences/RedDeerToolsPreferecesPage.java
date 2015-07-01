package org.jboss.reddeer.logparser.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.jboss.reddeer.logparser.LogParserActivator;

public class RedDeerToolsPreferecesPage
	extends PreferencePage
	implements IWorkbenchPreferencePage {

	public RedDeerToolsPreferecesPage() {
		super();
		noDefaultAndApplyButton();
		setPreferenceStore(LogParserActivator.getDefault().getPreferenceStore());
		setDescription("Expand the tree to edit preferences for a specific RedDeer tool");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {
		// TODO Auto-generated method stub
		return null;
	}
	
}