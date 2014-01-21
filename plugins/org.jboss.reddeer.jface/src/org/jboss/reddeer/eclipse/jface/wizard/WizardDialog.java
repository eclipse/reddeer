package org.jboss.reddeer.eclipse.jface.wizard;

import static org.hamcrest.core.AllOf.allOf;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.jface.exception.JFaceLayerException;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * A dialog where are wizard pages displayed. It can operate Next, Back, Cancel
 * and Finish buttons.
 * 
 * @author Lucia Jelinkova
 * @author apodhrad
 * 
 */
public class WizardDialog {

	protected final Logger log = Logger.getLogger(this.getClass());

	protected int currentPage;
	protected Properties properties;
	protected Map<WizardPage, Matcher<WizardDialog>> wizardPageMap;

	public WizardDialog() {
		properties = new Properties();
		wizardPageMap = new HashMap<WizardPage, Matcher<WizardDialog>>();
	}

	public void setProperty(Object key, Object value) {
		properties.put(key, value);
	}

	public Object getProperty(Object key) {
		return properties.get(key);
	}

	/**
	 * Returns the first wizard page
	 * 
	 * @return first wizard page
	 */
	public WizardPage getFirstPage() {
		return getWizardPage();
	}

	/**
	 * Returns a current wizard page
	 * 
	 * @return current wizard page
	 */
	public WizardPage getWizardPage() {
		for (WizardPage wizardPage : wizardPageMap.keySet()) {
			Matcher<WizardDialog> matcher = wizardPageMap.get(wizardPage);
			if (matcher.matches(this)) {
				return wizardPage;
			}
		}
		log.warn("No wizard page found in page index '" + currentPage + "'");
		return null;
	}

	/**
	 * Adds a new wizard page
	 * 
	 * @param page
	 *            wizard page
	 * @param pageIndex
	 *            wizard page index
	 */
	public void addWizardPage(WizardPage page, int pageIndex) {
		page.setWizardDialog(this);
		wizardPageMap.put(page, new WizardPageIndex(pageIndex));
	}
	
	/**
	 * Adds a new wizard page
	 * 
	 * @param page
	 *            wizard page
	 * @param pageIndex
	 *            wizard page index
	 * @param matcher
	 *            matcher when the wizard page will be displayed
	 */
	public void addWizardPage(WizardPage page, int pageIndex, Matcher<WizardDialog> matcher) {
		page.setWizardDialog(this);
		wizardPageMap.put(page, allOf(new WizardPageIndex(pageIndex), matcher));
	}

	public void finish() {
		log.info("Finish wizard");

		String shellText = new DefaultShell().getText();
		Button button = new PushButton("Finish");
		checkButtonEnabled(button);
		new WaitWhile(new JobIsRunning());
		button.click();

		new WaitWhile(new ShellWithTextIsActive(shellText), TimePeriod.LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}

	public void cancel() {
		log.info("Cancel wizard");

		String shellText = new DefaultShell().getText();
		new WaitWhile(new JobIsRunning());
		new PushButton("Cancel").click();

		new WaitWhile(new ShellWithTextIsActive(shellText));
		new WaitWhile(new JobIsRunning());
	}

	public void next() {
		log.info("Go to next wizard page");

		Button button = new PushButton("Next >");
		checkButtonEnabled(button);
		button.click();
		currentPage++;
	}

	public void back() {
		log.info("Go to previous wizard page");
		Button button = new PushButton("< Back");
		checkButtonEnabled(button);
		button.click();
		currentPage--;
	}

	protected void checkButtonEnabled(Button button) {
		if (!button.isEnabled()) {
			throw new JFaceLayerException("Button '" + button.getText() + "' is not enabled");
		}
	}

	public void selectPage(int pageIndex) {
		if (pageIndex != currentPage) {
			boolean goBack = pageIndex < currentPage;
			while (pageIndex != currentPage) {
				if (goBack) {
					back();
				} else {
					next();
				}
			}
		}
	}

	public int getPageIndex() {
		return currentPage;
	}
}
