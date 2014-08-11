package org.jboss.reddeer.jface.wizard;


/**
 * Superclass for export wizard dialogs. It opens the export wizard by clicking File -> Export... 
 * and selects an appropriate wizard in the dialog. 
 *   
 * @author Lucia Jelinkova
 * @since 0.6
 *
 */
public abstract class ExportWizardDialog extends TopMenuWizardDialog {

	public static final String DIALOG_TITLE = "Export";
	
	/**
	 * Constructor set path to specific export item in export dialog.
	 * @param path
	 */
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
