package org.jboss.reddeer.eclipse.jface.wizard;


/**
 * Superclass for import wizard dialogs. It opens the import wizard by clicking File -> Import... 
 * and selects an appropriate wizard in the dialog. 
 *   
 * @author Lucia Jelinkova
 *
 */
public abstract class ImportWizardDialog extends TopMenuWizardDialog {

	public static final String DIALOG_TITLE = "Import";
	
	public ImportWizardDialog(String... path) {
		super(path);
	}

	@Override
	protected String getDialogTitle() {
		return DIALOG_TITLE;
	}
	
	@Override
	protected String[] getMenuPath() {
		return new String[]{"File", "Import..."};
	}
}
