package org.jboss.reddeer.workbench.view;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotWorkbenchPart;
import org.eclipse.ui.IViewReference;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.Thrower;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * Superclass of Editor and View with ability to be closed, minimized and
 * maximized
 * 
 * @author jjankovi
 * 
 */
public abstract class WorkbenchPart {

	protected final Logger log = Logger.getLogger(this.getClass());

	public void close() {
		closeWorkbenchPart();
	}

	public void minimize() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	public void maximize() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	protected abstract SWTBotWorkbenchPart<IViewReference> workbenchPart();

	private void closeWorkbenchPart() {
		final SWTBotWorkbenchPart<IViewReference> workBenchPart = workbenchPart();
		Thrower.objectIsNull(workBenchPart,
				"Cannot close uninitialized workbench part");

		log.info("Closing workbench '" + workBenchPart.getTitle() + "'");

		workBenchPart.close();
		waitUntilWorkbenchIsClosed(workBenchPart);

		log.info("Workbench '" + workBenchPart.getTitle() + "' has been closed");
	}

	private void waitUntilWorkbenchIsClosed(
			final SWTBotWorkbenchPart<IViewReference> workBenchPart) {
		new WaitWhile(new WaitCondition() {

			@Override
			public boolean test() {
				return workBenchPart.isActive();
			}

			@Override
			public String description() {
				return "Workbench part with title ' "
						+ workBenchPart.getTitle() + "' was not closed";
			}
		});
	}

}
