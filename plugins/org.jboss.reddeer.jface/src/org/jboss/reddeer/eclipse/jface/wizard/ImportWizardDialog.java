package org.jboss.reddeer.eclipse.jface.wizard;


/**
 * Superclass for import wizard dialogs. It opens the import wizard by clicking File -> Import... 
 * and selects an appropriate wizard in the dialog. 
 *   
 * @author Lucia Jelinkova
 * @since 0.5
 * @deprecated replaced by {@link org.jboss.reddeer.jface.wizard.ImportWizardDialog}
 */
public abstract class ImportWizardDialog extends TopMenuWizardDialog {

	public static final String DIALOG_TITLE = "Import";
	
	/**
	 * Constructor set path to the specific import item in import dialog.
	 * @param path to the specific item in import dialog
	 */
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
