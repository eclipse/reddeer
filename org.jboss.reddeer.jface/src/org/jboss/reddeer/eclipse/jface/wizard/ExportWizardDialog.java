package org.jboss.reddeer.eclipse.jface.wizard;


/**
 * Superclass for export wizard dialogs. It opens the export wizard by clicking File -> Export... 
 * and selects an appropriate wizard in the dialog. 
 *   
 * @author Lucia Jelinkova
 *
 */
public abstract class ExportWizardDialog extends TopMenuWizardDialog {

	public static final String DIALOG_TITLE = "Export";
	
	public ExportWizardDialog(String... path) {
		super(path);
	}

	@Override
	protected String getDialogTitle() {
		return DIALOG_TITLE;
	}
	
	@Override
	protected String[] getMenuPath() {
		return new String[]{"File", "Export..."};
	}
}
