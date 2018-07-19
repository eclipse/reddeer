package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import org.eclipse.reddeer.jface.dialogs.TitleAreaDialog;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.hamcrest.Matcher;

/**
 * New Module info java file dialog
 * @author odockal
 *
 */
public class NewModuleInfoDialog extends TitleAreaDialog {
	
	public NewModuleInfoDialog(Shell shell) {
		super(shell);
	}
	
	public NewModuleInfoDialog(String text) {
		super(text);
	}
	
	public NewModuleInfoDialog(Matcher<?>... matchers) {
		super(matchers);
	}
	
	public void setModuleName(String name) {
		log.debug("Setting module name to '" + name + "'");
		new LabeledText("Module name:").setText(name);
	}
	
	public void dontCreate() {
		log.debug("Don't create module-info.java file");
		new PushButton("Don't Create").click();
	}
	
	public void create() {
		log.debug("Creating module-info.java file");
		new PushButton("Create").click();
	}
}
