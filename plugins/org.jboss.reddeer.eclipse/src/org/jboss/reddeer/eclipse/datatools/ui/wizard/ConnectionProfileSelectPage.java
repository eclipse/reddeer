package org.jboss.reddeer.eclipse.datatools.ui.wizard;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * A wizard page for selecting a connection profile.
 * 
 * @author apodhrad
 * 
 */
public class ConnectionProfileSelectPage extends WizardPage {

	public static final String TITLE = "New Connection Profile";
	public static final String LABEL_NAME = "Name:";
	public static final String LABEL_DESCRIPTION = "Description (optional):";

	/**
	 * A wizard page should not know on which page index it is displayed. The
	 * wizard page can also exist outside WizardDialog. Use no-argument
	 * constructor instead.
	 * 
	 * @param wizardDialog
	 * @param pageIndex
	 */
	@Deprecated
	public ConnectionProfileSelectPage(ConnectionProfileWizard parentDialog, int indexPage) {
		super(parentDialog, indexPage);
	}

	public ConnectionProfileSelectPage() {
		super();
	}
	
	public void setConnectionProfile(String connectionProfile) {
		((ConnectionProfileWizard) getWizardDialog()).setConnectionProfile(connectionProfile);
		new DefaultTable().select(connectionProfile);
	}

	public void setName(String name) {
		new LabeledText(LABEL_NAME).setText(name);
	}

	public void setDescription(String description) {
		new LabeledText(LABEL_DESCRIPTION).setText(description);
	}
}
