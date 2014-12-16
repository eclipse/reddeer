package org.jboss.reddeer.eclipse.utils;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.ShellHasChildrenOrIsNotAvailable;
import org.jboss.reddeer.swt.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.ShellHandler;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.AbstractShell;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.matcher.AndMatcher;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;

public class DeleteUtils {
	private static final Logger log = Logger.getLogger(DeleteUtils.class);

	/**
	 * Handles specific cases during project deletion
	 * 
	 * @param deleteShellText
	 *            text of shell
	 */
	public static void handleDeletion(org.jboss.reddeer.swt.api.Shell deleteShell) {
		handleDeletion(deleteShell, TimePeriod.VERY_LONG);
	}

	/**
	 * Handles specific cases during project deletion
	 * 
	 * @param deleteShellText
	 *            text of shell
	 * @param timeout
	 *            timeout
	 */
	public static void handleDeletion(org.jboss.reddeer.swt.api.Shell deleteShell, TimePeriod timeout) {
		new WaitUntil(new ShellHasChildrenOrIsNotAvailable(deleteShell), timeout);
		int childShells = 0;
		try{
			childShells = ShellHandler.getInstance().getShells(deleteShell.getSWTWidget()).length;
		} catch (SWTLayerException ex){
			log.debug("Delete shell is disposed.");
		} finally {
			if (childShells == 1) {
				new ShellWithButton("Delete Resources", "Continue");
				new PushButton("Continue").click();
				new WaitWhile(new ShellWithTextIsAvailable("Delete Resources"), timeout);
			}
			new WaitWhile(new JobIsRunning(), timeout);
		}
	}

	/**
	 * Deletes project via Eclipse API in case deleting via UI calls failed
	 * 
	 * @param deleteShellText
	 *            text of shell
	 */
	public static void forceProjectDeletion(Project project, boolean deleteFromFileSystem) {
		try {
			project.delete(deleteFromFileSystem);
		} catch (EclipseLayerException ele) {
			log.debug("Delete project '" + project.getName() + "' via Eclipse API ");
			org.jboss.reddeer.direct.project.Project.delete(project.getName(), deleteFromFileSystem, true);
		}

	}

	private static class ShellWithButton extends AbstractShell {

		public ShellWithButton(String title, String buttonLabel) {
			super(lookForShellWithButton(title, buttonLabel));
			setFocus();
			log.debug("Shell with title '" + title + "' and button '" + buttonLabel + "' found");
		}

	}

	private static Shell lookForShellWithButton(final String title, final String buttonLabel) {
		Matcher<String> titleMatcher = new WithTextMatcher(title);
		Matcher<String> buttonMatcher = new BaseMatcher<String>() {
			@Override
			public boolean matches(Object obj) {
				if (obj instanceof Control) {
					final Control control = (Control) obj;
					ReferencedComposite ref = new ReferencedComposite() {
						@Override
						public Control getControl() {
							return control;
						}
					};
					try {
						new PushButton(ref, buttonLabel);
						return true;
					} catch (SWTLayerException e) {
						// ok, this control doesn't contain the button
					}
				}
				return false;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("containing button '" + buttonLabel + "'");
			}
		};
		@SuppressWarnings("unchecked")
		Matcher<String> matcher = new AndMatcher(titleMatcher, buttonMatcher);
		return ShellLookup.getInstance().getShell(matcher);
	}

}
