package org.jboss.reddeer.eclipse.jface.preference;

import org.jboss.reddeer.swt.impl.button.CheckBox;

/**
 * Class represents folding preference page Java -> Editor -> Folding
 * 
 * @author jjankovi
 *
 */
public class FoldingPreferencePage extends PreferencePage {

	public FoldingPreferencePage() {
		super("Java", "Editor", "Folding");
	}
	
	public boolean isFoldingChecked() {
		return new CheckBox(0).isChecked();
	}
	
	public void enableFolding() {
		new CheckBox(0).toggle(true);
	}
	
	public void disableFolding() {
		new CheckBox(0).toggle(false);
	}
	
	public boolean isCommentsChecked() {
		return new CheckBox(1).isChecked();
	}
	
	public void enableComments() {
		new CheckBox(1).toggle(true);
	}
	
	public void disableComments() {
		new CheckBox(1).toggle(false);
	}
	
	public boolean isHeaderCommentsChecked() {
		return new CheckBox(2).isChecked();
	}
	
	public void enableHeaderComments() {
		new CheckBox(2).toggle(true);
	}
	
	public void disableHeaderComments() {
		new CheckBox(2).toggle(false);
	}
	
	public boolean isInnerTypesChecked() {
		return new CheckBox(3).isChecked();
	}

	public void enableInnerTypes() {
		new CheckBox(3).toggle(true);
	}
	
	public void disableInnerTypes() {
		new CheckBox(3).toggle(false);
	}
	
	public boolean isMembersChecked() {
		return new CheckBox(4).isChecked();
	}

	public void enableMembers() {
		new CheckBox(4).toggle(true);
	}
	
	public void disableMembers() {
		new CheckBox(4).toggle(false);
	}
	
	public boolean isImportsChecked() {
		return new CheckBox(5).isChecked();
	}

	public void enableImports() {
		new CheckBox(5).toggle(true);
	}
	
	public void disableImports() {
		new CheckBox(5).toggle(false);
	}

}
