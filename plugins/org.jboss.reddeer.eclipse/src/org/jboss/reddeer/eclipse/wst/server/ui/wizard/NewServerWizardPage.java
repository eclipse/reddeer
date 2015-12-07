package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import org.hamcrest.Matcher;
import org.hamcrest.core.StringContains;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.condition.TreeHasSelectedItems;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents the first page displayed when invoked
 * {@link NewServerWizardDialog}
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewServerWizardPage extends WizardPage {

	/**
	 * Select type.
	 *
	 * @param type
	 *            the type
	 */
	public void selectType(String... type) {
		new WaitUntil(new TreeHasSelectedItems(new DefaultTree()), TimePeriod.NORMAL, false);
		new WaitWhile(new JobIsRunning(new Matcher[] { new StringContains("Decoration") }, null, false),
				TimePeriod.NORMAL, false);
		new DefaultTreeItem(type).select();
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		new LabeledText("Server name:").setText(name);
	}

	/**
	 * Sets the host name.
	 *
	 * @param name
	 *            the new host name
	 */
	public void setHostName(String name) {
		new LabeledText("Server's host name:").setText(name);
	}
}
