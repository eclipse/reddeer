package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.condition.TreeHasSelectedItems;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents the first page displayed when invoked {@link NewServerWizardDialog}
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewServerWizardPage extends WizardPage {

	public void selectType(String... type){
		new WaitUntil(new TreeHasSelectedItems(new DefaultTree()),TimePeriod.NORMAL,false);
		new DefaultTreeItem(type).select();
	}

	public void setName(String name){
		new LabeledText("Server name:").setText(name);
	}

	public void setHostName(String name){
		new LabeledText("Server's host name:").setText(name);
	}
}
