package org.jboss.reddeer.jface.preference;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.jface.exception.JFaceLayerException;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.CLabelWithTextIsAvailable;
import org.jboss.reddeer.swt.condition.ShellHasChildrenOrIsNotAvailable;
import org.jboss.reddeer.swt.impl.button.CancelButton;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Preference dialog implementation. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class PreferenceDialog {

	private static final Logger log = Logger.getLogger(PreferenceDialog.class);
	private Set<PreferencePage> openedPreferecePages = new HashSet<PreferencePage>();
	private static final String PROGRESS_SHELL_TITLE = "Progress Information";
	
	/**
	 * Returns the title of the dialog
	 * @return
	 */
	public abstract String getTitle();
	
	/**
	 * Opens the dialog (e.g. by menu)
	 */
	protected abstract void openImpl();
	
	/**
	 * Opens the dialog. Contains checks if the dialog is open, 
	 * opening of the dialog (implementation by subclasses) and
	 * activating the dialog's shell. 
	 */
	public void open() {
		log.info("Open Preferences dialog");

		if (isOpen()){
			log.debug("Preferences dialog was already opened.");
		} else{
			openImpl();
		}
		
		new DefaultShell(getTitle());
	}
	
	/**
	 * Selects the specified preference page <var>page</var>.
	 * @param page preference page to be opened
	 */
	public void select(PreferencePage page) {
		if (page == null) {
			throw new IllegalArgumentException("page can't be null");
		}
		openedPreferecePages.add(page);
		select(page.getPath());
	}

	/**
	 * Selects preference page with the specified <var>path</var>.
	 * @param path path in preference shell tree to specific preference page
	 */
	public void select(String... path) {
		if (path == null) {
			throw new IllegalArgumentException("path can't be null");
		}
		if (path.length == 0) {
			throw new IllegalArgumentException("path can't be empty");
		}
		TreeItem t = new DefaultTreeItem(path);
		t.select();
		new WaitUntil(new CLabelWithTextIsAvailable(path[path.length-1]), TimePeriod.NORMAL, false);
	}
	
	/**
	 * Get name of the current preference page.
	 * 
	 * @return name of preference page
	 */
	public String getPageName() {
		DefaultCLabel cl = new DefaultCLabel();
		return cl.getText();
	}
	
	/**
	 * Presses Ok button on Property Dialog. 
	 */
	public void ok() {
		org.jboss.reddeer.swt.api.Shell preferenceShell = new DefaultShell(getTitle());
		ShellHandler handler = ShellHandler.getInstance();
		final String parentShellText = WidgetHandler.getInstance().getText(
				handler.getParentShell(preferenceShell.getSWTWidget()));
		
		OkButton ok = new OkButton();
		ok.click();
		while (!handler.isDisposed(preferenceShell.getSWTWidget())) {
			new WaitUntil(new ShellHasChildrenOrIsNotAvailable(preferenceShell));
			if(!handler.isDisposed(preferenceShell.getSWTWidget()) && 
					handler.getShells(preferenceShell.getSWTWidget()).length > 0){
				
				handlePreferencesOK();
			}
		}
		openedPreferecePages.clear();
		new DefaultShell(parentShellText);
	}
	
	/**
	 * Presses Cancel button on Property Dialog. 
	 */
	public void cancel() {
		final String parentShellText = WidgetHandler.getInstance().getText(
				ShellHandler.getInstance().getParentShell(new DefaultShell(getTitle()).getSWTWidget()));
		
		CancelButton cancel = new CancelButton();
		cancel.click();
		new WaitWhile(new ShellWithTextIsAvailable(getTitle())); 
		openedPreferecePages.clear();
		new DefaultShell(parentShellText);
	}
	
	/**
	 * Checks if the specific preference dialog is open.
	 * @return true if the dialog is open, false otherwise
	 */
	public boolean isOpen() {
		Shell shell = ShellLookup.getInstance().getShell(getTitle(),TimePeriod.SHORT);
		return (shell != null);		
	}
	
	private void handlePreferencesOK(){
		org.jboss.reddeer.swt.api.Shell changeShell = new DefaultShell();
		String changeShellText = changeShell.getText();
		if(changeShellText.equals(PROGRESS_SHELL_TITLE)){
			new WaitWhile(new ShellWithTextIsAvailable(PROGRESS_SHELL_TITLE));
			return;
		}
		PreferencePage usedPage = null;
		for(PreferencePage p: openedPreferecePages){
			if(changeShellText.equals(p.getPageChangedShellName())){
				p.handlePageChange();
				usedPage = p;
				break;
			}
		}
		try{ 
			new WaitWhile(new ShellWithTextIsAvailable(changeShellText));
		} catch (WaitTimeoutExpiredException ex){
			if(usedPage == null){
				throw new JFaceLayerException("Unable to close '"+changeShellText+"' because no "
						+ "preference page is handling it");
			} else {
				throw new JFaceLayerException(usedPage.getPath()[usedPage.getPath().length-1]+" preference page "
						+ "isn't handling closing properly because '"+changeShellText+"' is still open");
			}
		}
	}
}
