package org.jboss.reddeer.jface.wizard;

import static org.hamcrest.core.AllOf.allOf;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.DefaultReferencedComposite;
import org.jboss.reddeer.swt.impl.button.BackButton;
import org.jboss.reddeer.swt.impl.button.CancelButton;
import org.jboss.reddeer.swt.impl.button.FinishButton;
import org.jboss.reddeer.swt.impl.button.NextButton;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitWhile;

/**
 * A dialog where are wizard pages displayed. It can operate Next, Back, Cancel
 * and Finish buttons.
 * 
 * @author Lucia Jelinkova
 * @author apodhrad
 * @author vlado pakan
 * @since 0.6
 * 
 */
public class WizardDialog {

	protected final Logger log = Logger.getLogger(this.getClass());

	@Deprecated
	protected int currentPage;
	@Deprecated
	protected Properties properties;
	@Deprecated
	protected Map<WizardPage, Matcher<WizardDialog>> wizardPageMap;

	public WizardDialog() {
		properties = new Properties();
		wizardPageMap = new HashMap<WizardPage, Matcher<WizardDialog>>();
	}
	@Deprecated
	public void setProperty(Object key, Object value) {
		properties.put(key, value);
	}
	@Deprecated
	public Object getProperty(Object key) {
		return properties.get(key);
	}

	/**
	 * Returns a current wizard page
	 * @deprecated create instance of page by yourself
	 * @return current wizard page or null when there is not any wizard page
	 */
	public WizardPage getCurrentWizardPage() {
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
	 * Automatically lists to desired wizard page and returns it.
	 * @deprecated create instance of page by yourself
	 * @param page index of desired wizard page
	 * @return instance of desired WizardPage
	 */
	
	public WizardPage getWizardPage(int pageIndex){
		selectPage(pageIndex);
		return getCurrentWizardPage();
	}

	/**
	 * Adds a new wizard page
	 * @deprecated create instance of page by yourself
	 * @param page
	 *            wizard page
	 * @param pageIndex
	 *            wizard page index
	 */
	public void addWizardPage(WizardPage page, int pageIndex) {
		wizardPageMap.put(page, new WizardPageIndex(pageIndex));
	}
	
	/**
	 * Adds a new wizard page
	 * @deprecated create instance of page by yourself
	 * @param page
	 *            wizard page
	 * @param pageIndex
	 *            wizard page index
	 * @param matcher
	 *            matcher when the wizard page will be displayed
	 */
	public void addWizardPage(WizardPage page, int pageIndex, Matcher<WizardDialog> matcher) {
		wizardPageMap.put(page, allOf(new WizardPageIndex(pageIndex), matcher));
	}

	/**
	 * Click the finish button in wizard dialog.
	 */
	public void finish() {
		finish(TimePeriod.LONG);
	}

	/**
	 * Click the finish button in wizard dialog.
	 * @param timeout to wait for wizard shell to close.
	 */
	public void finish(TimePeriod timeout) {
		log.info("Finish wizard");

		String shellText = new DefaultShell().getText();
		Button button = new FinishButton();
		button.click();

		new WaitWhile(new ShellWithTextIsActive(shellText), timeout);
		new WaitWhile(new JobIsRunning(), timeout);
	}
	
	/**
	 * Click the cancel button in wizard dialog.
	 */
	public void cancel() {
		log.info("Cancel wizard");

		String shellText = new DefaultShell().getText();
		new WaitWhile(new JobIsRunning());
		new CancelButton().click();

		new WaitWhile(new ShellWithTextIsActive(shellText));
		new WaitWhile(new JobIsRunning());
	}

	/**
	 * Click the next button in wizard dialog.
	 */
	public void next() {
		log.info("Go to next wizard page");

		Button button = new NextButton();
		button.click();
		currentPage++;
	}

	/**
	 * Click the back button in wizard dialog.
	 */
	public void back() {
		log.info("Go to previous wizard page");
		Button button = new BackButton();
		button.click();
		currentPage--;
	}

	/**
	 * Go to the specific page of wizard dialog. First wizard dialog page has index 0.
	 * @param pageIndex of desired wizard page
	 * @deprecated since 0.8.0
	 */
	@Deprecated
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

	/**
	 * Get page index of current wizard page.
	 * @deprecated
	 * @return current wizard page index
	 */
	public int getPageIndex() {
		return currentPage;
	}
	/**
	 * Returns current dialog title
	 * @return
	 */
	public String getTitle() {
		return new DefaultShell().getText();
	}
	/**
	 * Returns current dialog page title
	 * @return
	 */
	public String getPageTitle() {
		Shell shell = new DefaultShell();
		// Page Title is 3rd Label within first Composite of wizard dialog Shell and is inactive
		Control labelControl = WidgetHandler.getInstance().getChildren(
				((Composite)WidgetHandler.getInstance().getChildren(shell.getSWTWidget())[0]))[2];
		
		return new DefaultLabel(new DefaultReferencedComposite(labelControl)).getText();
	}
	/**
	 * Returns current dialog page description
	 * @return
	 */
	public String getPageDescription() {
		Shell shell = new DefaultShell();
		// Page Description is 5th Text within first Composite of wizard dialog Shell and is inactive 
		Control labelControl = WidgetHandler.getInstance().getChildren(
				((Composite)WidgetHandler.getInstance().getChildren(shell.getSWTWidget())[0]))[4];
		
		return new DefaultText(new DefaultReferencedComposite(labelControl)).getText();
	}
	/**
	 * Returns true in case Finish button is enabled
	 * @return
	 */	
	public boolean isFinishEnabled() {
		return new FinishButton().isEnabled();
	}
	/**
	 * Returns true in case Next button is enabled
	 * @return
	 */
	public boolean isNextEnabled() {
		return new NextButton().isEnabled();
	}
	/**
	 * Returns true in case Back button is enabled
	 * @return
	 */
	public boolean isBackEnabled() {
		return new BackButton().isEnabled();
	}
}
