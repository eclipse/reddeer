package org.jboss.reddeer.workbench.view;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotWorkbenchPart;
import org.eclipse.ui.IViewReference;

/**
 * Superclass of Editor and View with ability to be closed, minimized and maximized
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
		SWTBotWorkbenchPart<IViewReference> workBenchPart = workbenchPart();
		if (workBenchPart == null) {
			throw new UnsupportedOperationException("Cannot close workbench part " +
					"before initialization provided by open method");
		}
		
		log.info("Closing workbench '" + workBenchPart.getTitle() + "'");
		workBenchPart.close();
		log.info("Workbench '" + workBenchPart.getTitle() + "' has been closed");
	}
	
}
